import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortCharactersByFrequency {

    @SuppressWarnings("unchecked")
    public String frequencySort(String s) {
        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : s.toCharArray()) {
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        // Bucket sort by frequency; index of the bucket represents the frequency value
        List<Character>[] buckets = new List[s.length() + 1];
        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(entry.getKey());
        }

        StringBuilder result = new StringBuilder();

        for (int freq = buckets.length - 1; freq >= 0; freq--) {
            if (buckets[freq] != null) {
                for (char c : buckets[freq]) {
                    for (int i = 0; i < freq; i++) {
                        result.append(c);
                    }
                }
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String s = "tree";

        SortCharactersByFrequency solution = new SortCharactersByFrequency();
        String result = solution.frequencySort(s);

        System.out.println("Characters sorted by frequency: " + result);
    }
}
