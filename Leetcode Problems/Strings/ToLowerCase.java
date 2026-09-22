public class ToLowerCase {

    public String toLowerCase(String s) {
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] >= 'A' && chars[i] <= 'Z') {
                chars[i] = (char) (chars[i] + ('a' - 'A'));
            }
        }

        return new String(chars);
    }

    public static void main(String[] args) {
        String s = "LEETCODE";

        ToLowerCase solution = new ToLowerCase();
        String result = solution.toLowerCase(s);

        System.out.println("Lowercase string: " + result);
    }
}
