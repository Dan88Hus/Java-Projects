package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Main {
    private static final String DB_PATH = System.getProperty("user.dir") + "/src/server/data/db.json";
    private static final Map<String, String> database = new HashMap<>();
    private static final Gson gson = new Gson();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        loadDatabase();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server started!");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.execute(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

    }

    private static void handleClient(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestJson = in.readLine();
            System.out.println("Received: " + requestJson);

            JsonObject request = gson.fromJson(requestJson, JsonObject.class);
            JsonObject response = processRequest(request);

            String responseJson = gson.toJson(response);
            out.println(responseJson);

            if ("exit".equals(request.get("type").getAsString())) {
                System.out.println("Shutting down server...");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadDatabase() {
        lock.writeLock().lock();
        try {
            if (Files.exists(Paths.get(DB_PATH))) {
                String json = new String(Files.readAllBytes(Paths.get(DB_PATH)));
                Type type = new TypeToken<Map<String, String>>(){}.getType();
                database.putAll(gson.fromJson(json, type));
            }
        } catch (IOException e) {
            System.out.println("Could not load database.");
        } finally {
            lock.writeLock().unlock();
        }
    }

    private static JsonObject processRequest (JsonObject request) {

        String type = request.get("type").getAsString();
        JsonObject response = new JsonObject();

        switch (type) {
            case "set":
                lock.writeLock().lock();
                try {
                    database.put(request.get("key").getAsString(), request.get("value").getAsString());
                    saveDatabase();
                    response.addProperty("response", "OK");
                } finally {
                    lock.writeLock().unlock();
                }
                break;
            case "get":
                lock.readLock().lock();
                try {
                    String key = request.get("key").getAsString();
                    if (database.containsKey(key)) {
                        response.addProperty("response", "OK");
                        response.addProperty("value", database.get(key));
                    } else {
                        response.addProperty("response", "ERROR");
                        response.addProperty("reason", "No such key");
                    }
                } finally {
                    lock.readLock().unlock();
                }
                break;
            case "delete":
                lock.writeLock().lock();
                try {
                    String key = request.get("key").getAsString();
                    if (database.containsKey(key)) {
                        database.remove(key);
                        saveDatabase();
                        response.addProperty("response", "OK");
                    } else {
                        response.addProperty("response", "ERROR");
                        response.addProperty("reason", "No such key");
                    }
                } finally {
                    lock.writeLock().unlock();
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

    private static void saveDatabase() {
        lock.writeLock().lock();
        try (FileWriter writer = new FileWriter(DB_PATH)) {
            gson.toJson(database, writer);
        } catch (IOException e) {
            System.out.println("Could not save database.");
        } finally {
            lock.writeLock().unlock();
        }
    }
}



