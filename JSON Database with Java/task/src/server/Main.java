package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Map<String, String> database = new HashMap<>();
    private static final Gson gson = new Gson();

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started!");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String requestJson = in.readLine();
                    System.out.println("Received: " + requestJson);


                    JsonObject request = gson.fromJson(requestJson, JsonObject.class);
                    JsonObject response = processRequest(request);

                    String responseJson = gson.toJson(response);
                    out.println(responseJson);

                    if ("exit".equals(request.get("type").getAsString())) {
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

    private static JsonObject processRequest (JsonObject request) {

        String type = request.get("type").getAsString();
        JsonObject response = new JsonObject();

        switch (type) {
            case "set":
                database.put(request.get("key").getAsString(), request.get("value").getAsString());
                response.addProperty("response", "OK");
                break;
            case "get":
                String key = request.get("key").getAsString();
                if (database.containsKey(key)) {
                    response.addProperty("response", "OK");
                    response.addProperty("value", database.get(key));
                } else {
                    response.addProperty("response", "ERROR");
                    response.addProperty("reason", "No such key");
                }
                break;
            case "delete":
                key = request.get("key").getAsString();
                if (database.containsKey(key)) {
                    database.remove(key);
                    response.addProperty("response", "OK");
                } else {
                    response.addProperty("response", "ERROR");
                    response.addProperty("reason", "No such key");
                }
                break;
            case "exit":
                response.addProperty("response", "OK");
                break;
            default:
                response.addProperty("response", "ERROR");
                response.addProperty("reason", "Invalid request");
        }
        return response;
    }
}



