import java.util.*;

/**
 * H5 - Two Sum: return the INDICES of two elements that add up to the target.
 * Map: value -> index of its latest occurrence seen so far. For each i, if (target - a[i]) is in the map
 * its stored index j < i is the partner. The lookup happens before the insert, so an element is
 * never used twice, and duplicates such as [3, 3] with target 6 work.
 * Time O(n), space O(n) (versus O(n^2) brute force).
 */
public class TwoSum {

    /** Returns {i, j} with i < j and a[i] + a[j] == target, or null if none exists. */
    static int[] twoSum(int[] a, int target) {
        Map<Long, Integer> indexOf = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            long need = (long) target - a[i];
            Integer j = indexOf.get(need);
            if (j != null) return new int[]{j, i};
            indexOf.put((long) a[i], i);
        }
        return null;
    }

    static boolean exists(int[] a, int target) {
        for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) if ((long) a[i] + a[j] == target) return true;
        return false;
    }

    public static void main(String[] args) {
        Object[][] tests = { {new int[]{2, 7, 11, 15}, 9}, {new int[]{3, 2, 4}, 6}, {new int[]{3, 3}, 6}, {new int[]{1, 2, 3}, 7},
                {new int[]{-3, 4, 3, 90}, 0}, {new int[]{5}, 10}, {new int[]{}, 1} };
        for (Object[] t : tests) {
            int[] a = (int[]) t[0]; int target = (Integer) t[1];
            int[] r = twoSum(a, target);
            System.out.println("  " + Arrays.toString(a) + ", target " + target + " -> "
                    + (r == null ? "no solution" : "indices [" + r[0] + ", " + r[1] + "]  (" + a[r[0]] + " + " + a[r[1]] + ")"));
        }
        Random rnd = new Random(8);
        boolean ok = true;
        for (int t = 0; t < 3000 && ok; t++) {
            int[] a = new int[rnd.nextInt(12)];
            for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(21) - 10;
            int target = rnd.nextInt(31) - 15;
            int[] r = twoSum(a, target);
            if (r == null) ok = !exists(a, target);
            else ok = r[0] != r[1] && (long) a[r[0]] + a[r[1]] == target;
        }
        System.out.println("  Randomised test (3000 cases) vs brute force: " + (ok ? "PASS" : "FAIL"));
    }
}
