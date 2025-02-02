package calculator;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();

            // Skip processing if input is empty
            if (input.isEmpty()) {
                continue;
            }

            if (!processInput(input)) {
                break;
            }
        }

        scanner.close();
    }

    public static boolean processInput(String input) {
        // Check if it's a command (starts with '/')
        if (input.startsWith("/")) {
            if (input.equals("/help")) {
                System.out.println("This is a simple calculator. You can enter mathematical expressions using + and - operators.");
            } else if (input.equals("/exit")) {
                System.out.println("Bye!");
                return false; // Exit the program
            } else {
                System.out.println("Unknown command");
            }
        } else {
            // Handle mathematical expression
            String preprocessedInput = preprocessExpression(input);
            if (preprocessedInput.matches("[0-9\\+\\-\\s]+") && isValidExpression(preprocessedInput)) {
                try {
                    int result = evaluateExpression(preprocessedInput);
                    System.out.println(result);
                } catch (Exception e) {
                    System.out.println("Invalid expression");
                }
            } else if (isSingleNumber(preprocessedInput)) {
                System.out.println(preprocessedInput); // Output the number as is
            } else {
                System.out.println("Invalid expression");
            }
        }

        return true; // Keep the program running
    }

    private static String preprocessExpression(String expression) {
// Normalize spaces
        String processed = expression.replaceAll("\\s+", " ");

        // Reduce multiple '+' signs to a single '+'
        processed = processed.replaceAll("\\++", "+");

        // Reduce multiple '-' signs:
        while (processed.contains("--")) {
            processed = processed.replaceAll("--", "+");
        }
        while (processed.contains("-+")) {
            processed = processed.replaceAll("-+", "-");
        }
        while (processed.contains("+-")) {
            processed = processed.replaceAll("\\+-", "-");
        }
        while (processed.contains("++")) {
            processed = processed.replaceAll("\\+\\+", "+");
        }

        // Remove leading '+'
        processed = processed.replaceAll("^\\+", "");

        // Remove any leading or trailing spaces
        return processed.trim();
    }

    // Check if the expression has the proper format (numbers with operators in between)
    public static boolean isValidExpression(String input) {
        // Split by spaces and check if there is at least one operator
        String[] tokens = input.split("\\s+");
        boolean hasOperator = false;

        // Check if the first token is a valid number or a negative/positive sign followed by a number
        if (tokens.length == 0 || (!isNumber(tokens[0]) && !tokens[0].equals("-") && !tokens[0].equals("+"))) {
            return false; // Invalid start
        }

        for (int i = 0; i < tokens.length; i++) {
            // If it's an operator, make sure it follows a number
            if (tokens[i].equals("+") || tokens[i].equals("-")) {
                hasOperator = true;
                // Ensure the previous token was a number (valid chain)
                if (i == 0 || !isNumber(tokens[i - 1])) {
                    return false; // Invalid operator usage
                }
            } else if (!isNumber(tokens[i])) {
                return false; // Invalid token (not a number or operator)
            }
        }

        // Check if the last token is a valid number
        if (!isNumber(tokens[tokens.length - 1])) {
            return false; // Invalid end
        }

        return hasOperator; // Ensure that there was at least one operator
    }

    // Helper to check if a string is a valid number
    public static boolean isNumber(String token) {
        try {
            Integer.parseInt(token); // Attempt to parse as an integer
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if the input is a single number (no operators)
    public static boolean isSingleNumber(String input) {
        return input.matches("[+-]?\\d+"); // Matches any single integer (positive or negative)
    }

    public static int evaluateExpression(String expression) throws Exception {
        String[] tokens = expression.split(" ");
        int result = 0;
        boolean isNegative = false;

        for (String token : tokens) {
            if (token.equals("+")) {
                continue;
            } else if (token.equals("-")) {
                isNegative = true;
            } else {
                try {
                    int num = Integer.parseInt(token);
                    if (isNegative) {
                        result -= num;
                        isNegative = false;
                    } else {
                        result += num;
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid expression");
                }
            }
        }
        return result;
    }
}
