package chucknorris;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Input the encoded string
        System.out.println("Input encoded string:");
        String encoded = scanner.nextLine();

        // Split the encoded string into blocks of zeros
        String[] blocks = encoded.split(" ");

        // Decode the blocks into binary
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < blocks.length; i += 2) {
            String prefix = blocks[i];
            String sequence = blocks[i + 1];

            if (prefix.equals("0")) {
                binary.append("1".repeat(sequence.length()));
            } else if (prefix.equals("00")) {
                binary.append("0".repeat(sequence.length()));
            }
        }

        // Split the binary string into chunks of 7 bits
        List<String> binaryChunks = new ArrayList<>();
        for (int i = 0; i < binary.length(); i += 7) {
            int end = Math.min(i + 7, binary.length());
            binaryChunks.add(binary.substring(i, end));
        }

        // Convert each 7-bit chunk into a character
        StringBuilder decodedMessage = new StringBuilder();
        for (String chunk : binaryChunks) {
            int charCode = Integer.parseInt(chunk, 2);
            decodedMessage.append((char) charCode);
        }

        // Output the decoded message
        System.out.println("The result:");
        System.out.println(decodedMessage.toString());
    }
}
