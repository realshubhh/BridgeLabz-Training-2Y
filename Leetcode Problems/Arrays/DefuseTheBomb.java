import java.util.Arrays;

public class DefuseTheBomb {

    public int[] decrypt(int[] code, int k) {
        int n = code.length;
        int[] result = new int[n];

        if (k == 0) {
            return result;
        }

        for (int i = 0; i < n; i++) {
            int sum = 0;

            if (k > 0) {
                for (int j = 1; j <= k; j++) {
                    sum += code[(i + j) % n];
                }
            } else {
                for (int j = 1; j <= -k; j++) {
                    sum += code[((i - j) % n + n) % n];
                }
            }

            result[i] = sum;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] code = {5, 7, 1, 4};
        int k = 3;

        DefuseTheBomb solution = new DefuseTheBomb();
        int[] result = solution.decrypt(code, k);

        System.out.println("Decrypted code: " + Arrays.toString(result));
    }
}
