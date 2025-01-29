package bullscows;

public class Main {
    public static void main(String[] args) {
        System.out.println("The secret code is prepared: ****.\n");
        printTurn(1, "1234", "1 cow.");
        printTurn(2, "5678", "1 bull.");
        printTurn(3, "9012", "2 bulls.");
        printTurn(4, "9083", "3 bulls.");
        printTurn(5, "9084", "4 bulls.");
        System.out.println("Congrats! The secret code is 9084.");
    }

    private static void printTurn(int turn, String answer, String grade) {
        System.out.println("Turn " + turn + ". Answer:");
        System.out.println(answer);
        System.out.println("Grade: " + grade + "\n");
    }
}
