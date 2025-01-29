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
        int length = scanner.nextInt();

        System.out.println("Input the number of possible symbols in the code:");
        System.out.print("> ");
        int range = scanner.nextInt();

        if (length > range || range > SYMBOLS.length()) {
            System.out.println("Error: invalid input. The length of the code cannot be greater than the number of possible symbols.");
            return;
        }

        String secretNumber = generateSecretCode(length, range);
        if (secretNumber == null) {
            return;
        }

        String visibleSecret = "*".repeat(length);
        String usedSymbols = SYMBOLS.substring(0, range);
        System.out.println("The secret is prepared: " + visibleSecret + " (" + usedSymbols.charAt(0) + "-" + usedSymbols.charAt(usedSymbols.length() - 1) + ").");
        System.out.println("Okay, let's start a game!");

        int turn = 1;
        while (true) {
            System.out.println("Turn " + turn + ":");
            System.out.print("> ");
            String guess = scanner.next();

            int bulls = countBulls(secretNumber, guess);
            int cows = countCows(secretNumber, guess) - bulls;

            if (bulls == 0 && cows == 0) {
                System.out.println("Grade: None");
            } else if (bulls > 0 && cows > 0) {
                System.out.println("Grade: " + bulls + (bulls == 1 ? " bull" : " bulls") + " and " + cows + (cows == 1 ? " cow" : " cows"));
            } else if (bulls > 0) {
                System.out.println("Grade: " + bulls + (bulls == 1 ? " bull" : " bulls"));
            } else {
                System.out.println("Grade: " + cows + (cows == 1 ? " cow" : " cows"));
            }

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
