public class FindNumbersWithEvenNumberOfDigits {
    public static int findNumbers(int[] nums) {
        int count = 0;

        for (int num : nums) {
            int value = Math.abs(num);
            int digits = 0;

            if (value == 0) {
                digits = 1;
            } else {
                while (value > 0) {
                    digits++;
                    value /= 10;
                }
            }

            if (digits % 2 == 0) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int[] nums = {12, 345, 2, 6, 7896};
        System.out.println("Count: " + findNumbers(nums));
    }
}
