/*
 * =====================================================================
 *  Problem TC-3 : Merge Sort Time Complexity
 *  Problem SC-3 : Merge Sort Space Complexity
 *  Sample Input Array: {8, 61, 2, 3, 4, 0}
 * =====================================================================
 *
 *  ---------------- TIME COMPLEXITY ANSWER (TC-3) ----------------------
 *
 *  1. DIVISION / SPLIT TREE for {8, 61, 2, 3, 4, 0}:
 *
 *                       {8, 61, 2, 3, 4, 0}                    (Level 0, size 6)
 *                        /                \
 *              {8, 61, 2}                {3, 4, 0}            (Level 1, size 3 each)
 *               /      \                  /      \
 *            {8, 61}    {2}           {3, 4}      {0}         (Level 2)
 *             /   \                    /   \
 *           {8}   {61}               {3}   {4}                (Level 3, size 1 - base case)
 *
 *     The array keeps splitting in half until every sub-array has
 *     exactly one element (a single element is trivially sorted).
 *     Then MERGE combines pairs back together in sorted order:
 *     {8,61} & {2} -> merge -> {2,8,61}
 *     {3,4} & {0}  -> merge -> {0,3,4}
 *     {2,8,61} & {0,3,4} -> merge -> {0,2,3,4,8,61}  (final sorted array)
 *
 *  2. RECURRENCE RELATION & RECURSION TREE SOLUTION:
 *
 *     T(N) = 2*T(N/2) + O(N)
 *       - 2*T(N/2)  -> cost of recursively sorting the two halves
 *       - O(N)      -> cost of merging the two sorted halves together
 *
 *     Recursion tree analysis:
 *
 *       Level 0:  1 problem of size N        -> work = N
 *       Level 1:  2 problems of size N/2      -> work = 2 * (N/2) = N
 *       Level 2:  4 problems of size N/4      -> work = 4 * (N/4) = N
 *       Level 3:  8 problems of size N/8      -> work = 8 * (N/8) = N
 *       ...
 *       Level k:  2^k problems of size N/2^k  -> work = N   (constant per level!)
 *
 *     Every level does O(N) total work (the pieces get smaller, but
 *     there are proportionally more of them, and the merge cost adds
 *     back up to N each time).
 *
 *     Height of the tree = number of times N can be halved until
 *     reaching size 1 = log2(N).
 *
 *     Total work = (work per level) * (number of levels)
 *                = O(N) * O(log N)
 *                = O(N log N)
 *
 *     FINAL TIME COMPLEXITY: O(N log N)  -- for best, average, AND
 *     worst case, since Merge Sort always splits evenly regardless
 *     of the input's initial order.
 *
 *  ---------------- SPACE COMPLEXITY ANSWER (SC-3) ---------------------
 *
 *  1. Auxiliary Space Complexity -> O(N)
 *     Explanation: Unlike Linear/Binary Search, Merge Sort's MERGE
 *     step cannot combine two sorted halves back together purely
 *     in-place. It must copy elements into a TEMPORARY array (see
 *     the 'temp' array in merge() below) to hold the merged, sorted
 *     result before copying it back into the original array. The
 *     size of this temporary storage scales directly with the
 *     number of elements N, giving an auxiliary space complexity of
 *     O(N). (There is also O(log N) space used by the recursion
 *     call stack, but this is dominated by the O(N) auxiliary array,
 *     so the overall auxiliary space is O(N).)
 *
 *  2. COMPARISON WITH IN-PLACE SORTS (Insertion Sort / Quick Sort):
 *     - Insertion Sort is fully IN-PLACE: it only needs O(1) extra
 *       space because it shifts elements within the same array using
 *       a single temporary variable to hold the "key" being inserted.
 *     - Quick Sort (in its standard in-place partitioning form) also
 *       only needs O(log N) auxiliary space on average, which comes
 *       from the recursion call stack alone -- it partitions and
 *       swaps elements within the original array rather than copying
 *       them into new arrays.
 *     - Merge Sort trades away this space efficiency for a GUARANTEED
 *       O(N log N) worst-case time (Quick Sort can degrade to O(N^2)
 *       in the worst case, and Insertion Sort is O(N^2) in general).
 *       So Merge Sort is more memory-hungry (O(N) vs O(1)/O(log N))
 *       but offers more predictable, stable performance -- a classic
 *       time-vs-space trade-off.
 * =====================================================================
 */

public class MergeSort {

    /**
     * Recursively splits the array and merges sorted halves.
     */
    public static void mergeSort(int[] arr, int left, int right, int depth) {
        if (left >= right) {
            return; // base case: sub-array of size 1 (already "sorted")
        }
        int mid = (left + right) / 2;

        indent(depth);
        System.out.println("Split -> " + subArrayToString(arr, left, right)
                + "  into  " + subArrayToString(arr, left, mid)
                + " and " + subArrayToString(arr, mid + 1, right));

        mergeSort(arr, left, mid, depth + 1);       // sort left half
        mergeSort(arr, mid + 1, right, depth + 1);  // sort right half
        merge(arr, left, mid, right, depth);        // merge sorted halves
    }

    /**
     * Merges two sorted sub-arrays arr[left..mid] and arr[mid+1..right]
     * using an auxiliary temporary array (this is the O(N) extra space).
     */
    private static void merge(int[] arr, int left, int mid, int right, int depth) {
        int[] temp = new int[right - left + 1]; // <-- auxiliary O(N) space
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        for (int m = 0; m < temp.length; m++) {
            arr[left + m] = temp[m];
        }

        indent(depth);
        System.out.println("Merge -> " + subArrayToString(arr, left, right));
    }

    private static void indent(int depth) {
        for (int d = 0; d < depth; d++) System.out.print("   ");
    }

    private static String subArrayToString(int[] arr, int left, int right) {
        StringBuilder sb = new StringBuilder("{");
        for (int i = left; i <= right; i++) {
            sb.append(arr[i]);
            if (i != right) sb.append(", ");
        }
        return sb.append("}").toString();
    }

    public static void main(String[] args) {
        int[] arr = {8, 61, 2, 3, 4, 0};
        System.out.println("Original Array: {8, 61, 2, 3, 4, 0}\n");
        mergeSort(arr, 0, arr.length - 1, 0);
        System.out.println("\nFinal Sorted Array: " + subArrayToString(arr, 0, arr.length - 1));
    }
}
