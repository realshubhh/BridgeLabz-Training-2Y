import java.util.*;

/**
 * H2 - Is there a pair of elements whose sum equals the target?
 * One pass: for each x, look up (target - x) among the numbers seen so far. If present -> pair found,
 * otherwise remember x. Because x is inserted AFTER the lookup, an element is never paired with itself,
 * but two equal values (e.g. 3 and 3 for target 6) are still found.
 * Time O(n) average, space O(n). Stored as long to avoid int overflow in target - x.
 */
public class PairWithSum {

    /** Returns the pair {a, b} (a appears before b) or null if no pair exists. */
    static int[] findPair(int[] a, int target) {
        Set<Long> seen = new HashSet<>();
        for (int x : a) {
            long need = (long) target - x;
            if (seen.contains(need)) return new int[]{(int) need, x};
            seen.add((long) x);
        }
        return null;
    }

    static boolean hasPair(int[] a, int target) { return findPair(a, target) != null; }

    static boolean bruteForce(int[] a, int target) {
        for (int i = 0; i < a.length; i++) for (int j = i + 1; j < a.length; j++) if ((long) a[i] + a[j] == target) return true;
        return false;
    }

    public static void main(String[] args) {
        Object[][] tests = { {new int[]{8, 7, 2, 5, 3, 1}, 10}, {new int[]{5, 2, 6, 8, 1, 9}, 12}, {new int[]{3, 3}, 6},
                {new int[]{3}, 6}, {new int[]{}, 0}, {new int[]{-4, 4, 10}, 0}, {new int[]{Integer.MAX_VALUE, 1}, Integer.MIN_VALUE} };
        for (Object[] t : tests) {
            int[] a = (int[]) t[0]; int target = (Integer) t[1];
            int[] p = findPair(a, target);
            System.out.println("  " + Arrays.toString(a) + ", target " + target + " -> " + (p == null ? "no pair" : "pair (" + p[0] + ", " + p[1] + ")"));
        }
        Random rnd = new Random(2);
        boolean ok = true;
        for (int t = 0; t < 3000 && ok; t++) {
            int[] a = new int[rnd.nextInt(10)];
            for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(21) - 10;
            int target = rnd.nextInt(31) - 15;
            ok = hasPair(a, target) == bruteForce(a, target);
        }
        System.out.println("  Randomised test (3000 cases) vs brute force: " + (ok ? "PASS" : "FAIL"));
    }
}
