package lastpencil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello World!");
// Specify the number of pencils
        int numberOfPencils = 8; // You can change this number for different outputs

        // Create a string of vertical bars representing pencils
        String pencils = "|".repeat(numberOfPencils);

        // Print the pencils and the "Your turn!" message
        System.out.println(pencils);
        System.out.println("Your turn!");

    }
}
