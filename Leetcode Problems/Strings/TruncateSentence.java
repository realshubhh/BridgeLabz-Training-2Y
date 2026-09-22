public class TruncateSentence {

    public String truncateSentence(String s, int k) {
        String[] words = s.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < k; i++) {
            if (i > 0) {
                result.append(" ");
            }
            result.append(words[i]);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String s = "Hello how are you Contestant";
        int k = 4;

        TruncateSentence solution = new TruncateSentence();
        String result = solution.truncateSentence(s, k);

        System.out.println("Truncated sentence: " + result);
    }
}
