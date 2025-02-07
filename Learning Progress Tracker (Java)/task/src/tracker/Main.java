package tracker;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Learning Progress Tracker");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("No input.");
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("Error: unknown command!");
            }
        }

        scanner.close();
    }
}
