package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("input string:");
//        System.out.print("> ");
        String input = scanner.nextLine();
        System.out.println("The result:");

        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            // Convert the character to its 7-bit binary representation
            String binaryValue = String.format("%7s", Integer.toBinaryString(character)).replace(' ', '0');
            // Print the character and its binary value
            System.out.println(character + " = " + binaryValue);

        }
        scanner.close();
    }
}