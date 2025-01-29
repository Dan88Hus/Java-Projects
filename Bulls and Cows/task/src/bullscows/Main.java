package bullscows;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    private static String secretCode;
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        int length = scanner.nextInt();
        scanner.close();

        String secretNumber = generateSecretNumber(length);

        if (secretNumber != null) {
            System.out.println("The random secret number is " + secretNumber + ".");
        }
    }

    public static String generateSecretNumber(int length) {
        if (length > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + length + " because there aren't enough unique digits.");
            return null;
        }

        while (true) {
            long pseudoRandomNumber = System.nanoTime();
            String numberString = new StringBuilder(Long.toString(pseudoRandomNumber)).reverse().toString();
            HashSet<Character> uniqueDigits = new HashSet<>();
            StringBuilder secretCode = new StringBuilder();

            for (char digit : numberString.toCharArray()) {
                if (uniqueDigits.size() == length) break;
                if (!uniqueDigits.contains(digit) && (secretCode.length() > 0 || digit != '0')) {
                    uniqueDigits.add(digit);
                    secretCode.append(digit);
                }
            }

            if (secretCode.length() == length) {
                return secretCode.toString();
            }
        }
    }
}
