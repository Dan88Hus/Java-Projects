package readability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Main <filename>");
            return;
        }

        String filename = args[0];
        StringBuilder text = new StringBuilder();
        int characters = 0;
        int words = 0;
        int sentences = 0;

        // Read the file and process the text
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append("\n");
                characters += countVisibleCharacters(line);
                words += countWords(line);
                sentences += countSentences(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Calculate the ARI score
        double score = calculateARI(characters, words, sentences);
        int roundedScore = (int) Math.floor(score);
        String ageBracket = getAgeBracket(roundedScore);

        // Output the results
        System.out.println("The text is:");
        System.out.println(text.toString().trim());
        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("The score is: " + score);
        System.out.println("This text should be understood by " + ageBracket + ".");
    }

    private static int countVisibleCharacters(String line) {
        // Count all characters except spaces, newlines, and tabs
        return line.replaceAll("[\\s]", "").length();
    }

    private static int countWords(String line) {
        // Split the line by whitespace and count the words
        String[] words = line.trim().split("\\s+");
        return words.length == 1 && words[0].isEmpty() ? 0 : words.length;
    }

    private static int countSentences(String line) {
        // Count sentences based on common sentence-ending punctuation
        String[] sentences = line.split("[.!?]+");
        return sentences.length == 1 && sentences[0].isEmpty() ? 0 : sentences.length;
    }

    private static double calculateARI(int characters, int words, int sentences) {
        // Calculate the ARI score using the formula
        return 4.71 * (double) characters / words + 0.5 * (double) words / sentences - 21.43;
    }

    private static String getAgeBracket(int score) {
        // Determine the age bracket based on the ARI score
        if (score < 1) return "5-6 year-olds";
        if (score < 2) return "6-7 year-olds";
        if (score < 3) return "7-8 year-olds";
        if (score < 4) return "8-9 year-olds";
        if (score < 5) return "9-10 year-olds";
        if (score < 6) return "10-11 year-olds";
        if (score < 7) return "11-12 year-olds";
        if (score < 8) return "12-13 year-olds";
        if (score < 9) return "13-14 year-olds";
        if (score < 10) return "14-15 year-olds";
        if (score < 11) return "15-16 year-olds";
        if (score < 12) return "16-17 year-olds";
        if (score < 13) return "17-18 year-olds";
        if (score < 14) return "18-22 year-olds";
        return "22+ year-olds";
    }
}
