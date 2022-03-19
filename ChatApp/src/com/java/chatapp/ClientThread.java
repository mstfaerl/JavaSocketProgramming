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
public class ClientThread extends Thread {

    DataInputStream inputStream = null;
    PrintStream outputStream = null;
    Socket clientSocket = null;
    ClientThread clientThread[];
    static Scanner scanner = new Scanner(System.in);

    public ClientThread(Socket clientSocket, ClientThread[] clientThread) {
        this.clientSocket = clientSocket;
        this.clientThread = clientThread;
    }

    public void run() {
        String line;
        String name;
        try {
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new PrintStream(clientSocket.getOutputStream());
            outputStream.println("Enter your name : ");
            name = inputStream.readLine();
            outputStream.println("If you want to leave please write /quit.");
            for (int i = 0; i < 9; i++) {
                if (clientThread[i] != null && clientThread[i] != this) {
                    clientThread[i].outputStream.println(name + " has connected chat!");
                }
            }

            while (true) {
                line = inputStream.readLine();
                if (line.startsWith("/quit")) {
                    break;
                }
                for (int i = 0; i < 9; i++) {
                    if (clientThread[i] != null) {
                        clientThread[i].outputStream.println("<" + name + "> : " + line);
                    }
                }
            }

            for (int i = 0; i < 9; i++) {
                if (clientThread[i] != null && clientThread[i] != this) {
                    clientThread[i].outputStream.println(name + " has disconnected chat!");
                }
            }

            for (int i = 0; i < 9; i++) {
                if (clientThread[i] == this) {
                    clientThread[i] = null;
                }
            }

            inputStream.close();
            outputStream.close();
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
