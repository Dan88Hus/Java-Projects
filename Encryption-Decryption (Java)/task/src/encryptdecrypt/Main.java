package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Default values
        String mode = "enc";
        int key = 0;
        String data = "";
        String inputFile = null;
        String outputFile = null;

        // Parse command-line arguments
        for (int i = 0; i < args.length; i += 2) {
            if (i + 1 >= args.length) {
                break; // Prevent index out of bounds
            }
            String arg = args[i];
            String value = args[i + 1];

            switch (arg) {
                case "-mode":
                    mode = value;
                    break;
                case "-key":
                    try {
                        key = Integer.parseInt(value);
                    } catch (NumberFormatException e) {
                        // If parsing fails, keep the default value
                    }
                    break;
                case "-data":
                    data = value;
                    break;
                case "-in":
                    inputFile = value;
                    break;
                case "-out":
                    outputFile = value;
                    break;
            }
        }
        // Get input data (prefer -data over -in)
        if (data.isEmpty() && inputFile != null) {
            data = readFromFile(inputFile);
        }

// Process the data based on mode
        String result;
        if ("enc".equals(mode)) {
            result = encrypt(data, key);
        } else if ("dec".equals(mode)) {
            result = decrypt(data, key);
        } else {
            result = "Unknown mode: " + mode;
        }

        if (outputFile != null) {
            writeToFile(outputFile, result);
        } else {
            System.out.println(result);
        }
    }

    private static void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error: Could not write to output file: " + fileName);
        }
    }

    private static String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        File file = new File(fileName);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine());
                if (scanner.hasNextLine()) {
                    content.append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found: " + fileName);
            return "";
        }

        return content.toString();
    }

    private static String decrypt(String ciphertext, int key) {
        StringBuilder result = new StringBuilder();

        for (char c : ciphertext.toCharArray()) {
            // Get integer representation and shift it backward
            int originalCode = c;
            int shiftedCode = originalCode - key;

            // Convert back to character and append
            result.append((char) shiftedCode);
        }
        return result.toString();
    }

    private static String encrypt(String message, int key) {
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()){
            // Get integer representation and shift it
            int originalCode = c;
            int shiftedCode = originalCode + key;

            // Convert back to character and append
            result.append((char) shiftedCode);
        }
        return result.toString();
    }
    
}
