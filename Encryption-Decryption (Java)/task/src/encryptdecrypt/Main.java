package encryptdecrypt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the input message
        String message = scanner.nextLine();
        // Read the shift value (key)
        int key = scanner.nextInt();

        String encrypted = encrypt(message, key);
        System.out.println(encrypted);
        scanner.close();
    }

    private static String encrypt(String message, int key) {
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()){
            if (c>= 'a' && c<='z'){
                char encryptedChar = (char) ('a' + (c - 'a' + key) % 26);
                result.append(encryptedChar);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
