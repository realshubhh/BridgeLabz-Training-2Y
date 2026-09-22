import java.util.Arrays;

public class MinimumDifferenceBetweenHighestAndLowestOfKScores {

    public int minimumDifference(int[] nums, int k) {
        if (k == 1) {
            return 0;
        }

        Arrays.sort(nums);
        int minDifference = Integer.MAX_VALUE;

        for (int i = 0; i + k - 1 < nums.length; i++) {
            int difference = nums[i + k - 1] - nums[i];
            minDifference = Math.min(minDifference, difference);
        }

        return minDifference;
    }

    public static void main(String[] args) {
        int[] nums = {9, 4, 1, 7};
        int k = 2;

        MinimumDifferenceBetweenHighestAndLowestOfKScores solution = new MinimumDifferenceBetweenHighestAndLowestOfKScores();
        int result = solution.minimumDifference(nums, k);

        System.out.println("Minimum difference: " + result);
    }
}
