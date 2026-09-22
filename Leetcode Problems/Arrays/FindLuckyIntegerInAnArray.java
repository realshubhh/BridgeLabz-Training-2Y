import java.util.HashMap;
import java.util.Map;

public class FindLuckyIntegerInAnArray {

    public int findLucky(int[] arr) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (int num : arr) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        int luckyNumber = -1;

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getKey().equals(entry.getValue())) {
                luckyNumber = Math.max(luckyNumber, entry.getKey());
            }
        }

        return luckyNumber;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 3, 4};

        FindLuckyIntegerInAnArray solution = new FindLuckyIntegerInAnArray();
        int result = solution.findLucky(arr);

        System.out.println("Lucky integer: " + result);
    }
}
