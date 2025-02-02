package calculator;

import java.util.*;

public class Main {
    private static final Map<String, Integer> variables = new HashMap<>();

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
        if (input.startsWith("/")) {
            return processCommand(input);
        } else if (input.contains("=")) {
            processAssignment(input);
        } else if (isValidIdentifier(input)) {
            processVariable(input);
        } else {
            processExpression(input);
        }

        return true;
    }

    private static boolean processCommand(String input) {
        if (input.equals("/help")) {
            System.out.println("This is a calculator that supports basic operations and variable storage.");
        } else if (input.equals("/exit")) {
            System.out.println("Bye!");
            return false;
        } else {
            System.out.println("Unknown command");
        }
        return true;
    }

    private static void processAssignment(String input) {
        String[] parts = input.split("\\s*=\\s*");

        if (parts.length != 2) {
            System.out.println("Invalid assignment");
            return;
        }

        String variable = parts[0];
        String value = parts[1];

        if (!isValidIdentifier(variable)) {
            System.out.println("Invalid identifier");
            return;
        }

        if (isValidIdentifier(value)) {
            if (variables.containsKey(value)) {
                variables.put(variable, variables.get(value));
            } else {
                System.out.println("Unknown variable");
            }
        } else {
            try {
                int num = Integer.parseInt(value);
                variables.put(variable, num);
            } catch (NumberFormatException e) {
                System.out.println("Invalid assignment");
            }
        }
    }

    private static void processVariable(String variable) {
        if (variables.containsKey(variable)) {
            System.out.println(variables.get(variable));
        } else {
            System.out.println("Unknown variable");
        }
    }

    private static void processExpression(String expression) {
        String preprocessedInput = preprocessExpression(expression);

        if (!isValidExpression(preprocessedInput)) {
            System.out.println("Invalid expression");
            return;
        }

        try {
            int result = evaluateExpression(preprocessedInput);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Invalid expression");
        }
    }

    private static String preprocessExpression(String expression) {
        String processed = expression.replaceAll("\\s+", " ");
        processed = processed.replaceAll("\\++", "+");
        while (processed.contains("--")) {
            processed = processed.replaceAll("--", "+");
        }
        while (processed.contains("-+")) {
            processed = processed.replaceAll("-+", "-");
        }
        while (processed.contains("+-")) {
            processed = processed.replaceAll("\\+-", "-");
        }
        processed = processed.replaceAll("^\\+", "");
        return processed.trim();
    }

    private static boolean isValidIdentifier(String input) {
        return input.matches("[a-zA-Z]+");
    }

    private static boolean isValidExpression(String input) {
        return input.matches("[a-zA-Z0-9\\+\\-\\s]+");
    }

    private static int evaluateExpression(String expression) throws Exception {
        String[] tokens = expression.split(" ");
        int result = 0;
        boolean isNegative = false;

        for (String token : tokens) {
            if (token.equals("+")) {
                continue;
            } else if (token.equals("-")) {
                isNegative = true;
            } else {
                int num;
                if (isValidIdentifier(token)) {
                    if (variables.containsKey(token)) {
                        num = variables.get(token);
                    } else {
                        throw new Exception("Unknown variable");
                    }
                } else {
                    num = Integer.parseInt(token);
                }

                if (isNegative) {
                    result -= num;
                    isNegative = false;
                } else {
                    result += num;
                }
            }
        }
        return result;
    }
}
