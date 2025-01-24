package lastpencil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfPencils = 0;
        String firstPlayer = "";
        String secondPlayer = "";

        // Input for the number of pencils
        while (true) {
            System.out.println("How many pencils would you like to use:");
            String input = scanner.nextLine();
            try {
                numberOfPencils = Integer.parseInt(input);
                if (numberOfPencils <= 0) {
                    System.out.println("The number of pencils should be positive");
                    continue;
                }
                break; // Valid input, exit the loop
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }

        // Input for the first player
        while (true) {
            System.out.println("Who will be the first (John, Jack):");
            firstPlayer = scanner.nextLine();
            if (firstPlayer.equals("John")) {
                secondPlayer = "Jack";
                break;
            } else if (firstPlayer.equals("Jack")) {
                secondPlayer = "John";
                break;
            } else {
                System.out.println("Choose between 'John' and 'Jack'");
            }
        }

        // Game loop
        String currentPlayer = firstPlayer;
        while (numberOfPencils > 0) {
            // Print the current state of pencils
            String pencils = "|".repeat(numberOfPencils);
            System.out.println(pencils);
            System.out.println(currentPlayer + "'s turn!"); // Indicate whose turn it is

            // Input for the number of pencils to take
            int pencilsToTake = 0;
            while (true) {
                String input = scanner.nextLine();
                try {
                    pencilsToTake = Integer.parseInt(input);
                    if (pencilsToTake < 1 || pencilsToTake > 3) {
                        System.out.println("Possible values: '1', '2' or '3'");
                        continue;
                    }
                    if (pencilsToTake > numberOfPencils) {
                        System.out.println("Too many pencils were taken");
                        continue;
                    }
                    break; // Valid input, exit the loop
                } catch (NumberFormatException e) {
                    System.out.println("Possible values: '1', '2' or '3'");
                }
            }

            // Remove the pencils
            numberOfPencils -= pencilsToTake;
            // Switch players
            currentPlayer = currentPlayer.equals(firstPlayer) ? secondPlayer : firstPlayer;

            // Check if the game is over
            if (numberOfPencils <= 0) {
                System.out.println(currentPlayer + "'s won!");
                break; // Exit the loop if no pencils remain
            }


        }

        // Close the scanner
        scanner.close();
    }
}
