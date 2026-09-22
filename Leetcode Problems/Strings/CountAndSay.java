public class CountAndSay {

    public String countAndSay(int n) {
        String result = "1";

        for (int i = 1; i < n; i++) {
            result = generateNextTerm(result);
        }

        return result;
    }

    private String generateNextTerm(String term) {
        StringBuilder nextTerm = new StringBuilder();
        int count = 1;

        for (int i = 1; i <= term.length(); i++) {
            if (i < term.length() && term.charAt(i) == term.charAt(i - 1)) {
                count++;
            } else {
                nextTerm.append(count).append(term.charAt(i - 1));
                count = 1;
            }
        }

        return nextTerm.toString();
    }

    public static void main(String[] args) {
        int n = 5;

        CountAndSay solution = new CountAndSay();
        String result = solution.countAndSay(n);

        System.out.println("Count and Say sequence for n=" + n + ": " + result);
    }
}
