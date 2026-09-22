import java.util.Arrays;

public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        int writeIndex = 0;

        for (int readIndex = 0; readIndex < nums.length; readIndex++) {
            if (nums[readIndex] != val) {
                nums[writeIndex] = nums[readIndex];
                writeIndex++;
            }
        }

        return writeIndex;
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 2, 3, 6};
        int val = 3;

        RemoveElement solution = new RemoveElement();
        int newLength = solution.removeElement(nums, val);

        System.out.println("New length: " + newLength);
        System.out.println("Array after removal: " + Arrays.toString(Arrays.copyOfRange(nums, 0, newLength)));
    }
}
