import java.util.*;

public class ThirdMaximumNumber {
    public static int thirdMax(int[] nums) {
        Long first = null, second = null, third = null;

        for (int num : nums) {
            long value = num;

            if (Objects.equals(first, value) ||
                Objects.equals(second, value) ||
                Objects.equals(third, value)) {
                continue;
            }

            if (first == null || value > first) {
                third = second;
                second = first;
                first = value;
            } else if (second == null || value > second) {
                third = second;
                second = value;
            } else if (third == null || value > third) {
                third = value;
            }
        }

        return third == null ? first.intValue() : third.intValue();
    }

    public static void main(String[] args) {
        int[] nums = {3, 2, 1};
        System.out.println("Third maximum: " + thirdMax(nums));
    }
}
