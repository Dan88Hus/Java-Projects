package asciimirror;

public class Main {
    public static void main(String[] args) {

        String[] cowLines = {
                "                    _______ ",
                "                   < hello >",
                "                    ------- ",
                "            ^__^   /        ",
                "    _______/(oo)  /         ",
                "/\\/(       /(__)            ",
                "   | w----||                ",
                "   ||     ||                "
        };

        for (String line : cowLines) {
            System.out.print(line + "\n");
        }
    }
}