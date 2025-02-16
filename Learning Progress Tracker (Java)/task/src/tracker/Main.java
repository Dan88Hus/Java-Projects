package tracker;



import java.util.regex.Pattern;
import java.util.*;

public class Main {

    private static final Map<Integer, Student> students = new LinkedHashMap<>();
    private static final Map<String, Integer> courseCompletion = Map.of(
            "Java", 600, "DSA", 400, "Databases", 480, "Spring", 550);
    private static int idCounter = 10000;
    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z]+(?:[-'][A-Za-z]+)*(?: [A-Za-z]+(?:[-'][A-Za-z]+)*)*$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z0-9]{1,}$");


    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("No input.");
                continue;
            }
            switch (input) {
                case "exit":
                    System.out.println("Bye!");
                    return;
                case "back":
                    System.out.println("Enter 'exit' to exit the program.");
                    break;
                case "add students":
                    addStudents(scanner);
                    break;
                case "list":
                    listStudents();
                    break;
                case "add points":
                    addPoints(scanner);
                    break;
                case "find":
                    findStudent(scanner);
                    break;
                case "statistics":
                    showStatistics(scanner);
                    break;
                default:
                    System.out.println("Unknown command!");
            }
        }
    }

    private static void addStudents(Scanner scanner) {
        System.out.println("Enter student credentials or 'back' to return");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("back")) break;
            String[] parts = input.split("\\s+");
            if (parts.length < 3) {
                System.out.println("Incorrect credentials format.");
                continue;
            }
            String firstName = parts[0];

            if (firstName.isEmpty() || firstName.length() <=1 || !firstName.matches("[A-Za-z]+([-'’][A-Za-z]+)*")) {
                System.out.println("Incorrect first name.");
                continue;
            }

            String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));

            String email = parts[parts.length - 1];

            if (lastName.isEmpty() || lastName.length() <=1 || !lastName.matches("([A-Za-z]+([-'’][A-Za-z]+)*)(\\s[A-Za-z]+([-'’][A-Za-z]+)*)*")) {
                System.out.println("Incorrect last name.");
                continue;
            }
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                System.out.println("Incorrect email.");
                continue;
            }


            if (students.values().stream().anyMatch(s -> s.getEmail().equals(email))) {
                System.out.println("This email is already taken.");
                continue;
            }

            students.put(idCounter, new Student(idCounter, firstName, lastName, email));
            System.out.println("The student has been added.");
            idCounter++;
        }
        System.out.println("Total " + students.size() + " students have been added.");
    }

    private static void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            students.keySet().forEach(System.out::println);
        }
    }

    private static void addPoints(Scanner scanner) {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("back")) break;
            String[] parts = input.split(" ");
            if (parts.length != 5) {
                System.out.println("Incorrect points format.");
                continue;
            }

            String idString = parts[0];

            // Check if ID is numeric before parsing
            if (!idString.matches("\\d+")) {  // Ensures the ID consists only of digits
                System.out.printf("No student is found for id=%s.%n", idString);
                continue;
            }

            try {
                // First, check if the ID is valid before proceeding to points parsing
                int id = Integer.parseInt(parts[0]);
                if (!students.containsKey(id)) {
                    System.out.printf("No student is found for id=%d.%n", id);
                    continue; // Skip points parsing if student is invalid
                }

                // Now parse the points and check if they are valid
                int[] points = Arrays.stream(parts, 1, 5).mapToInt(Integer::parseInt).toArray();

                // Check if any point is negative
                if (Arrays.stream(points).anyMatch(p -> p < 0)) {
                    System.out.println("Incorrect points format.");
                    continue; // Don't proceed with invalid points
                }

                // If everything is fine, update points
                Student student = students.get(id);
                student.addPoints(points);
                System.out.println("Points updated.");
            } catch (NumberFormatException e) {
                // If there's an error parsing the points, show the error message
                System.out.println("Incorrect points format.");
            }
        }
    }

    private static void findStudent(Scanner scanner) {
        System.out.println("Enter an id or 'back' to return:");
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("back")) break;
            try {
                int id = Integer.parseInt(input);
                if (!students.containsKey(id)) {
                    System.out.printf("No student is found for id=%d.%n", id);
                } else {
                    System.out.println(students.get(id));
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect ID format.");
            }
        }
    }

    private static void showStatistics(Scanner scanner) {
        System.out.println("Type the name of a course to see details or 'back' to quit and six lines with the following information: Most popular, Least popular, Highest activity, Lowest activity, Easiest course, Hardest course:");

        String[] courseNames = {"Java", "DSA", "Databases", "Spring"};
        int[] enrollments = new int[4];
        int[] totalPoints = new int[4];

        // Calculate statistics by iterating over all students
        for (Student student : students.values()) {
            int[] scores = student.getScores(); // Get student's scores
            for (int i = 0; i < scores.length; i++) {
                if (scores[i] > 0) {
                    enrollments[i]++;    // Count students with points in this course
                    totalPoints[i] += scores[i]; // Sum total points for this course
                }
            }
        }
        // Determine most and least popular courses
        String mostPopular = getCourseNamesByMax(enrollments, courseNames);
        String leastPopular = getCourseNamesByMin(enrollments, courseNames);

        // Determine highest and lowest activity courses
        String highestActivity = getCourseNamesByMax(totalPoints, courseNames);
        String lowestActivity = getCourseNamesByMin(totalPoints, courseNames);

        // Determine easiest and hardest courses (average points per student)
        double[] avgPoints = new double[4];
        boolean allCoursesHaveZeroEnrollments = true;
        for (int i = 0; i < 4; i++) {
            if (enrollments[i] > 0) {
                avgPoints[i] = (double) totalPoints[i] / enrollments[i];
                allCoursesHaveZeroEnrollments = false;
            }
        }
        String easiestCourse = allCoursesHaveZeroEnrollments ? "n/a" : getCourseNamesByMax(avgPoints, courseNames);
        String hardestCourse = allCoursesHaveZeroEnrollments ? "n/a" : getCourseNamesByMin(avgPoints, courseNames);

        // Print statistics
        System.out.printf("Most popular: %s%n", mostPopular);
        System.out.printf("Least popular: %s%n", leastPopular);
        System.out.printf("Highest activity: %s%n", highestActivity);
        System.out.printf("Lowest activity: %s%n", lowestActivity);
        System.out.printf("Easiest course: %s%n", easiestCourse);
        System.out.printf("Hardest course: %s%n", hardestCourse);

// Wait for input on specific course details
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("back")) break;
            if (!Arrays.asList(courseNames).contains(input)) {
                System.out.println("Unknown course.");
            } else {
                System.out.println(input);
                System.out.println("id\tpoints\tcompleted");
                for (Student student : students.values()) {
                    int score = student.getScores()[Arrays.asList(courseNames).indexOf(input)];
                    Integer completion = courseCompletion.get(input);
                    if (score > 0 && completion != null) {
                        System.out.printf("%d\t%d\t%.1f%%%n", student.getId(), score,
                                (double) score / completion * 100);
                    }
                }
            }
        }

    }

    private static String getCourseNamesByMin(int[] values, String[] names) {
        int min = Arrays.stream(values).filter(x -> x > 0).min().orElse(Integer.MAX_VALUE);
        if (min == Integer.MAX_VALUE) return "n/a";  // If no course has any enrollments (all are 0)
        return getCoursesWithValue(values, names, min);
    }


    private static String getCourseNamesByMin(double[] values, String[] names) {
        double min = Arrays.stream(values).filter(x -> x > 0).min().orElse(Double.MAX_VALUE);
        if (min == Double.MAX_VALUE) return "n/a";  // If no course has any enrollments (all are 0)
        return getCoursesWithValue(values, names, min);
    }

    private static String getCourseNamesByMax(int[] values, String[] names) {
        int max = Arrays.stream(values).max().orElse(0);
        if (max == 0) return "n/a";
        return getCoursesWithValue(values, names, max);
    }
    private static String getCourseNamesByMax(double[] values, String[] names) {
        double max = Arrays.stream(values).max().orElse(0);
        if (max == 0) return "n/a";
        return getCoursesWithValue(values, names, max);
    }

    private static String getCoursesWithValue(double[] values, String[] names, double value) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                result.add(names[i]);
            }
        }
        return String.join(", ", result);
    }
    private static String getCoursesWithValue(int[] values, String[] names, int value) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                result.add(names[i]);
            }
        }
        return String.join(", ", result);
    }
}

class Student {
    private final int id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final int[] scores = new int[4];

    public Student(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public int[] getScores() {
        return scores;
    }

    public void addPoints(int[] points) {
        for (int i = 0; i < scores.length; i++) {
            scores[i] += points[i];
        }
    }

    @Override
    public String toString() {
        return String.format("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d",
                id, scores[0], scores[1], scores[2], scores[3]);
    }

}
