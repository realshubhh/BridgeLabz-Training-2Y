public class RangeSumQueryImmutable {

    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};

        NumArray numArray = new NumArray(nums);

        System.out.println("Sum of range [0, 2]: " + numArray.sumRange(0, 2));
        System.out.println("Sum of range [2, 5]: " + numArray.sumRange(2, 5));
        System.out.println("Sum of range [0, 5]: " + numArray.sumRange(0, 5));
    }
}

class NumArray {
    private final int[] prefixSums;

    public NumArray(int[] nums) {
        prefixSums = new int[nums.length + 1];

        for (int i = 0; i < nums.length; i++) {
            prefixSums[i + 1] = prefixSums[i] + nums[i];
        }
    }

    public int sumRange(int left, int right) {
        return prefixSums[right + 1] - prefixSums[left];
    }
}
