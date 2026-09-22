import java.util.Arrays;

public class BuildArrayFromPermutation {

    public int[] buildArray(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        for (int i = 0; i < n; i++) {
            answer[i] = nums[nums[i]];
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] nums = {0, 2, 1, 5, 3, 4};

        BuildArrayFromPermutation solution = new BuildArrayFromPermutation();
        int[] result = solution.buildArray(nums);

        System.out.println("Result: " + Arrays.toString(result));
    }
}
