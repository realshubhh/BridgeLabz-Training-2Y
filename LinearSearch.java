/*
 * =====================================================================
 *  Problem TC-1 : Linear Search Time Complexity
 *  Problem SC-1 : Linear Search Space Complexity
 *  Sample Input Array: {8, 61, 2, 3, 4, 0}
 * =====================================================================
 *
 *  ---------------- TIME COMPLEXITY ANSWERS (TC-1) --------------------
 *
 *  1. BEST CASE  -> O(1)
 *     Explanation: The best case occurs when the target element is
 *     found at the very first index of the array. Using target = 8
 *     on {8, 61, 2, 3, 4, 0}, the very first comparison (index 0)
 *     matches, so the loop runs only ONCE regardless of array size N.
 *     Hence the number of operations is constant -> O(1).
 *
 *  2. WORST CASE -> O(N)
 *     Explanation: The worst case occurs when the target is either
 *     the LAST element of the array or NOT PRESENT at all. Using
 *     target = 0 (the last element) or target = 5 (not present),
 *     every single element must be compared before concluding, so
 *     the algorithm performs N comparisons for an array of size N.
 *     Hence the time complexity is O(N).
 *
 *  3. AVERAGE CASE -> O(N)
 *     Explanation: If the target is equally likely to be at any of
 *     the N positions (uniform distribution), the expected number
 *     of comparisons is (1 + 2 + 3 + ... + N) / N = (N+1)/2.
 *     Dropping the constant factor (1/2) and the lower-order term,
 *     this simplifies asymptotically to O(N). So on average, Linear
 *     Search is still linear in the size of the input.
 *
 *  ---------------- SPACE COMPLEXITY ANSWER (SC-1) --------------------
 *
 *  Auxiliary Space Complexity -> O(1)
 *     Explanation: Linear Search only uses a fixed number of extra
 *     variables (a loop counter "i" and the "target" value) no
 *     matter how large the input array is. It does NOT create any
 *     additional data structures whose size depends on N (no extra
 *     arrays, no recursion stack). Therefore the auxiliary
 *     (extra/helper) space used is constant -> O(1). Note: this is
 *     the AUXILIARY space; it does not count the O(N) space already
 *     occupied by the input array itself.
 * =====================================================================
 */

public class LinearSearch {

    /**
     * Iterative Linear Search.
     * Returns the index of 'target' in 'arr', or -1 if not found.
     */
    public static int linearSearch(int[] arr, int target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("   Found " + target + " at index " + i
                        + " after " + comparisons + " comparison(s).");
                return i;
            }
        }
        System.out.println("   " + target + " not found after " + comparisons + " comparison(s).");
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {8, 61, 2, 3, 4, 0};

        System.out.println("Array: {8, 61, 2, 3, 4, 0}\n");

        System.out.println("Best Case Demo  (target = 8, first element):");
        linearSearch(arr, 8);

        System.out.println("\nWorst Case Demo (target = 0, last element):");
        linearSearch(arr, 0);

        System.out.println("\nWorst Case Demo (target = 5, not present):");
        linearSearch(arr, 5);

        System.out.println("\nAverage Case Demo (target = 3, middle element):");
        linearSearch(arr, 3);
    }
}
