public class FindNumbersWithEvenNumberOfDigits {

    public int findNumbers(int[] nums) {
        int count = 0;

        for (int num : nums) {
            if (countDigits(num) % 2 == 0) {
                count++;
            }
        }

        return count;
    }

    private int countDigits(int num) {
        if (num == 0) {
            return 1;
        }

        int digits = 0;
        while (num != 0) {
            digits++;
            num /= 10;
        }

        return digits;
    }

    public static void main(String[] args) {
        int[] nums = {12, 345, 2, 6, 7896};

        FindNumbersWithEvenNumberOfDigits solution = new FindNumbersWithEvenNumberOfDigits();
        int result = solution.findNumbers(nums);

        System.out.println("Count of numbers with even digits: " + result);
    }
}
