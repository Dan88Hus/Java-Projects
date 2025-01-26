package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        // Print the input string first
        System.out.println("Input string:");
//        System.out.print(">");
        String input = scanner.nextLine();

        StringBuilder binaryString = new StringBuilder();
        for (char c : input.toCharArray()) {
            // Convert each character to a 7-bit binary string and append it to the binaryString
            String binary = String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0');
            binaryString.append(binary);
        }

//        System.out.println("Binary representation of the input string:");
//        System.out.println(binaryString.toString());


        // Apply Chuck Norris encoding to the binary string
        String encoded = encodeChuckNorris(binaryString.toString());

        // Output the encoded message, trimming the trailing space
        // Print the encoded string for the current character
        System.out.println("The result:");
        System.out.println(encoded);
        
    }

    private static String encodeChuckNorris(String binary) {
        StringBuilder encoded = new StringBuilder();
        char prevChar = binary.charAt(0);
        int count = 1;

        // Loop through the binary string
        for (int i = 1; i < binary.length(); i++) {
            char currentChar = binary.charAt(i);
            if (currentChar == prevChar) {
                count++;
            } else {
// Add the current series to the encoded string
                if (prevChar == '1') {
                    encoded.append("0 ");  // 0 means 1s
                } else {
                    encoded.append("00 "); // 00 means 0s
                }
                encoded.append("0".repeat(count));  // Add the count of 1s or 0s

                // Reset the count for the new series
                prevChar = currentChar;
                count = 1;
            }
        }
        // Add the last series
        if (prevChar == '1') {
            encoded.append("0 ");  // 0 means 1s
        } else {
            encoded.append("00 "); // 00 means 0s
        }
        encoded.append("0".repeat(count));  // Add the count of 1s or 0s

        return encoded.toString();
    }

}
