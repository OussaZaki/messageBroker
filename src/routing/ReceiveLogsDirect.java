package routing;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsDirect {

    private static final String EXCHANGE_NAME = "configuration";
    public static final String name = "client";

    public static void main(String[] argv) throws Exception {
        
        /* calling connectionFactory to create a custome connexion with
         * rabbitMQ server information.
         */
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("146.148.27.98");
        factory.setUsername("admin");
        factory.setPassword("adminadmin");
        factory.setPort(5672);
        
        // establish the connection with RabbitMQ server using our factory.
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        // linking with the queue 
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        String queueName = channel.queueDeclare().getQueue();
        
        // command line checking
        if (argv.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect [0..n]");
            System.exit(1);
        }

        //for(String severity : argv){    
        //  channel.queueBind(queueName, EXCHANGE_NAME, severity);
        //}
        
        // define the reciever 
        String who = name + argv[0];
        channel.queueBind(queueName, EXCHANGE_NAME, who);

        System.out.println(" [*] i'm " + who + " and i'm waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        
        // waiting for the message
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();

            System.out.println(" [x] Received '" + routingKey + "':'" + message + "'");
        }
    }
}
