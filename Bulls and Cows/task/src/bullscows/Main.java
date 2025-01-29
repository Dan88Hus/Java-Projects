package bullscows;

import java.util.HashSet;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, enter the secret code's length:");
        System.out.print("> ");
        int length = scanner.nextInt();

        String secretNumber = generateSecretNumber(length);
        if (secretNumber == null) {
            return;
        }

        System.out.println("Okay, let's start a game!");
        int turn = 1;

        while (true) {
            System.out.println("Turn " + turn + ":");
            System.out.print("> ");
            String guess = scanner.next();

            int bulls = countBulls(secretNumber, guess);
            int cows = countCows(secretNumber, guess) - bulls;

            System.out.println("Grade: " + bulls + (bulls == 1 ? " bull" : " bulls") + " and " + cows + (cows == 1 ? " cow" : " cows"));

            if (bulls == length) {
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            }
            turn++;
        }

        scanner.close();
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

    public static int countBulls(String secret, String guess) {
        int bulls = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    public static int countCows(String secret, String guess) {
        int cows = 0;
        for (char c : guess.toCharArray()) {
            if (secret.contains(Character.toString(c))) {
                cows++;
            }
        }
        return cows;
    }
}
