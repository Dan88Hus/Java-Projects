package sorting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(final String[] args) {
        String dataType = null;

        String sortingType = "natural"; // Default sorting type

        String inputFile = null;

        String outputFile = null;

        boolean readFromFile = false;

        boolean writeToFile = false;


        // Parse command-line arguments

        List<String> arguments = Arrays.asList(args);

        for (int i = 0; i < arguments.size(); i++) {

            String arg = arguments.get(i);

            switch (arg) {

                case "-sortingType":

                    if (i + 1 < arguments.size() && !arguments.get(i + 1).startsWith("-")) {

                        sortingType = arguments.get(i + 1);

                        i++;

                    } else {

                        System.out.println("No sorting type defined!");

                        return;

                    }

                    break;

                case "-dataType":

                    if (i + 1 < arguments.size() && !arguments.get(i + 1).startsWith("-")) {

                        dataType = arguments.get(i + 1);

                        i++;

                    } else {

                        System.out.println("No data type defined!");

                        return;

                    }

                    break;

                case "-inputFile":

                    if (i + 1 < arguments.size() && !arguments.get(i + 1).startsWith("-")) {

                        inputFile = arguments.get(i + 1);

                        readFromFile = true;

                        i++;

                    } else {

                        System.out.println("No input file specified!");

                        return;

                    }

                    break;

                case "-outputFile":

                    if (i + 1 < arguments.size() && !arguments.get(i + 1).startsWith("-")) {

                        outputFile = arguments.get(i + 1);

                        writeToFile = true;

                        i++;

                    } else {

                        System.out.println("No output file specified!");

                        return;

                    }

                    break;

                default:

                    if (arg.startsWith("-")) {

                        System.out.printf("\"%s\" is not a valid parameter. It will be skipped.%n", arg);

                    }

            }

        }


        // Ensure dataType is defined

        if (dataType == null) {
            dataType = "word"; // Default to word if not specified
            // Optionally add a message
            // System.out.println("No data type defined, using 'word' as default.");
        }


        // Prepare input reader

        List<String> inputLines = new ArrayList<>();

        try {

            if (readFromFile) {

                File file = new File(inputFile);

                if (!file.exists()) {

                    System.out.println("Input file not found: " + inputFile);

                    return;

                }

                Scanner fileScanner = new Scanner(file);

                while (fileScanner.hasNextLine()) {

                    inputLines.add(fileScanner.nextLine());

                }

                fileScanner.close();

            } else {

                Scanner scanner = new Scanner(System.in);

                while (scanner.hasNextLine()) {

                    inputLines.add(scanner.nextLine());

                }

            }

        } catch (IOException e) {

            System.out.println("Error reading input file: " + e.getMessage());

            return;

        }


        // Prepare output writer

        StringBuilder output = new StringBuilder();


        // Store input data

        List<String> words = new ArrayList<>();

        List<Long> numbers = new ArrayList<>();

        List<String> lines = new ArrayList<>();

        Map<String, Integer> wordFrequency = new HashMap<>();

        Map<Long, Integer> numberFrequency = new HashMap<>();

        Map<String, Integer> lineFrequency = new HashMap<>();


        // Process input

        for (String input : inputLines) {

            if (dataType.equals("long")) {

                String[] tokens = input.split("\\s+");

                for (String token : tokens) {

                    if (!token.isEmpty()) {

                        try {

                            long number = Long.parseLong(token);

                            numbers.add(number);

                            numberFrequency.put(number, numberFrequency.getOrDefault(number, 0) + 1);

                        } catch (NumberFormatException e) {

                            System.out.printf("\"%s\" is not a long. It will be skipped.%n", token);

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


        // Sorting and generating output

        if (sortingType.equals("natural")) {

            if (dataType.equals("long")) {

                Collections.sort(numbers);

                output.append("Total numbers: ").append(numbers.size()).append(".\nSorted data: ");

                for (Long num : numbers) {

                    output.append(num).append(" ");

                }

            } else if (dataType.equals("word")) {

                Collections.sort(words);

                output.append("Total words: ").append(words.size()).append(".\nSorted data: ");

                for (String word : words) {

                    output.append(word).append(" ");

                }

            } else if (dataType.equals("line")) {

                Collections.sort(lines);

                output.append("Total lines: ").append(lines.size()).append(".\nSorted data:\n");

                for (String line : lines) {

                    output.append(line).append("\n");

                }

            }

        } else if (sortingType.equals("byCount")) {

            if (dataType.equals("long")) {

                List<Map.Entry<Long, Integer>> sortedEntries = new ArrayList<>(numberFrequency.entrySet());

                sortedEntries.sort(Comparator.comparing(Map.Entry<Long, Integer>::getValue)

                        .thenComparing(Map.Entry::getKey));


                output.append("Total numbers: ").append(numbers.size()).append(".\n");

                for (Map.Entry<Long, Integer> entry : sortedEntries) {

                    double percentage = (entry.getValue() * 100.0) / numbers.size();

                    output.append(entry.getKey()).append(": ").append(entry.getValue())

                            .append(" time(s), ").append((int) percentage).append("%\n");

                }

            } else if (dataType.equals("word")) {

                List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(wordFrequency.entrySet());

                sortedEntries.sort(Comparator.comparing(Map.Entry<String, Integer>::getValue)

                        .thenComparing(Map.Entry::getKey));


                output.append("Total words: ").append(words.size()).append(".\n");

                for (Map.Entry<String, Integer> entry : sortedEntries) {

                    double percentage = (entry.getValue() * 100.0) / words.size();

                    output.append(entry.getKey()).append(": ").append(entry.getValue())

                            .append(" time(s), ").append((int) percentage).append("%\n");

                }

            } else if (dataType.equals("line")) {

                List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(lineFrequency.entrySet());

                sortedEntries.sort(Comparator.comparing(Map.Entry<String, Integer>::getValue)

                        .thenComparing(Map.Entry::getKey));


                output.append("Total lines: ").append(lines.size()).append(".\n");

                for (Map.Entry<String, Integer> entry : sortedEntries) {

                    double percentage = (entry.getValue() * 100.0) / lines.size();

                    output.append(entry.getKey()).append(": ").append(entry.getValue())

                            .append(" time(s), ").append((int) percentage).append("%\n");

                }

            }

        }


        // Write to output file if specified, else print to console

        if (writeToFile) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

                writer.write(output.toString());

            } catch (IOException e) {

                System.out.println("Error writing to output file: " + e.getMessage());

            }

        } else {

            System.out.println(output);

        }
    }
}
