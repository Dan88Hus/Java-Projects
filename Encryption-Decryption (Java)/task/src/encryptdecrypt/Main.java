package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        String message = "we found a treasure!";
        String encrypted = encrypt(message);
        System.out.println(encrypted);
    }

    private static String encrypt(String message) {
        StringBuilder result = new StringBuilder();

        for (char c : message.toCharArray()){
            if (c>= 'a' && c<='z'){
                char encryptedChar = (char) ('a' + 'z' -c);
                result.append(encryptedChar);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
