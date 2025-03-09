package server;

import com.google.gson.*;
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
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class Main {
    private static final String DB_PATH = System.getProperty("user.dir") + "/src/server/data/db.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static JsonObject database = loadDatabase();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        System.out.println("Server started!");

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static JsonObject loadDatabase() {
        try {
            lock.readLock().lock();
            if (!Files.exists(Paths.get(DB_PATH))) {
                return new JsonObject();
            }
            String content = new String(Files.readAllBytes(Paths.get(DB_PATH)));
            return JsonParser.parseString(content).getAsJsonObject();
        } catch (IOException e) {
            return new JsonObject();
        } finally {
            lock.readLock().unlock();
        }
    }

    private static void saveDatabase() {
        try {
            lock.writeLock().lock();
            Files.write(Paths.get(DB_PATH), gson.toJson(database).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                JsonObject request = JsonParser.parseString(in.readLine()).getAsJsonObject();
                JsonObject response = handleRequest(request);
                out.println(gson.toJson(response));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private JsonObject handleRequest(JsonObject request) {
            JsonObject response = new JsonObject();
            String type = request.get("type").getAsString();

            if ("exit".equals(type)) {
                response.addProperty("response", "OK");
                System.exit(0);
            }

            JsonArray keyPath = request.has("key") ? request.getAsJsonArray("key") : null;
            JsonElement value = request.has("value") ? request.get("value") : null;

            switch (type) {
                case "get":
                    response = handleGet(keyPath);
                    break;
                case "set":
                    response = handleSet(keyPath, value);
                    break;
                case "delete":
                    response = handleDelete(keyPath);
                    break;
                default:
                    response.addProperty("response", "ERROR");
                    response.addProperty("reason", "Invalid request type");
            }
            return response;
        }

        private JsonObject handleGet(JsonArray keyPath) {
            JsonObject response = new JsonObject();
            lock.readLock().lock();
            try {
                JsonElement value = getNestedValue(database, keyPath);
                if (value != null) {
                    response.addProperty("response", "OK");
                    response.add("value", value);
                } else {
                    response.addProperty("response", "ERROR");
                    response.addProperty("reason", "No such key");
                }
            } finally {
                lock.readLock().unlock();
            }
            return response;
        }

        private JsonObject handleSet(JsonArray keyPath, JsonElement value) {
            JsonObject response = new JsonObject();
            lock.writeLock().lock();
            try {
                setNestedValue(database, keyPath, value);
                saveDatabase();
                response.addProperty("response", "OK");
            } finally {
                lock.writeLock().unlock();
            }
            return response;
        }

        private JsonObject handleDelete(JsonArray keyPath) {
            JsonObject response = new JsonObject();
            lock.writeLock().lock();
            try {
                if (removeNestedValue(database, keyPath)) {
                    saveDatabase();
                    response.addProperty("response", "OK");
                } else {
                    response.addProperty("response", "ERROR");
                    response.addProperty("reason", "No such key");
                }
            } finally {
                lock.writeLock().unlock();
            }
            return response;
        }

        private JsonElement getNestedValue(JsonObject json, JsonArray keyPath) {
            JsonElement current = json;
            for (JsonElement key : keyPath) {
                if (current.isJsonObject() && current.getAsJsonObject().has(key.getAsString())) {
                    current = current.getAsJsonObject().get(key.getAsString());
                } else {
                    return null;
                }
            }
            return current;
        }

        private void setNestedValue(JsonObject json, JsonArray keyPath, JsonElement value) {
            JsonObject current = json;
            for (int i = 0; i < keyPath.size() - 1; i++) {
                String key = keyPath.get(i).getAsString();
                if (!current.has(key) || !current.get(key).isJsonObject()) {
                    current.add(key, new JsonObject());
                }
                current = current.getAsJsonObject(key);
            }
            current.add(keyPath.get(keyPath.size() - 1).getAsString(), value);
        }

        private boolean removeNestedValue(JsonObject json, JsonArray keyPath) {
            JsonObject current = json;
            for (int i = 0; i < keyPath.size() - 1; i++) {
                String key = keyPath.get(i).getAsString();
                if (current.has(key) && current.get(key).isJsonObject()) {
                    current = current.getAsJsonObject(key);
                } else {
                    return false;
                }
            }
            return current.remove(keyPath.get(keyPath.size() - 1).getAsString()) != null;
        }
    }
}

