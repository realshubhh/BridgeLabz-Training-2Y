public class LargestNumberAtLeastTwiceOthers {
    public static int dominantIndex(int[] nums) {
        int largest = -1;
        int secondLargest = -1;
        int largestIndex = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > largest) {
                secondLargest = largest;
                largest = nums[i];
                largestIndex = i;
            } else if (nums[i] > secondLargest) {
                secondLargest = nums[i];
            }
        }

        return largest >= 2 * secondLargest ? largestIndex : -1;
    }

    public static void main(String[] args) {
        int[] nums = {3, 6, 1, 0};
        System.out.println("Dominant index: " + dominantIndex(nums));
    }
}
