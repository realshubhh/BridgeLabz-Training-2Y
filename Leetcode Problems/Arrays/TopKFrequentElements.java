import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopKFrequentElements {

    @SuppressWarnings("unchecked")
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (int num : nums) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        // Bucket sort: index represents frequency, bucket holds numbers with that frequency
        List<Integer>[] buckets = new List[nums.length + 1];
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(entry.getKey());
        }

        int[] result = new int[k];
        int resultIndex = 0;

        for (int freq = buckets.length - 1; freq >= 0 && resultIndex < k; freq--) {
            if (buckets[freq] != null) {
                for (int num : buckets[freq]) {
                    if (resultIndex == k) {
                        break;
                    }
                    result[resultIndex++] = num;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;

        TopKFrequentElements solution = new TopKFrequentElements();
        int[] result = solution.topKFrequent(nums, k);

        System.out.println("Top " + k + " frequent elements: " + Arrays.toString(result));
    }
}
