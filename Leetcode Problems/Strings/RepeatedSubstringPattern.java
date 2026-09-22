public class RepeatedSubstringPattern {

    public boolean repeatedSubstringPattern(String s) {
        String doubled = (s + s).substring(1, 2 * s.length() - 1);
        return doubled.contains(s);
    }

    public static void main(String[] args) {
        String s = "abab";

        RepeatedSubstringPattern solution = new RepeatedSubstringPattern();
        boolean result = solution.repeatedSubstringPattern(s);

        System.out.println("Can be constructed by repeating a substring? " + result);
    }
}
