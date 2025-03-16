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
        String algorithm = "shift"; // Default algorithm

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
                case "-alg":
                    algorithm = value;
                    break;
            }
        }

        // Get input data (prefer -data over -in)
        if (data.isEmpty() && inputFile != null) {
            data = readFromFile(inputFile);
        }

        // Process the data based on mode and algorithm
        String result;
        if ("enc".equals(mode)) {
            if ("shift".equals(algorithm)) {
                result = encryptShift(data, key);
            } else if ("unicode".equals(algorithm)) {
                result = encryptUnicode(data, key);
            } else {
                System.out.println("Error: Unknown algorithm: " + algorithm);
                return;
            }
        } else if ("dec".equals(mode)) {
            if ("shift".equals(algorithm)) {
                result = decryptShift(data, key);
            } else if ("unicode".equals(algorithm)) {
                result = decryptUnicode(data, key);
            } else {
                System.out.println("Error: Unknown algorithm: " + algorithm);
                return;
            }
        } else {
            System.out.println("Error: Unknown mode: " + mode);
            return;
        }

        // Output result
        if (outputFile != null) {
            writeToFile(outputFile, result);
        } else {
            System.out.println(result);
        }
    }

    /**
     * Reads content from a file.
     */
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

    /**
     * Writes content to a file.
     */
    private static void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.println("Error: Could not write to output file: " + fileName);
        }
    }

    /**
     * Encrypts a message using the shift algorithm.
     * Only shifts English letters and wraps around the alphabet.
     */
    public static String encryptShift(String message, int key) {
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                // Lowercase letters
                char shifted = (char) ('a' + (c - 'a' + key) % 26);
                result.append(shifted);
            } else if (c >= 'A' && c <= 'Z') {
                // Uppercase letters
                char shifted = (char) ('A' + (c - 'A' + key) % 26);
                result.append(shifted);
            } else {
                // Non-letter characters remain unchanged
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * Decrypts a message using the shift algorithm.
     * Only shifts English letters and wraps around the alphabet.
     */
    public static String decryptShift(String message, int key) {
        // For decryption, we use a negative key and handle wrapping
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                // Calculate the shifted position (handle negative modulo correctly)
                int shiftedPos = (c - 'a' - key) % 26;
                if (shiftedPos < 0) shiftedPos += 26;

                char shifted = (char) ('a' + shiftedPos);
                result.append(shifted);
            } else if (c >= 'A' && c <= 'Z') {
                // Calculate the shifted position (handle negative modulo correctly)
                int shiftedPos = (c - 'A' - key) % 26;
                if (shiftedPos < 0) shiftedPos += 26;

                char shifted = (char) ('A' + shiftedPos);
                result.append(shifted);
            } else {
                // Non-letter characters remain unchanged
                result.append(c);
            }
        }

        return result.toString();
    }

    /**
     * Encrypts a message using the unicode algorithm.
     * Shifts all characters by the key according to Unicode table.
     */
    public static String encryptUnicode(String message, int key) {
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()) {
            int originalCode = c;
            int shiftedCode = originalCode + key;
            result.append((char) shiftedCode);
        }

        return result.toString();
    }

    /**
     * Decrypts a message using the unicode algorithm.
     * Shifts all characters backward by the key according to Unicode table.
     */
    public static String decryptUnicode(String message, int key) {
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()) {
            int originalCode = c;
            int shiftedCode = originalCode - key;
            result.append((char) shiftedCode);
        }

        return result.toString();
    }
}
