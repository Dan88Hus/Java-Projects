package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {
            String userInput;
            System.out.println("Connected to the server. Type your commands:");
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);
                String response = in.readLine();
                System.out.println(response);
                if (userInput.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}