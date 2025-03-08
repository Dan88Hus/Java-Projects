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
    private static final String[] database = new String[SIZE];

    public static void main(String[] args) {

        for (int i = 0; i < SIZE; i++) {
            database[i] = "";
        }
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started!");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String command = in.readLine();
                    System.out.println("Received: " + command);
                    String response = processCommand(command);
                    out.println(response);

                    if ("exit".equals(command)) {
                        System.out.println("Shutting down server...");
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String processCommand (String command) {

        if (command == null || command.isEmpty()) {
            return "ERROR";
        }

        String[] parts = command.split(" ", 3);
        String action = parts[0];

        switch (action) {
            case "set":
                if (parts.length < 3) return "ERROR";
                return set(parts[1], parts[2]);
            case "get":
                if (parts.length < 2) return "ERROR";
                return get(parts[1]);
            case "delete":
                if (parts.length < 2) return "ERROR";
                return delete(parts[1]);
            case "exit":
                return "OK";
            default:
                return "ERROR";
        }

    }

    private static String get(String indexStr) {
        int index = parseIndex(indexStr);
        if (index < 0 || database[index].isEmpty()) return "ERROR";
        return database[index];
    }

    private static String set(String indexStr, String text) {
        int index = parseIndex(indexStr);
        if (index < 0) return "ERROR";
        database[index] = text;
        return "OK";

    }

    private static String delete(String indexStr) {
        int index = parseIndex(indexStr);
        if (index < 0) return "ERROR";
        database[index] = "";
        return "OK";
    }


    private static int parseIndex(String indexStr) {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index < 0 || index >= SIZE) return -1;
            return index;
        } catch (NumberFormatException e) {
            return -1;
        }
        
    }

}



