import java.util.*;

/**
 * H3 - Longest Consecutive Sequence in an unsorted array, in O(n).
 * Put every value in a hash set (duplicates vanish). Only start counting from a value x that is the
 * START of a run (x - 1 is NOT in the set); then walk x+1, x+2, ... while present.
 * Every element is visited at most twice overall -> O(n) time, O(n) space (no sorting needed).
 */
public class LongestConsecutive {

    /** Returns {length, startValue}; {0, 0} for an empty array. */
    static long[] longest(int[] a) {
        Set<Long> set = new HashSet<>();
        for (int x : a) set.add((long) x);
        long best = 0, bestStart = 0;
        for (long x : set) {
            if (set.contains(x - 1)) continue;               // not the beginning of a run
            long cur = x, len = 1;
            while (set.contains(cur + 1)) { cur++; len++; }
            if (len > best) { best = len; bestStart = x; }
        }
        return new long[]{best, bestStart};
    }

    static int bruteForceSorted(int[] a) {                   // O(n log n) checker
        if (a.length == 0) return 0;
        int[] s = a.clone(); Arrays.sort(s);
        int best = 1, run = 1;
        for (int i = 1; i < s.length; i++) {
            if ((long) s[i] == (long) s[i - 1] + 1) run++;
            else if (s[i] != s[i - 1]) run = 1;
            best = Math.max(best, run);
        }
        return best;
    }

    public static void main(String[] args) {
        int[][] tests = { {100, 4, 200, 1, 3, 2}, {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, {9, 1, 4, 7, 3, -1, 0, 5, 8, -1, 6},
                {1, 1, 1}, {5}, {}, {Integer.MIN_VALUE, Integer.MAX_VALUE}, {-2, -1, 0, 1} };
        for (int[] a : tests) {
            long[] r = longest(a);
            String seq = r[0] == 0 ? "-" : r[1] + ".." + (r[1] + r[0] - 1);
            System.out.println("  " + Arrays.toString(a) + " -> length " + r[0] + "  (" + seq + ")");
        }
        Random rnd = new Random(17);
        boolean ok = true;
        for (int t = 0; t < 2000 && ok; t++) {
            int[] a = new int[rnd.nextInt(30)];
            for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(30) - 15;
            ok = longest(a)[0] == bruteForceSorted(a);
        }
        System.out.println("  Randomised test (2000 arrays) vs sorting approach: " + (ok ? "PASS" : "FAIL"));
    }
}
