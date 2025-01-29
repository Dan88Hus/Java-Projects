package bullscows;

import java.util.Scanner;

public class Main {
    private static final String SECRET_CODE = "9305";
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a 4-digit number:");
        String guess = scanner.next();
        scanner.close();

        gradeGuess(guess);
        
    }

    private static void gradeGuess(String guess) {
        int bulls = 0, cows = 0;

        for (int i = 0; i < SECRET_CODE.length(); i++) {
            if (guess.charAt(i) == SECRET_CODE.charAt(i)) {
                bulls++;
            } else if (SECRET_CODE.contains(String.valueOf(guess.charAt(i)))) {
                cows++;
            }
        }
        System.out.print("Grade: ");
        if (bulls == 0 && cows == 0) {
            System.out.print("None");
        } else {
            if (bulls > 0) {
                System.out.print(bulls + " bull(s)");
            }
            if (bulls > 0 && cows > 0) {
                System.out.print(" and ");
            }
            if (cows > 0) {
                System.out.print(cows + " cow(s)");
            }
        }
        System.out.println(". The secret code is " + SECRET_CODE + ".");


    }
}
