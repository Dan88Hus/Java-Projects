package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String host = "localhost"; // Server address
        int port = 12345; // Same port as the server
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
//            System.out.println("Client connected to server!");
            System.out.println("Client started!");
            int N = 12; // You can choose any integer number
            String message = "Give me a record # " + N;
            //Send message to the server
            out.println(message);
            System.out.println("Sent: " + message);
            //Receive response from the server
            String response = in.readLine();
            System.out.println("Received: " + response);
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}