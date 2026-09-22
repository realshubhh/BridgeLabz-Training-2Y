public class LongestPalindromicSubstring {

    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }

        int start = 0;
        int maxLength = 1;

        for (int i = 0; i < s.length(); i++) {
            int lengthOdd = expandAroundCenter(s, i, i);
            int lengthEven = expandAroundCenter(s, i, i + 1);
            int currentMax = Math.max(lengthOdd, lengthEven);

            if (currentMax > maxLength) {
                maxLength = currentMax;
                start = i - (currentMax - 1) / 2;
            }
        }

        return s.substring(start, start + maxLength);
    }

    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public static void main(String[] args) {
        String s = "babad";

        LongestPalindromicSubstring solution = new LongestPalindromicSubstring();
        String result = solution.longestPalindrome(s);

        System.out.println("Longest palindromic substring: " + result);
    }
}
