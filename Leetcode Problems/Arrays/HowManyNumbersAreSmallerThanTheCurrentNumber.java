import java.util.Arrays;

public class HowManyNumbersAreSmallerThanTheCurrentNumber {

    public int[] smallerNumbersThanCurrent(int[] nums) {
        int n = nums.length;
        int[] countByValue = new int[102];

        for (int num : nums) {
            countByValue[num + 1]++;
        }

        for (int i = 1; i < countByValue.length; i++) {
            countByValue[i] += countByValue[i - 1];
        }

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = countByValue[nums[i]];
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = {8, 1, 2, 2, 3};

        HowManyNumbersAreSmallerThanTheCurrentNumber solution = new HowManyNumbersAreSmallerThanTheCurrentNumber();
        int[] result = solution.smallerNumbersThanCurrent(nums);

        System.out.println("Result: " + Arrays.toString(result));
    }
}
