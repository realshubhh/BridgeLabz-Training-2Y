import java.util.Arrays;

public class RemoveDuplicatesFromSortedArray {

    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int writeIndex = 1;

        for (int readIndex = 1; readIndex < nums.length; readIndex++) {
            if (nums[readIndex] != nums[writeIndex - 1]) {
                nums[writeIndex] = nums[readIndex];
                writeIndex++;
            }
        }

        return writeIndex;
    }

    public static void main(String[] args) {
        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};

        RemoveDuplicatesFromSortedArray solution = new RemoveDuplicatesFromSortedArray();
        int newLength = solution.removeDuplicates(nums);

        System.out.println("New length: " + newLength);
        System.out.println("Array after removing duplicates: " + Arrays.toString(Arrays.copyOfRange(nums, 0, newLength)));
    }
}
