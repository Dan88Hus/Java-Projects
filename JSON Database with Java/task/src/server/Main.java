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
    private static final int SIZE = 1000;
    private static final String[] database = new String[SIZE];

    public static void main(String[] args) {

        Arrays.fill(database, ""); // Initialize with empty strings

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run () {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String input;
                while ((input = in.readLine()) != null) {
                    String[] parts = input.split(" ", 3);
                    String command = parts[0].toLowerCase();

                    switch (command) {
                        case "set":
                            if (parts.length < 3) {
                                out.println("ERROR");
                                break;
                            }
                            handleSet(parts[1], parts[2], out);
                            break;
                        case "get":
                            if (parts.length < 2) {
                                out.println("ERROR");
                                break;
                            }
                            handleGet(parts[1], out);
                            break;
                        case "delete":
                            if (parts.length < 2) {
                                out.println("ERROR");
                                break;
                            }
                            handleDelete(parts[1], out);
                            break;
                        case "exit":
                            socket.close();
                            return;
                        default:
                            out.println("ERROR");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
