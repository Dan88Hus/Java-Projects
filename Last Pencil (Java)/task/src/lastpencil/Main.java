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

        // Create a string of vertical bars representing pencils
        String pencils = "|".repeat(numberOfPencils);

        // Print the pencils and the first player's name
        System.out.println(pencils);
        System.out.println(firstPlayer + " is going first!");

        // Close the scanner
        scanner.close();
    }
}
