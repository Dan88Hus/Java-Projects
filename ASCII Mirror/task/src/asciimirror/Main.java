package asciimirror;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input the file path:");
        String filePath = scanner.nextLine();

        // Validate file existence
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File not found!");
            return;
        }

        // Read lines from the file
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
            return;
        }

        // Find the longest line length
        int maxLength = lines.stream().mapToInt(String::length).max().orElse(0);

        // Format lines with padding
        List<String> formattedLines = new ArrayList<>();
        for (String line : lines) {
            formattedLines.add(String.format("%-" + maxLength + "s", line));
        }

        // Print the mirrored ASCII art
        for (String line : formattedLines) {
            System.out.println(line + " | " + line);
        }
        
    }
}