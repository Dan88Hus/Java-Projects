package calculator;

import java.util.*;

public class Main {
    private static final Map<String, Integer> variables = new HashMap<>();
    private static final Map<String, Integer> precedence = Map.of(
            "+", 1, "-", 1, "*", 2, "/", 2, "^", 3
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;
            if (!processInput(input)) break;
        }

        scanner.close();
    }

    private static boolean processInput(String input) {
        if (input.startsWith("/")) {
            return processCommand(input);
        } else if (input.contains("=")) {
            processAssignment(input);
        } else {
            processExpression(input);
        }
        return true;
    }

    private static boolean processCommand(String input) {
        if (input.equals("/help")) {
            System.out.println("Supported operations: +, -, *, /, ^ (power), parentheses (). Variables are allowed.");
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

    private static void processExpression(String expression) {
        try {
            expression = preprocessExpression(expression);
            expression = replaceVariables(expression);
            String postfix = infixToPostfix(expression);
            int result = evaluatePostfix(postfix);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid expression");
        }
    }

    private static String preprocessExpression(String expression) {
        expression = expression.replaceAll("\\s+", ""); // Remove spaces
        expression = expression.replaceAll("\\++", "+"); // Convert multiple '+' to single '+'
        expression = expression.replaceAll("--", "+"); // Convert '--' to '+'
        expression = expression.replaceAll("-\\+", "-"); // Convert '-+' to '-'
        expression = expression.replaceAll("\\+-", "-"); // Convert '+-' to '-'
        expression = expression.replaceAll("\\-\\-\\-+", "-"); // Convert '---' to '-'
        expression = expression.replaceAll("\\+\\+\\++", "+"); // Convert '+++' to '+'
        return expression;
    }

    private static String replaceVariables(String expression) {
        StringBuilder newExpression = new StringBuilder();
        StringBuilder variable = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isLetter(ch)) {
                variable.append(ch);
            } else {
                if (variable.length() > 0) {
                    String varName = variable.toString();
                    if (variables.containsKey(varName)) {
                        newExpression.append(variables.get(varName)); // Replace variable with value
                    } else {
                        throw new IllegalArgumentException("Unknown variable");
                    }
                    variable.setLength(0);
                }
                newExpression.append(ch);
            }
        }

        if (variable.length() > 0) {
            String varName = variable.toString();
            if (variables.containsKey(varName)) {
                newExpression.append(variables.get(varName));
            } else {
                throw new IllegalArgumentException("Unknown variable");
            }
        }

        return newExpression.toString();
    }

    private static String infixToPostfix(String infix) {
        List<String> tokens = tokenize(infix);
        StringBuilder output = new StringBuilder();
        Deque<String> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                output.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.append(stack.pop()).append(" ");
                }
                if (stack.isEmpty()) throw new IllegalArgumentException();
                stack.pop();
            } else if (precedence.containsKey(token)) {
                while (!stack.isEmpty() && precedence.getOrDefault(stack.peek(), 0) >= precedence.get(token)) {
                    output.append(stack.pop()).append(" ");
                }
                stack.push(token);
            } else {
                throw new IllegalArgumentException();
            }
        }

        while (!stack.isEmpty()) {
            String op = stack.pop();
            if (op.equals("(")) throw new IllegalArgumentException();
            output.append(op).append(" ");
        }

        return output.toString().trim();
    }

    private static int evaluatePostfix(String postfix) {
        Deque<Integer> stack = new ArrayDeque<>();
        List<String> tokens = Arrays.asList(postfix.split(" "));

        for (String token : tokens) {
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                if (stack.size() < 2) throw new IllegalArgumentException();
                int b = stack.pop();
                int a = stack.pop();
                switch (token) {
                    case "+": stack.push(a + b); break;
                    case "-": stack.push(a - b); break;
                    case "*": stack.push(a * b); break;
                    case "/":
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        stack.push(a / b); break;
                    case "^": stack.push((int) Math.pow(a, b)); break;
                    default: throw new IllegalArgumentException();
                }
            }
        }

        if (stack.size() != 1) throw new IllegalArgumentException();
        return stack.pop();
    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder number = new StringBuilder();
        boolean lastWasOperator = true;

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                number.append(ch);
                lastWasOperator = false;
            } else {
                if (number.length() > 0) {
                    tokens.add(number.toString());
                    number.setLength(0);
                }

                if (precedence.containsKey(String.valueOf(ch))) {
                    if (ch == '-' && lastWasOperator) {
                        number.append(ch);
                    } else {
                        tokens.add(String.valueOf(ch));
                        lastWasOperator = true;
                    }
                } else {
                    tokens.add(String.valueOf(ch));
                }
            }
        }

        if (number.length() > 0) tokens.add(number.toString());
        return tokens;
    }

    private static boolean isValidIdentifier(String str) {
        return str.matches("[a-zA-Z]+");
    }

    private static boolean isNumber(String str) {
        return str.matches("-?\\d+");
    }
}
