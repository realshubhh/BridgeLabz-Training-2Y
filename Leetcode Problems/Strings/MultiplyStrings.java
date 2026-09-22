public class MultiplyStrings {

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }

        int n = num1.length();
        int m = num2.length();
        int[] product = new int[n + m];

        for (int i = n - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';

            for (int j = m - 1; j >= 0; j--) {
                int digit2 = num2.charAt(j) - '0';

                int sum = product[i + j + 1] + digit1 * digit2;
                product[i + j + 1] = sum % 10;
                product[i + j] += sum / 10;
            }
        }

        StringBuilder result = new StringBuilder();
        for (int digit : product) {
            if (!(result.length() == 0 && digit == 0)) {
                result.append(digit);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String num1 = "123";
        String num2 = "456";

        MultiplyStrings solution = new MultiplyStrings();
        String result = solution.multiply(num1, num2);

        System.out.println("Product: " + result);
    }
}
