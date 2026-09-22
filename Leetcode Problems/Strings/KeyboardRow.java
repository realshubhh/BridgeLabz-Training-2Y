import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardRow {

    public String[] findWords(String[] words) {
        Map<Character, Integer> rowOfChar = new HashMap<>();
        String[] rows = {"qwertyuiop", "asdfghjkl", "zxcvbnm"};

        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            for (char c : rows[rowIndex].toCharArray()) {
                rowOfChar.put(c, rowIndex);
            }
        }

        List<String> result = new ArrayList<>();

        for (String word : words) {
            if (isSingleRowWord(word, rowOfChar)) {
                result.add(word);
            }
        }

        return result.toArray(new String[0]);
    }

    private boolean isSingleRowWord(String word, Map<Character, Integer> rowOfChar) {
        String lowerWord = word.toLowerCase();
        int firstRow = rowOfChar.get(lowerWord.charAt(0));

        for (char c : lowerWord.toCharArray()) {
            if (rowOfChar.get(c) != firstRow) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        String[] words = {"Hello", "Alaska", "Dad", "Peace"};

        KeyboardRow solution = new KeyboardRow();
        String[] result = solution.findWords(words);

        System.out.println("Words typed using one row: " + Arrays.toString(result));
    }
}
