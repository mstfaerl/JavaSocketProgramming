/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.chatapp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author m07er
 */
public class MainServer {

    static Socket clientSocket = null;
    static ServerSocket serverSocket = null;
    static ClientThread clientThread[] = new ClientThread[10];
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        
        System.out.print("Please entered a port number : ");
        int portNumber = scanner.nextInt();

        try {
            //A port number is a 16-bit unsigned integer, thus ranging from 0 to 65535
            if (portNumber >= 0 && portNumber < 65535) {
                serverSocket = new ServerSocket(portNumber);
                System.out.println("You connected succesfully on " + portNumber + " port number.");
            } else {
                serverSocket = new ServerSocket(2222);
                System.out.println("You can't connect with this port. So you connected automaticly 2222");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                //For multiple client created some threads
                clientSocket = serverSocket.accept();
                for (int i = 0; i < 9; i++) {
                    if (clientThread[i] == null) {
                        (clientThread[i] = new ClientThread(clientSocket, clientThread)).start();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
