public class ReplaceAllQuestionMarksToAvoidConsecutiveRepeatingCharacters {

    public String modifyString(String s) {
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '?') {
                for (char candidate = 'a'; candidate <= 'c'; candidate++) {
                    boolean matchesLeft = i > 0 && chars[i - 1] == candidate;
                    boolean matchesRight = i < chars.length - 1 && chars[i + 1] == candidate;

                    if (!matchesLeft && !matchesRight) {
                        chars[i] = candidate;
                        break;
                    }
                }
            }
        }

        return new String(chars);
    }

    public static void main(String[] args) {
        String s = "a?a?";

        ReplaceAllQuestionMarksToAvoidConsecutiveRepeatingCharacters solution = new ReplaceAllQuestionMarksToAvoidConsecutiveRepeatingCharacters();
        String result = solution.modifyString(s);

        System.out.println("Modified string: " + result);
    }
}
