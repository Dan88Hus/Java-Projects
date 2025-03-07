package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    private static final int PORT = 12345;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
//            System.out.println("Server started on port " + PORT);
            System.out.println("Server started!");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept(); // Accept new client
//                    System.out.println("New client connected!");
                    handleClient(clientSocket); // Handle client in a separate thread
                } catch (IOException e) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Could not start server on port: " + PORT);
            System.exit(-1);
        }
    }

    private static void handleClient (Socket clientSocket){

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            //Receive the client's message
            String message = in.readLine();

            if (message != null && message.contains("#")) {
                System.out.println("Received: " + message);
                // Correctly format and send response
                String response = "A record " + message.split("#")[1].trim() + " was sent!";
                out.println(response);
                System.out.println("Sent: " + response);
            } else {
                out.println("Invalid request!");
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        }
    }
}



