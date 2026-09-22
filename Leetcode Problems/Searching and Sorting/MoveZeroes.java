import java.util.*;

public class MoveZeroes {
    public static void moveZeroes(int[] nums) {
        int insert = 0;

        for (int num : nums) {
            if (num != 0) {
                nums[insert++] = num;
            }
        }

        while (insert < nums.length) {
            nums[insert++] = 0;
        }
    }

    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 3, 12};
        moveZeroes(nums);

        System.out.println(Arrays.toString(nums));
    }
}
