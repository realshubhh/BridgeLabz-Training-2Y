public class StringToIntegerAtoi {

    public int myAtoi(String s) {
        int index = 0;
        int n = s.length();

        while (index < n && s.charAt(index) == ' ') {
            index++;
        }

        if (index == n) {
            return 0;
        }

        int sign = 1;
        if (s.charAt(index) == '+' || s.charAt(index) == '-') {
            sign = (s.charAt(index) == '-') ? -1 : 1;
            index++;
        }

        long result = 0;

        while (index < n && Character.isDigit(s.charAt(index))) {
            int digit = s.charAt(index) - '0';
            result = result * 10 + digit;

            if (sign == 1 && result > Integer.MAX_VALUE) {
                return Integer.MAX_VALUE;
            }

            if (sign == -1 && -result < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }

            index++;
        }

        return (int) (sign * result);
    }

    public static void main(String[] args) {
        String s = "   -42";

        StringToIntegerAtoi solution = new StringToIntegerAtoi();
        int result = solution.myAtoi(s);

        System.out.println("Parsed integer: " + result);
    }
}
