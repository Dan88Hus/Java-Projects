package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        while (true) {
//            System.out.print("> "); // Prompt for user input
            String input = scanner.nextLine().trim(); // Read input and trim whitespace

            // Check for exit command
            if (input.equals("/exit")) {
                System.out.println("Bye!");
                break; // Exit the loop
            }

            // If the input is empty, ignore it
            if (input.isEmpty()) {
                continue; // Skip to the next iteration
            }

            // Split the input into parts
            String[] parts = input.split("\\s+"); // Split by whitespace

            // Handle different cases based on the number of inputs
            if (parts.length == 1) {
                // If only one number is provided
                try {
                    int singleNumber = Integer.parseInt(parts[0]);
                    System.out.println(singleNumber); // Print the single number
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid integers.");
                }
            } else if (parts.length >= 2) {
                // If two or more numbers are provided
                try {
                    int num1 = Integer.parseInt(parts[0]);
                    int num2 = Integer.parseInt(parts[1]);
                    int sum = num1 + num2;
                    System.out.println(sum); // Print the sum
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter valid integers.");
                }
            }
        }

    }
}
