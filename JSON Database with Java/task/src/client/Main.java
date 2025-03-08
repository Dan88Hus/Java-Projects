package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    @Parameter(names = "-t", description = "Type of request (get, set, delete, exit)", required = true)
    private String type;

    @Parameter(names = "-k", description = "Key for the request")
    private String key;

    @Parameter(names = "-v", description = "Value (only for set request)")
    private String value;
    private static final Gson gson = new Gson();

    public static void main(String[] args) {
        Main client = new Main();
        JCommander.newBuilder().addObject(client).build().parse(args);
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket("localhost", 12345);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            JsonObject request = new JsonObject();
            request.addProperty("type", type);
            request.addProperty("key", key);
            if ("set".equals(type) && value != null) {
                request.addProperty("value", value);
            }

            String requestJson = gson.toJson(request);
            System.out.println("Client started!");
            System.out.println("Sent: " + requestJson);
            out.println(requestJson);

            String responseJson = in.readLine();
            System.out.println("Received: " + responseJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}