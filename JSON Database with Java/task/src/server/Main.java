package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    private static final int SIZE = 1000;
    private static String[] database = new String[SIZE];

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            database[i] = ""; // Initialize the database with empty strings
        }
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread{
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;

        }
        @Override
        public void run () {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String command;
                while ((command = in.readLine()) != null) {
                    String response = processCommand(command);
                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String processCommand(String command) {
            String[] parts = command.split(" ", 3);
            String action = parts[0];

            switch (action) {
                case "set":
                    return set(parts[1], parts[2]);
                case "get":
                    return get(parts[1]);
                case "delete":
                    return delete(parts[1]);
                case "exit":
                    return "Goodbye!";
                default:
                    return "ERROR";
            }
        }
        private String set(String indexStr, String text) {
            int index = parseIndex(indexStr);
            if (index < 0) return "ERROR";
            database[index] = text;
            return "OK";
        }
        private int parseIndex(String indexStr) {
            try {
                int index = Integer.parseInt(indexStr) - 1; // Convert to 0-based index
                if (index < 0 || index >= SIZE) return -1;
                return index;
            } catch (NumberFormatException e) {
                return -1;
            }
        }

        private String get(String indexStr) {
            int index = parseIndex(indexStr);
            if (index < 0 || database[index].isEmpty()) return "ERROR";
            return database[index];
        }
        private String delete(String indexStr) {
            int index = parseIndex(indexStr);
            if (index < 0) return "ERROR";
            database[index] = "";
            return "OK";
        }
    }
}
