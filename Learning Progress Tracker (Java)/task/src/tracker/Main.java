package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> students = new ArrayList<>();
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]+(?:[-'][A-Za-z]+)*(?: [A-Za-z]+(?:[-'][A-Za-z]+)*)*$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z0-9]{1,}$");

    public static void main(String[] args) {

        System.out.println("Learning Progress Tracker");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("No input.");
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            } else if (input.equalsIgnoreCase("add students")) {
                addStudents();
            } else if (input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else {
                System.out.println("Error: unknown command!");
            }
        }

    }

    private static void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");
        int count = 0;

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("back")) {
                System.out.println("Total " + count + " students have been added.");
                return;
            }
            // Check if the input is empty
            if (input.isEmpty()) {
                System.out.println("Incorrect credentials.");
                continue;
            }

            int lastSpaceIndex = input.lastIndexOf(" "); // count firstname char
            if (lastSpaceIndex == -1) {
                System.out.println("Incorrect credentials.");
                continue;
            }
            String fullName = input.substring(0, lastSpaceIndex);
//            String email = input.substring(lastSpaceIndex + 1).trim();
            String[] emailParts = input.split(" ");
            String email = "";
            if (emailParts.length < 3 ){
                email = "";
            } else {
                email = emailParts[emailParts.length-1].trim();
            }


            // Check if the email is empty
            if (email.isEmpty()) {
                System.out.println("Incorrect credentials.");
                continue;
            }

            String[] nameParts = fullName.split(" ");

            if (nameParts.length < 2) {
                System.out.println("Incorrect last name.");
                continue;
            }

            String firstName = nameParts[0];
            String lastName = fullName.substring(firstName.length() + 1);


            if (!NAME_PATTERN.matcher(firstName).matches() || firstName.length() < 2) {
                System.out.println("Incorrect first name.");
                continue;
            }

            if (!NAME_PATTERN.matcher(lastName).matches() || lastName.length() < 2) {
                System.out.println("Incorrect last name.");
                continue;
            }

            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Incorrect email.");
                continue;
            }

            students.add(input);
            count++;
            System.out.println("The student has been added.");
        }


    }

}
