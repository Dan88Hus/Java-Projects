package tracker;



import java.util.regex.Pattern;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<Integer, Student> students = new LinkedHashMap<>();
    private static final Map<String, Integer> emailToId = new HashMap<>();
    private static int studentIdCounter = 10000;
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
            } else if (input.equalsIgnoreCase("list")) {
                listStudents();
            } else if (input.equalsIgnoreCase("add points")) {
                addPoints();
            } else if (input.equalsIgnoreCase("find")) {
                findStudent();
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

            String[] parts = input.split("\\s+");
            if (parts.length < 3) {
                System.out.println("Incorrect credentials.");
                continue;
            }

            String email = parts[parts.length - 1];
            String fullName = input.substring(0, input.lastIndexOf(" "));

            if (!isValidName(fullName)) {
                System.out.println("Incorrect credentials.");
                continue;
            }

            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Incorrect email.");
                continue;
            }

            if (emailToId.containsKey(email)) {
                System.out.println("This email is already taken.");
                continue;
            }

            Student student = new Student(studentIdCounter++, fullName, email);
            students.put(student.getId(), student);
            emailToId.put(email, student.getId());
            count++;
            System.out.println("The student has been added.");
        }
    }

    private static boolean isValidName(String fullName) {
        String[] nameParts = fullName.split("\\s+");
        if (nameParts.length < 2) return false;

        for (String name : nameParts) {
            if (!NAME_PATTERN.matcher(name).matches() || name.length() < 2) {
                return false;
            }
        }
        return true;
    }

    private static void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            students.keySet().forEach(System.out::println);
        }
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("back")) {
                return;
            }

            String[] parts = input.split("\\s+");
            if (parts.length != 5) {
                System.out.println("Incorrect points format.");
                continue;
            }

            try {
                int studentId = Integer.parseInt(parts[0]);
                if (!students.containsKey(studentId)) {
                    System.out.printf("No student is found for id=%d.%n", studentId);
                    continue;
                }

                int javaPoints = Integer.parseInt(parts[1]);
                int dsaPoints = Integer.parseInt(parts[2]);
                int databasesPoints = Integer.parseInt(parts[3]);
                int springPoints = Integer.parseInt(parts[4]);

                if (javaPoints < 0 || dsaPoints < 0 || databasesPoints < 0 || springPoints < 0) {
                    System.out.println("Incorrect points format.");
                    continue;
                }

                students.get(studentId).updatePoints(javaPoints, dsaPoints, databasesPoints, springPoints);
                System.out.println("Points updated.");
            } catch (NumberFormatException e) {
                System.out.println("Incorrect points format.");
            }
        }
    }

    private static void findStudent() {
        System.out.println("Enter an id or 'back' to return:");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("back")) {
                return;
            }

            try {
                int studentId = Integer.parseInt(input);
                if (students.containsKey(studentId)) {
                    System.out.println(students.get(studentId));
                } else {
                    System.out.printf("No student is found for id=%d.%n", studentId);
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input format.");
            }
        }
    }
}

class Student {
    private final int id;
    private final String fullName;
    private final String email;
    private int javaPoints, dsaPoints, databasesPoints, springPoints;

    public Student(int id, String fullName, String email) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void updatePoints(int java, int dsa, int databases, int spring) {
        this.javaPoints += java;
        this.dsaPoints += dsa;
        this.databasesPoints += databases;
        this.springPoints += spring;
    }

    @Override
    public String toString() {
        return String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                id, javaPoints, dsaPoints, databasesPoints, springPoints);
    }
}
