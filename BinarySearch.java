/*
 * =====================================================================
 *  Problem TC-2 : Iterative Binary Search Time Complexity
 *  Problem SC-2 : Binary Search Space Complexity
 *  Sample Sorted Array: {0, 2, 3, 4, 8, 61}   (indices 0..5)
 * =====================================================================
 *
 *  ---------------- TIME COMPLEXITY ANSWER (TC-2) ----------------------
 *
 *  1. STEP-BY-STEP NARROWING for target = 4:
 *
 *     Array (index):  0:0  1:2  2:3  3:4  4:8  5:61
 *
 *     Step 1: low = 0, high = 5, mid = (0+5)/2 = 2 -> arr[2] = 3
 *             3 < 4  -> target is in the right half
 *             new low = mid + 1 = 3
 *
 *     Step 2: low = 3, high = 5, mid = (3+5)/2 = 4 -> arr[4] = 8
 *             8 > 4  -> target is in the left half
 *             new high = mid - 1 = 3
 *
 *     Step 3: low = 3, high = 3, mid = (3+3)/2 = 3 -> arr[3] = 4
 *             4 == 4 -> TARGET FOUND at index 3
 *
 *     The search space was halved at every step:
 *        size 6 -> size 3 (approx.) -> size 1  (found in 3 steps)
 *
 *  2. DERIVATION of worst-case comparisons for size N:
 *
 *     After each comparison, the search space is divided by 2.
 *     Starting with N elements, after k comparisons the remaining
 *     search space size is N / 2^k.
 *     The search ends (worst case) when the remaining space becomes
 *     less than 1, i.e.:
 *
 *          N / 2^k = 1
 *          2^k = N
 *          k = log2(N)
 *
 *     So the number of comparisons in the worst case grows as
 *     log2(N) -> Time Complexity = O(log N)
 *
 *     This is dramatically faster than Linear Search's O(N) because
 *     each comparison eliminates HALF of the remaining elements
 *     instead of just one.
 *
 *  ---------------- SPACE COMPLEXITY ANSWER (SC-2) ---------------------
 *
 *  Auxiliary Space Complexity (Iterative version) -> O(1)
 *     Explanation: The iterative implementation below only keeps
 *     three extra integer variables (low, high, mid) regardless of
 *     how large N is. No extra arrays or recursive call stack frames
 *     are created, so the auxiliary space stays constant -> O(1).
 *     (Note: a RECURSIVE Binary Search would instead use O(log N)
 *     auxiliary space because of the recursion call stack depth.)
 * =====================================================================
 */

public class BinarySearch {

    /**
     * Iterative Binary Search on a SORTED array.
     * Returns the index of 'target', or -1 if not found.
     * Prints each step so the search-space narrowing is visible.
     */
    public static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int step = 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            System.out.println("   Step " + step + ": low=" + low + ", high=" + high
                    + ", mid=" + mid + " -> arr[mid]=" + arr[mid]);

            if (arr[mid] == target) {
                System.out.println("   Target " + target + " found at index " + mid);
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;   // discard left half
            } else {
                high = mid - 1;  // discard right half
            }
            step++;
        }
        System.out.println("   Target " + target + " not found.");
        return -1;
    }

    public static void main(String[] args) {
        int[] sortedArr = {0, 2, 3, 4, 8, 61};

        System.out.println("Sorted Array: {0, 2, 3, 4, 8, 61}\n");
        System.out.println("Searching for target = 4:");
        binarySearch(sortedArr, 4);

        System.out.println("\nSearching for target = 61 (worst case, edge element):");
        binarySearch(sortedArr, 61);

        System.out.println("\nSearching for target = 5 (not present):");
        binarySearch(sortedArr, 5);
    }
}
