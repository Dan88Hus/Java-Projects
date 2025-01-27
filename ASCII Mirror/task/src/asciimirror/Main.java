package asciimirror;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the file path:");
        String filePath = scanner.nextLine();
        System.out.println(filePath);

        List<String> cowAscii = cowAscii();
        cowAscii.forEach(System.out::println);
    }

    private static List<String> cowAscii() {
        return List.of(
                "            ^__^",
                "    _______/(oo)",
                "/\\/(       /(__)",
                "   | w----||    ",
                "   ||     ||    "
        );
    }
}