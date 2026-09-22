public class DetectCapital {

    public boolean detectCapitalUse(String word) {
        int upperCaseCount = 0;

        for (char c : word.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCaseCount++;
            }
        }

        if (upperCaseCount == word.length() || upperCaseCount == 0) {
            return true;
        }

        return upperCaseCount == 1 && Character.isUpperCase(word.charAt(0));
    }

    public static void main(String[] args) {
        String word = "USA";

        DetectCapital solution = new DetectCapital();
        boolean result = solution.detectCapitalUse(word);

        System.out.println("Correct capital usage? " + result);
    }
}
