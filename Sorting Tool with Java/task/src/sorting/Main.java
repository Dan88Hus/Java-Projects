package sorting;

import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(final String[] args) {
        Scanner scanner = new Scanner(System.in);
        String dataType = "word"; // Default data type
        String sortingType = "natural"; // Default sorting type

        // Parse command-line arguments
        List<String> arguments = Arrays.asList(args);
        if (arguments.contains("-dataType")) {
            int index = arguments.indexOf("-dataType");
            if (index + 1 < arguments.size()) {
                dataType = arguments.get(index + 1);
            } else {
                System.out.println("No data type specified!");
                return;
            }
        }
        if (arguments.contains("-sortingType")) {
            int index = arguments.indexOf("-sortingType");
            if (index + 1 < arguments.size()) {
                sortingType = arguments.get(index + 1);
            } else {
                System.out.println("No sorting type specified!");
                return;
            }
        }

        // Store input data
        List<String> words = new ArrayList<>();
        List<Long> numbers = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        Map<String, Integer> wordFrequency = new HashMap<>();
        Map<Long, Integer> numberFrequency = new HashMap<>();
        Map<String, Integer> lineFrequency = new HashMap<>();

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (dataType.equals("long")) {
                String[] tokens = input.split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        try {
                            long number = Long.parseLong(token);
                            numbers.add(number);
                            numberFrequency.put(number, numberFrequency.getOrDefault(number, 0) + 1);
                        } catch (NumberFormatException e) {
                            System.out.println("\"" + token + "\" is not a valid long.");
                        }
                    }
                }
            } else if (dataType.equals("word")) {
                String[] tokens = input.split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        words.add(token);
                        wordFrequency.put(token, wordFrequency.getOrDefault(token, 0) + 1);
                    }
                }
            } else if (dataType.equals("line")) {
                lines.add(input);
                lineFrequency.put(input, lineFrequency.getOrDefault(input, 0) + 1);
            }
        }

        // Sorting and printing results
        if (sortingType.equals("natural")) {
            if (dataType.equals("long")) {
                Collections.sort(numbers);
                System.out.println("Total numbers: " + numbers.size() + ".");
                System.out.print("Sorted data: ");
                for (Long num : numbers) {
                    System.out.print(num + " ");
                }
                System.out.println();
            } else if (dataType.equals("word")) {
                Collections.sort(words);
                System.out.println("Total words: " + words.size() + ".");
                System.out.print("Sorted data: ");
                for (String word : words) {
                    System.out.print(word + " ");
                }
                System.out.println();
            } else if (dataType.equals("line")) {
                Collections.sort(lines);
                System.out.println("Total lines: " + lines.size() + ".");
                System.out.println("Sorted data:");
                for (String line : lines) {
                    System.out.println(line);
                }
            }
        } else if (sortingType.equals("byCount")) {
            if (dataType.equals("long")) {
                List<Map.Entry<Long, Integer>> sortedEntries = new ArrayList<>(numberFrequency.entrySet());
                sortedEntries.sort(Comparator.comparing(Map.Entry<Long, Integer>::getValue)
                        .thenComparing(Map.Entry::getKey));

                System.out.println("Total numbers: " + numbers.size() + ".");
                for (Map.Entry<Long, Integer> entry : sortedEntries) {
                    double percentage = (entry.getValue() * 100.0) / numbers.size();
                    System.out.printf("%d: %d time(s), %.0f%%%n", entry.getKey(), entry.getValue(), percentage);
                }
            } else if (dataType.equals("word")) {
                List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequency.entrySet());
                sortedEntries.sort(Comparator.comparing(Map.Entry<String, Integer>::getValue)
                        .thenComparing(Map.Entry::getKey));

                System.out.println("Total words: " + words.size() + ".");
                for (Map.Entry<String, Integer> entry : sortedEntries) {
                    double percentage = (entry.getValue() * 100.0) / words.size();
                    System.out.printf("%s: %d time(s), %.0f%%%n", entry.getKey(), entry.getValue(), percentage);
                }
            } else if (dataType.equals("line")) {
                List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(lineFrequency.entrySet());
                sortedEntries.sort(Comparator.comparing(Map.Entry<String, Integer>::getValue)
                        .thenComparing(Map.Entry::getKey));

                System.out.println("Total lines: " + lines.size() + ".");
                for (Map.Entry<String, Integer> entry : sortedEntries) {
                    double percentage = (entry.getValue() * 100.0) / lines.size();
                    System.out.printf("%s: %d time(s), %.0f%%%n", entry.getKey(), entry.getValue(), percentage);
                }
            }
        }
    }
}
