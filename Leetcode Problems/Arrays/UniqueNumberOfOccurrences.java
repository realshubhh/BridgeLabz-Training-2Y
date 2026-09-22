import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UniqueNumberOfOccurrences {

    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (int num : arr) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        Set<Integer> occurrenceSet = new HashSet<>(frequency.values());

        return occurrenceSet.size() == frequency.size();
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 1, 1, 3};

        UniqueNumberOfOccurrences solution = new UniqueNumberOfOccurrences();
        boolean result = solution.uniqueOccurrences(arr);

        System.out.println("Are occurrence counts unique? " + result);
    }
}
