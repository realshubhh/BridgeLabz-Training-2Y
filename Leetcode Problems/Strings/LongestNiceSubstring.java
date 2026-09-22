import java.util.HashSet;
import java.util.Set;

public class LongestNiceSubstring {

    public String longestNiceSubstring(String s) {
        if (s.length() < 2) {
            return "";
        }

        Set<Character> charSet = new HashSet<>();
        for (char c : s.toCharArray()) {
            charSet.add(c);
        }

        for (int i = 0; i < s.length(); i++) {
            char current = s.charAt(i);
            char opposite = Character.isUpperCase(current)
                    ? Character.toLowerCase(current)
                    : Character.toUpperCase(current);

            if (!charSet.contains(opposite)) {
                String leftPart = longestNiceSubstring(s.substring(0, i));
                String rightPart = longestNiceSubstring(s.substring(i + 1));
                return leftPart.length() >= rightPart.length() ? leftPart : rightPart;
            }
        }

        return s;
    }

    public static void main(String[] args) {
        String input = "YazaAay";

        LongestNiceSubstring solution = new LongestNiceSubstring();
        String result = solution.longestNiceSubstring(input);

        System.out.println("Longest Nice Substring: " + result);
    }
}
