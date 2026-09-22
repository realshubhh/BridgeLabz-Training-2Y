public class MaximumSubarraySumWithOneDeletion {

    public int maximumSum(int[] arr) {
        int n = arr.length;

        // noDeletion: max subarray sum ending here with no deletion used
        // oneDeletion: max subarray sum ending here with exactly one deletion used
        int noDeletion = arr[0];
        int oneDeletion = 0;
        int result = arr[0];

        for (int i = 1; i < n; i++) {
            oneDeletion = Math.max(oneDeletion + arr[i], noDeletion);
            noDeletion = Math.max(noDeletion + arr[i], arr[i]);

            result = Math.max(result, Math.max(noDeletion, oneDeletion));
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, -2, 0, 3};

        MaximumSubarraySumWithOneDeletion solution = new MaximumSubarraySumWithOneDeletion();
        int result = solution.maximumSum(arr);

        System.out.println("Maximum subarray sum with one deletion allowed: " + result);
    }
}
