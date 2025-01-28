package chucknorris;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String operation;

        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            operation = scanner.nextLine().trim();

            if (operation.equalsIgnoreCase("encode")) {
                System.out.println("Input string:");
                String input = scanner.nextLine();
                System.out.println("Encoded string:");
                System.out.println(encode(input));
            } else if (operation.equalsIgnoreCase("decode")) {
                System.out.println("Input encoded string:");
                String input = scanner.nextLine();
                String result = decode(input);
                if (result == null) {
                    System.out.println("Encoded string is not valid.");
                } else {
                    System.out.println("Decoded string:");
                    System.out.println(result);
                }
            } else if (operation.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("There is no '" + operation + "' operation");
            }
        }

        scanner.close();
    }

    private static String encode(String input) {
        StringBuilder binaryString = new StringBuilder();
        for (char c : input.toCharArray()) {
            binaryString.append(String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0'));
        }

        StringBuilder encoded = new StringBuilder();
        char prevChar = ' ';
        for (char c : binaryString.toString().toCharArray()) {
            if (c != prevChar) {
                if (c == '0') {
                    encoded.append(" 00 0");
                } else {
                    encoded.append(" 0 0");
                }
            } else {
                encoded.append("0");
            }
            prevChar = c;
        }

        return encoded.toString().trim();
    }

    private static String decode(String input) {
        if (!isValidEncodedString(input)) {
            return null;
        }

        StringBuilder binaryString = new StringBuilder();
        String[] parts = input.split(" ");
        for (int i = 0; i < parts.length; i += 2) {
            String prefix = parts[i];
            String zeros = parts[i + 1];

            if (prefix.equals("0")) {
                binaryString.append("1".repeat(zeros.length()));
            } else if (prefix.equals("00")) {
                binaryString.append("0".repeat(zeros.length()));
            }
        }

        if (binaryString.length() % 7 != 0) {
            return null;
        }

        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 7) {
            String byteString = binaryString.substring(i, i + 7);
            decoded.append((char) Integer.parseInt(byteString, 2));
        }

        return decoded.toString();
    }

    private static boolean isValidEncodedString(String input) {
        String[] parts = input.split(" ");
        if (parts.length % 2 != 0) {
            return false;
        }

        for (int i = 0; i < parts.length; i += 2) {
            String prefix = parts[i];
            String zeros = parts[i + 1];

            if (!prefix.equals("0") && !prefix.equals("00")) {
                return false;
            }
            if (!zeros.matches("0+")) {
                return false;
            }
        }

        return true;
    }
}
