package readability;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine().trim();
        scanner.close();

        String[] sentences = text.split("[.!?]");
        int totalWords = 0;
        int sentenceCount = 0;

        for (String sentence : sentences) {
            String[] words = sentence.trim().split("\\s+");
            if (words.length > 0 && !sentence.trim().isEmpty()) {
                totalWords += words.length;
                sentenceCount++;
            }
        }

        double averageWordsPerSentence = (double) totalWords / sentenceCount;

        if (averageWordsPerSentence > 10) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }
    }
}
