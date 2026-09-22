import java.util.*;

public class RandomPickWithWeight {
    private int[] prefixSums;
    private Random random;

    public RandomPickWithWeight(int[] weights) {
        prefixSums = new int[weights.length];
        int sum = 0;

        for (int i = 0; i < weights.length; i++) {
            sum += weights[i];
            prefixSums[i] = sum;
        }

        random = new Random();
    }

    public int pickIndex() {
        int target = random.nextInt(prefixSums[prefixSums.length - 1]) + 1;

        int left = 0, right = prefixSums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (prefixSums[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        RandomPickWithWeight picker =
                new RandomPickWithWeight(new int[]{1, 3});

        System.out.println("Randomly selected index: "
                + picker.pickIndex());
    }
}
