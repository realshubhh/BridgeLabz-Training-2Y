import java.util.*;

public class FindLuckyIntegerInAnArray {
    public static int findLucky(int[] arr) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (int num : arr) {
            frequency.put(num, frequency.getOrDefault(num, 0) + 1);
        }

        int answer = -1;

        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getKey().equals(entry.getValue())) {
                answer = Math.max(answer, entry.getKey());
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 3, 4};
        System.out.println("Lucky integer: " + findLucky(arr));
    }
}
