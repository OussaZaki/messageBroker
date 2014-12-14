package routing;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;



public class EmitLogDirect {

    private static final String EXCHANGE_NAME = "configuration";

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
        
        // We're connected now, to the broker on the cloud machine.
        // If we wanted to connect to a broker on a the local machine we'd simply specify "localhost" as IP adresse.
        
        
        // creating a "configuration" direct channel/queue 
        Channel channel = connection.createChannel();       
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        
        // the message and the distination
        String forWho = getForWho(argv);
        String message = getMessage(argv);
        
        // publish the message
        channel.basicPublish(EXCHANGE_NAME, forWho, null, message.getBytes());
        System.out.println(" [x] Sent '" + forWho + "':'" + message + "'");
        
        // close the queue and the connexion
        channel.close();
        connection.close();
    }
    
    // simple function to get the destination from the command line argument
    private static String getForWho(String[] strings) {
        if (strings.length < 1) {
            return "client0";
        }
        return strings[0];
    }
    
    // simple function to get the message from the command line argument
    private static String getMessage(String[] strings) {
        if (strings.length < 2) {
            return "Hello World!";
        }
        return joinStrings(strings, " ", 1);
    }
    
    // simple function to construct the message 
    private static String joinStrings(String[] strings, String delimiter, int startIndex) {
        int length = strings.length;
        if (length == 0) {
            return "";
        }
        if (length < startIndex) {
            return "";
        }
        StringBuilder words = new StringBuilder(strings[startIndex]);
        for (int i = startIndex + 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}
