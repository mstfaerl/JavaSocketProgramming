/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.chatapp;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author m07er
 */
public class Client implements Runnable {

    static Socket clientSocket = null;
    static PrintStream outputStream = null;
    static DataInputStream inputStream = null;
    static BufferedReader inputLine = null;
    static boolean closed = false;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.print("Please entered a port number : ");
        int portNumber = scanner.nextInt();
        System.out.print("Please entered a host : ");
        String host = scanner.next();

        try {
            if (portNumber >= 0 && portNumber < 65535) {
                clientSocket = new Socket(host, portNumber);
                System.out.println("You connected succesfully on " + portNumber + " port number and the " + host + " host. Say hello!");
            } else {
                clientSocket = new Socket("localhost", 2222);
                System.out.println("You can't connect with this port. So you connected automaticly localhost and 2222");
            }

            inputLine = new BufferedReader(new InputStreamReader(System.in));
            outputStream = new PrintStream(clientSocket.getOutputStream());
            inputStream = new DataInputStream(clientSocket.getInputStream());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (clientSocket != null && outputStream != null && inputStream != null) {
            try {
                new Thread(new Client()).start();
                while (!closed) {
                    outputStream.println(inputLine.readLine());
                }

                outputStream.close();
                inputStream.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        String responseLine;
        try {
            while ((responseLine = inputStream.readLine()) != null) {
                System.out.println(responseLine);
                if (responseLine.contains("***Bye")) {
                    break;
                }
            }
            closed = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
