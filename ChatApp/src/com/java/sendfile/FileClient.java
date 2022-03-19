/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.sendfile;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author m07er
 */
public class FileClient {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        String host;
        int portNumber;

        System.out.print("Please enter your port number : ");
        portNumber = scanner.nextInt();
        System.out.print("Please enter your host : ");
        host = scanner.next();

        socket = new Socket(host, portNumber);

        System.out.print("Please enter your file path that you will send : ");
        String filePath = scanner.next();
        File file = new File(filePath);
        // Get the size of the file
        long length = file.length();
        byte[] bytes = new byte[16 * 1024];
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        socket.close();
    }
}
