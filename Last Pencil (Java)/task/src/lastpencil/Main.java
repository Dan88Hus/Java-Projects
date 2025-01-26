package lastpencil;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Ask for the initial number of pencils
        int pencils = 0;
        while (true) {
            System.out.println("How many pencils would you like to use:");
            String input = scanner.nextLine();
            try {
                pencils = Integer.parseInt(input);
                if (pencils <= 0) {
                    System.out.println("The number of pencils should be positive");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("The number of pencils should be numeric");
            }
        }

        // Ask for the first player
        String firstPlayer = "";
        while (true) {
            System.out.println("Who will be the first (John, Jack):");
            firstPlayer = scanner.nextLine();
            if (firstPlayer.equals("John") || firstPlayer.equals("Jack")) {
                break;
            } else {
                System.out.println("Choose between 'John' and 'Jack'");
            }
        }

        // Game loop
        String currentPlayer = firstPlayer;
        while (pencils > 0) {
            // Display pencils
            for (int i = 0; i < pencils; i++) {
                System.out.print("|");
            }
            System.out.println();

            // Determine and execute the move
            if (currentPlayer.equals("Jack")) {
                System.out.println("Jack's turn:");
                int botMove = calculateBotMove(pencils, random);
                System.out.println(botMove);
                pencils -= botMove;
            } else {
                System.out.println("John's turn!");
                int playerMove = 0;
                while (true) {
                    String input = scanner.nextLine();
                    try {
                        playerMove = Integer.parseInt(input);
                        if (playerMove < 1 || playerMove > 3) {
                            System.out.println("Possible values: '1', '2' or '3'");
                        } else if (playerMove > pencils) {
                            System.out.println("Too many pencils were taken");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Possible values: '1', '2' or '3'");
                    }
                }
                pencils -= playerMove;
            }
            // Check if the game is over
            if (pencils == 0) {
                System.out.println(currentPlayer.equals("John") ? "Jack won!" : "John won!");
                break;
            }

            // Switch players
            currentPlayer = currentPlayer.equals("John") ? "Jack" : "John";
        }

        scanner.close();
    }

    // Bot's winning strategy
    private static int calculateBotMove(int pencils, Random random) {
        if (pencils % 4 == 0) {
            return 3;
        } else if (pencils % 4 == 3) {
            return 2;
        } else if (pencils % 4 == 2) {
            return 1;
        } else {
            // Losing position, take a random move
            return random.nextInt(3) + 1;
        }
    }
}
