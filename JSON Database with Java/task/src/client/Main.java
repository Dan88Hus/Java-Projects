package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    @Parameter(names = "-t", description = "Type of request (get, set, delete, exit)", required = true)
    private String type;

    @Parameter(names = "-i", description = "Index of the cell", required = false)
    private Integer index;

    @Parameter(names = "-m", description = "Message to store (only for set)", required = false)
    private String message;

    public static void main(String[] args) {
        Main client = new Main();
        JCommander.newBuilder().addObject(client).build().parse(args);
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            StringBuilder command = new StringBuilder(type);
            if (index != null) command.append(" ").append(index);
            if (message != null) command.append(" ").append(message);

            System.out.println("Client started!");
            System.out.println("Sent: " + command);
            out.println(command.toString());

            String response = in.readLine();
            System.out.println("Received: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}