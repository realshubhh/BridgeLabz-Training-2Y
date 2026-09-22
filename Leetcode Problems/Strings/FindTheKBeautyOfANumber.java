public class FindTheKBeautyOfANumber {

    public int divisorSubstrings(int num, int k) {
        String numString = Integer.toString(num);
        int count = 0;

        for (int i = 0; i + k <= numString.length(); i++) {
            String substring = numString.substring(i, i + k);
            int divisor = Integer.parseInt(substring);

            if (divisor != 0 && num % divisor == 0) {
                count++;
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int num = 240;
        int k = 2;

        FindTheKBeautyOfANumber solution = new FindTheKBeautyOfANumber();
        int result = solution.divisorSubstrings(num, k);

        System.out.println("K-Beauty of the number: " + result);
    }
}
