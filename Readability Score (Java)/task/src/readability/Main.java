package readability;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        String filename = args[0];
        String text = readFile(filename);

        if (text == null) {
            System.out.println("Error reading file.");
            return;
        }

        int words = countWords(text);
        int sentences = countSentences(text);
        int characters = countCharacters(text);
        int syllables = countSyllables(text);
        int polysyllables = countPolysyllables(text);

        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = scanner.nextLine().toLowerCase();

        double ariScore = calculateARI(words, sentences);
        double fkScore = calculateFK(words, sentences, syllables);
        double smogScore = calculateSMOG(polysyllables, sentences);
        double clScore = calculateCL(words, characters, sentences);

        if (choice.equals("ari") || choice.equals("all")) {
            System.out.printf("Automated Readability Index: %.2f (about %d-year-olds).\n", ariScore, (int) Math.ceil(ariScore));
        }
        if (choice.equals("fk") || choice.equals("all")) {
            System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d-year-olds).\n", fkScore, (int) Math.ceil(fkScore));
        }
        if (choice.equals("smog") || choice.equals("all")) {
            System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d-year-olds).\n", smogScore, (int) Math.ceil(smogScore));
        }
        if (choice.equals("cl") || choice.equals("all")) {
            System.out.printf("Coleman–Liau index: %.2f (about %d-year-olds).\n", clScore, (int) Math.ceil(clScore));
        }

        double averageAge = (ariScore + fkScore + smogScore + clScore) / 4;
        System.out.printf("This text should be understood in average by %.2f-year-olds.\n", averageAge);
    }

    private static String readFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(" ");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return content.toString().trim();
    }

    private static int countWords(String text) {
        return text.split("\\s+").length;
    }

    private static int countSentences(String text) {
        return text.split("[.!?]").length;
    }

    private static int countCharacters(String text) {
        return text.replaceAll("\\s+", "").length();
    }

    private static int countSyllables(String text) {
        int syllableCount = 0;
        String[] words = text.split("\\s+");
        for (String word : words) {
            syllableCount += countSyllablesInWord(word);
        }
        return syllableCount;
    }

    private static int countSyllablesInWord(String word) {
        word = word.toLowerCase();
        int count = 0;
        boolean lastWasVowel = false;

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if ("aeiouy".indexOf(ch) >= 0) {
                if (!lastWasVowel) {
                    count++;
                    lastWasVowel = true;
                }
            } else {
                lastWasVowel = false;
            }
        }

        // Rule 3: If the last letter is 'e', subtract one syllable
        if (word.endsWith("e")) {
            count--;
        }

        // Rule 4: If no vowels were found, consider it as 1 syllable
        if (count == 0) {
            count = 1;
        }

        return count;
    }

    private static int countPolysyllables(String text) {
        int polysyllableCount = 0;
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (countSyllablesInWord(word) > 2) {
                polysyllableCount++;
            }
        }
        return polysyllableCount;
    }

    private static double calculateARI(int words, int sentences) {
        if (sentences == 0 || words == 0) {
            return 0; // Avoid division by zero
        }
        return (4.71 * (double) words / sentences) - (0.5 * (double) sentences / words) - 21.43;
    }

    private static double calculateFK(int words, int sentences, int syllables) {
        return (0.39 * (double) words / sentences) + (11.8 * (double) syllables / words) - 15.59;
    }

    private static double calculateSMOG(int polysyllables, int sentences) {
        if (sentences == 0) {
            return 0; // Avoid division by zero
        }
        return (1.043 * (double) polysyllables * 30 / sentences) + 3.1291;
    }

    private static double calculateCL(int words, int characters, int sentences) {
        if (sentences == 0 || words == 0) {
            return 0; // Avoid division by zero
        }
        double L = (double) characters / words * 100; // Average number of characters per 100 words
        double S = (double) sentences / words * 100; // Average number of sentences per 100 words
        return (0.0588 * L) - (0.296 * S) - 15.8; // Coleman-Liau index formula
    }
}
