import java.util.HashMap;
import java.util.Map;

public class WordPattern {

    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");

        if (pattern.length() != words.length) {
            return false;
        }

        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char patternChar = pattern.charAt(i);
            String word = words[i];

            if (charToWord.containsKey(patternChar) && !charToWord.get(patternChar).equals(word)) {
                return false;
            }

            if (wordToChar.containsKey(word) && wordToChar.get(word) != patternChar) {
                return false;
            }

            charToWord.put(patternChar, word);
            wordToChar.put(word, patternChar);
        }

        return true;
    }

    public static void main(String[] args) {
        String pattern = "abba";
        String s = "dog cat cat dog";

        WordPattern solution = new WordPattern();
        boolean result = solution.wordPattern(pattern, s);

        System.out.println("Does the string follow the pattern? " + result);
    }
}
