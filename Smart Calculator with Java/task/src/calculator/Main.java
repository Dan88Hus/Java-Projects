package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
//            System.out.print("> ");
            input = scanner.nextLine().trim();

            // Exit condition
            if (input.equals("/exit")) {
                System.out.println("Bye!");
                break;
            }

            // Help command
            if (input.equals("/help")) {
                System.out.println("This is a simple calculator that supports addition (+) and subtraction (-).");
                System.out.println("It handles unary and binary minus operators, and sequences of minus signs are treated as follows:");
                System.out.println("  -- becomes +, --- becomes -, ---- becomes +, and so on.");
                continue;
            }

            // Empty line, no output
            if (input.isEmpty()) {
                continue;
            }

            // Process and evaluate the expression
            try {
                int result = evaluateExpression(input);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Invalid expression");
            }
        }

        scanner.close();
    }

    // Evaluate the expression
    public static int evaluateExpression(String expression) {
        // Replace sequences of minus signs based on the rule
        expression = replaceMinusSigns(expression);

        // Now evaluate the expression safely
        return evaluateMathExpression(expression);
    }

    // Replace sequences of minus signs
    public static String replaceMinusSigns(String expression) {
        Pattern pattern = Pattern.compile("(-{2})+");  // Matches two or more consecutive minus signs
        Matcher matcher = pattern.matcher(expression);

        // Replace all matches
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            // If the match length is even, replace with '+' else replace with '-'
            String replacement = (matcher.group().length() % 2 == 0) ? "+" : "-";
            matcher.appendReplacement(result, replacement);
        }
        matcher.appendTail(result);  // Append any remaining part of the expression
        return result.toString();
    }

    // Simple method to evaluate the math expression (+, -)
    public static int evaluateMathExpression(String expression) {
        // Split the expression by + or -
        String[] tokens = expression.split("(?=[+-])|(?<=[+-])");

        int result = 0;
        int currentSign = 1;  // Start with positive sign

        for (String token : tokens) {
            if (token.isEmpty()) continue;
            token = token.trim();

            if (token.equals("+")) {
                currentSign = 1;
            } else if (token.equals("-")) {
                currentSign = -1;
            } else {
                // Parse the number and apply the sign
                result += currentSign * Integer.parseInt(token);
            }
        }
        return result;
    }
}
