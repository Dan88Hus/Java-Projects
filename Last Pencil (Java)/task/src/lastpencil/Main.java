package lastpencil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask for the number of pencils
        System.out.println("How many pencils would you like to use:");
        int numberOfPencils = scanner.nextInt();

        // Clear the newline character from the input buffer
        scanner.nextLine();

        // Ask for the name of the player who goes first
        System.out.println("Who will be the first (John, Jack):");
        String firstPlayer = scanner.nextLine();
        String secondPlayer = firstPlayer.equals("John") ? "Jack" : "John";

        // Game loop
        String currentPlayer = firstPlayer;
        while (numberOfPencils > 0) {
            // Print the current state of pencils
            String pencils = "|".repeat(numberOfPencils);
            System.out.println(pencils);

            // Print whose turn it is
            System.out.println(currentPlayer + "'s turn:");

            // Read the number of pencils to take
            int pencilsToTake = scanner.nextInt();

            // Validate the number of pencils to take
            if (pencilsToTake > numberOfPencils) {
                System.out.println("Error: You cannot take more pencils than are available.");
                continue; // Skip to the next iteration
            }

            // Remove the pencils
            numberOfPencils -= pencilsToTake;

            // Check if the game is over
            if (numberOfPencils <= 0) {
                break; // Exit the loop if no pencils remain
            }

            // Switch players
            currentPlayer = currentPlayer.equals(firstPlayer) ? secondPlayer : firstPlayer;
        }

        // Close the scanner
        scanner.close();
    }
}
