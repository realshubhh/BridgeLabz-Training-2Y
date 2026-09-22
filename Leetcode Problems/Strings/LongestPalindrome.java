import java.util.HashMap;
import java.util.Map;

public class LongestPalindrome {

    public int longestPalindrome(String s) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        int length = 0;
        boolean hasOddCount = false;

        for (int count : frequency.values()) {
            length += (count / 2) * 2;
            if (count % 2 == 1) {
                hasOddCount = true;
            }
        }

        return hasOddCount ? length + 1 : length;
    }

    public static void main(String[] args) {
        String s = "abccccdd";

        LongestPalindrome solution = new LongestPalindrome();
        int result = solution.longestPalindrome(s);

        System.out.println("Length of longest palindrome that can be built: " + result);
    }
}
