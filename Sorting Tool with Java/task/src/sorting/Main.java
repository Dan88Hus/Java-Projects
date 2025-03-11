package sorting;

import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(final String[] args) {
        boolean sortIntegers = Arrays.asList(args).contains("-sortIntegers");

        String dataType = "word";

        if (!sortIntegers) {
            for (int i = 0; i < args.length; i++) {
                if ("-dataType".equals(args[i]) && i + 1 < args.length) {
                    dataType = args[i + 1];
                }
            }
        }

        Scanner scanner = new Scanner(System.in);
        List<String> elements = new ArrayList<>();
        long count = 0;
        String maxElement = "";
        long maxCount = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            if (sortIntegers) {
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    try {
                        BigInteger number = new BigInteger(token);
                        elements.add(token);
                        count++;
                    } catch (NumberFormatException ignored) {}
                }
            } else if (dataType.equals("long")) {
                String[] tokens = line.split("\\s+");
                for (String token : tokens) {
                    try {
                        BigInteger number = new BigInteger(token);
                        elements.add(token);
                        count++;
                        if (maxElement.isEmpty() || number.compareTo(new BigInteger(maxElement)) > 0) {
                            maxElement = token;
                            maxCount = 1;
                        } else if (number.compareTo(new BigInteger(maxElement)) == 0) {
                            maxCount++;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            } else if (dataType.equals("line")) {
                elements.add(line);
                count++;
                if (line.length() > maxElement.length() || maxElement.isEmpty() || (line.length() == maxElement.length() && line.compareTo(maxElement) < 0)) {
                    maxElement = line;
                    maxCount = 1;
                } else if (line.equals(maxElement)) {
                    maxCount++;
                }
            } else if (dataType.equals("word")) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Remove the integer validation check
                    elements.add(word);
                    count++;
                    if (word.length() > maxElement.length() || maxElement.isEmpty() || (word.length() == maxElement.length() && word.compareTo(maxElement) < 0)) {
                        maxElement = word;
                        maxCount = 1;
                    } else if (word.equals(maxElement)) {
                        maxCount++;
                    }
                }
            }
        }

        if (sortIntegers) {
            Collections.sort(elements, (a, b) -> {
                try {
                    return new BigInteger(a).compareTo(new BigInteger(b));
                } catch (NumberFormatException e) {
                    return a.compareTo(b);
                }
            });
            System.out.println("Total numbers: " + count + ".");
            System.out.print("Sorted data: ");
            for (String element : elements) {
                System.out.print(element + " ");
            }
            System.out.println();
        } else if (dataType.equals("long")) {
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
