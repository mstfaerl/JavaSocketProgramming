/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.sendfile;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author m07er
 */
public class FileServer {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        int portNumber;

        try {
            System.out.print("Please enter your port number : ");
            portNumber = scanner.nextInt();
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
        }

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            socket = serverSocket.accept();
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
        }

        try {
            in = socket.getInputStream();
        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
        }

        try {
            System.out.print("Please enter your file path that you recieve : ");
            String filePath = scanner.next();
            out = new FileOutputStream(filePath);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. ");
        }

        byte[] bytes = new byte[16 * 1024];

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        socket.close();
        serverSocket.close();
    }
}
