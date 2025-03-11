package sorting;

import java.util.*;

public class Main {
    public static void main(final String[] args) {
        String dataType = "word"; // Default data type
        if (args.length > 0 && args[0].equals("-dataType") && args.length > 1) {
            dataType = args[1];
        }
        Scanner scanner = new Scanner(System.in);
        List<String> elements = new ArrayList<>();
        long count = 0;
        String maxElement = "";
        long maxCount = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue; // Skip empty lines
            if (dataType.equals("long")) {
                String[] numbers = line.split("\\s+");
                for (String numberStr : numbers) {
                    try {
                        long number = Long.parseLong(numberStr);
                        elements.add(numberStr);
                        count++;
                        if (maxElement.isEmpty() || number > Long.parseLong(maxElement)) {
                            maxElement = numberStr;
                            maxCount = 1;
                        } else if (numberStr.equals(maxElement)) {
                            maxCount++;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore non-long inputs
                    }
                }
            } else if (dataType.equals("line")) {
                elements.add(line);
                count++;
                if (maxElement.isEmpty() || line.length() > maxElement.length() || (line.length() == maxElement.length() && line.compareTo(maxElement) < 0)) {
                    maxElement = line;
                    maxCount = 1;
                } else if (line.equals(maxElement)) {
                    maxCount++;
                }
            } else if (dataType.equals("word")) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    elements.add(word);
                    count++;
                    if (maxElement.isEmpty() || word.length() > maxElement.length() || (word.length() == maxElement.length() && word.compareTo(maxElement) < 0)) {
                        maxElement = word;
                        maxCount = 1;
                    } else if (word.equals(maxElement)) {
                        maxCount++;
                    }
                }
            }
        }
        scanner.close();

        // Output the results
        if (dataType.equals("long")) {
            System.out.println("Total numbers: " + count + ".");
            System.out.println("The greatest number: " + maxElement + " (" + maxCount + " time(s), " + Math.round(maxCount * 100.0 / count) + "%).");
        } else if (dataType.equals("line")) {
            System.out.println("Total lines: " + count + ".");
            System.out.println("The longest line:");
            System.out.println(maxElement);
            System.out.println("(" + maxCount + " time(s), " + Math.round(maxCount * 100.0 / count) + "%).");
        } else if (dataType.equals("word")) {
            System.out.println("Total words: " + count + ".");
            System.out.println("The longest word: " + maxElement + " (" + maxCount + " time(s), " + Math.round(maxCount * 100.0 / count) + "%).");
        } else {
            System.out.println("Invalid data type specified.");
        }
    }
}
