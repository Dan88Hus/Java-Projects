package bullscows;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final String SYMBOLS = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        System.out.print("> ");
        String lengthInput = scanner.nextLine();
        if (!isValidInteger(lengthInput)) {
            System.out.println("Error: \"" + lengthInput + "\" isn't a valid number.");
            return;
        }
        int length = Integer.parseInt(lengthInput);

        System.out.println("Input the number of possible symbols in the code:");
        System.out.print("> ");
        String rangeInput = scanner.nextLine();
        if (!isValidInteger(rangeInput)) {
            System.out.println("Error: \"" + rangeInput + "\" isn't a valid number.");
            return;
        }
        int range = Integer.parseInt(rangeInput);

        // Check if the length is greater than the range
        if (length > range) {
            System.out.println("Error: it's not possible to generate a code with a length of " + length + " with " + range + " unique symbols.");
            return;
        }

        // Check if the range exceeds the maximum allowed symbols
        if (range > SYMBOLS.length()) {
            System.out.println("Error: maximum number of possible symbols in the code is " + SYMBOLS.length() + " (0-9, a-z).");
            return;
        }

        // Generate the secret code
        String secretNumber = generateSecretCode(length, range);

        // Prepare the game
        String visibleSecret = "*".repeat(length);
        String usedSymbols = SYMBOLS.substring(0, range);
        System.out.println("The secret is prepared: " + visibleSecret + " (" + usedSymbols.charAt(0) + "-" + usedSymbols.charAt(usedSymbols.length() - 1) + ").");
        System.out.println("Okay, let's start a game!");

        int turn = 1;
        while (true) {
            System.out.println("Turn " + turn + ":");
            System.out.print("> ");
            String guess = scanner.nextLine();

            // Check if the guess length is correct
            if (guess.length() != length) {
                System.out.println("Error: guess length must be " + length + " characters.");
                break;
            }

            // Check if the guess contains valid symbols
            if (!isValidGuess(guess, range)) {
                System.out.println("Error: guess contains invalid symbols.");
                break;
            }

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

    public static String generateSecretCode(int length, int range) {
        Random random = new Random();
        HashSet<Character> uniqueSymbols = new HashSet<>();
        StringBuilder secretCode = new StringBuilder();

        while (uniqueSymbols.size() < length) {
            char symbol = SYMBOLS.charAt(random.nextInt(range));
            if (uniqueSymbols.add(symbol)) {
                secretCode.append(symbol);
            }
        }

        return secretCode.toString();
    }

    public static int countBulls(String secret, String guess) {
        int bulls = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (i < guess.length() && secret.charAt(i) == guess.charAt(i)) {
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

    // Helper method to check if a string is a valid integer
    private static boolean isValidInteger(String input) {
        if (input.isEmpty()) return false;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    // Helper method to check if the guess contains valid symbols
    private static boolean isValidGuess(String guess, int range) {
        for (char c : guess.toCharArray()) {
            if (SYMBOLS.indexOf(c) >= range) {
                return false; // Invalid symbol
            }
        }
        return true; // All symbols are valid
    }
}
