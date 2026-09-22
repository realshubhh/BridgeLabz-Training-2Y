public class MaximumAbsoluteSumOfAnySubarray {

    public int maxAbsoluteSum(int[] nums) {
        int maxPrefixSum = 0;
        int minPrefixSum = 0;
        int currentSum = 0;

        for (int num : nums) {
            currentSum += num;
            maxPrefixSum = Math.max(maxPrefixSum, currentSum);
            minPrefixSum = Math.min(minPrefixSum, currentSum);
        }

        return maxPrefixSum - minPrefixSum;
    }

    public static void main(String[] args) {
        int[] nums = {1, -3, 2, 3, -4};

        MaximumAbsoluteSumOfAnySubarray solution = new MaximumAbsoluteSumOfAnySubarray();
        int result = solution.maxAbsoluteSum(nums);

        System.out.println("Maximum absolute subarray sum: " + result);
    }
}
