import java.util.HashSet;
import java.util.Set;

public class SubstringsOfSizeThreeWithDistinctCharacters {

    public int countGoodSubstrings(String s) {
        int count = 0;

        for (int i = 0; i + 3 <= s.length(); i++) {
            String window = s.substring(i, i + 3);
            if (hasAllDistinctCharacters(window)) {
                count++;
            }
        }

        return count;
    }

    private boolean hasAllDistinctCharacters(String window) {
        Set<Character> seen = new HashSet<>();
        for (char c : window.toCharArray()) {
            if (!seen.add(c)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "xyzzaz";

        SubstringsOfSizeThreeWithDistinctCharacters solution = new SubstringsOfSizeThreeWithDistinctCharacters();
        int result = solution.countGoodSubstrings(s);

        System.out.println("Count of good substrings: " + result);
    }
}
