package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Type /help for instructions or /exit to quit.");

        while (true) {
//            System.out.print("> "); // Prompt for user input
            String input = scanner.nextLine().trim(); // Read input and trim whitespace

            // Check for exit command
            if (input.equals("/exit")) {
                System.out.println("Bye!");
                break; // Exit the loop
            }

            // Check for help command
            if (input.equals("/help")) {
                System.out.println("The program calculates the sum of numbers.");
                continue; // Skip to the next iteration
            }

            // If the input is empty, ignore it
            if (input.isEmpty()) {
                continue; // Skip to the next iteration
            }

            // Split the input into parts
            String[] parts = input.split("\\s+"); // Split by whitespace
            int sum = 0;
            boolean validInput = true; // Flag to check if all inputs are valid

            // Iterate through the parts to calculate the sum
            for (String part : parts) {
                try {
                    int number = Integer.parseInt(part);
                    sum += number; // Add to the sum
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid integers.");
                    validInput = false; // Set flag to false if any input is invalid
                    break; // Exit the loop on invalid input
                }
            }

            // Print the sum if all inputs were valid
            if (validInput) {
                System.out.println(sum);
            }
        }
    }
}
