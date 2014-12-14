/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author samsung
 */
public class Main {

    public static void main(String[] argv) throws IOException, InterruptedException {

        System.out.println("Testing Rabbit");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        switch (n) {
            case 1:
                System.out.println("Testin simple Hello, word");
                simple.Send.main(null);
                break;
            default:
                System.out.println("Ty for your foolish input");
                break;
        }


    }
}
