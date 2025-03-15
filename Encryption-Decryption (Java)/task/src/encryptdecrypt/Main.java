package encryptdecrypt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Default values
        String mode = "enc";
        int key = 0;
        String data = "";

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
            }
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

        System.out.println(result);
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
