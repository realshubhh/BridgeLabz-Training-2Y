import java.util.*;

/**
 * S4 - Sliding Window Maximum using a deque of INDICES.
 * Invariant: the deque holds indices of the current window in decreasing order of value,
 *            so the front is always the index of the window maximum.
 * For every i: (1) drop the front if it left the window, (2) drop from the back all values <= a[i]
 *              (they can never be a maximum while a[i] is in the window), (3) add i.
 * Each index enters and leaves once -> O(n) time, O(k) space.
 */
public class SlidingWindowMax {

    static int[] maxSlidingWindow(int[] a, int k) {
        if (a == null) throw new IllegalArgumentException("array is null");
        if (k <= 0) throw new IllegalArgumentException("window size must be positive");
        if (a.length == 0) return new int[0];
        if (k > a.length) throw new IllegalArgumentException("window size larger than array");

        int[] res = new int[a.length - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < a.length; i++) {
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();            // out of window
            while (!dq.isEmpty() && a[dq.peekLast()] <= a[i]) dq.pollLast();         // useless elements
            dq.offerLast(i);
            if (i >= k - 1) res[i - k + 1] = a[dq.peekFirst()];
        }
        return res;
    }

    static int[] bruteForce(int[] a, int k) {
        int[] r = new int[a.length - k + 1];
        for (int i = 0; i < r.length; i++) { int m = Integer.MIN_VALUE; for (int j = i; j < i + k; j++) m = Math.max(m, a[j]); r[i] = m; }
        return r;
    }

    public static void main(String[] args) {
        int[] a = {1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println("  array " + Arrays.toString(a) + ", k = 3");
        System.out.println("  max   " + Arrays.toString(maxSlidingWindow(a, 3)));
        System.out.println("  k = 1 : " + Arrays.toString(maxSlidingWindow(a, 1)));
        System.out.println("  k = 8 : " + Arrays.toString(maxSlidingWindow(a, 8)));
        System.out.println("  all equal [4,4,4,4], k=2 : " + Arrays.toString(maxSlidingWindow(new int[]{4, 4, 4, 4}, 2)));
        System.out.println("  descending [9,8,7,6], k=2 : " + Arrays.toString(maxSlidingWindow(new int[]{9, 8, 7, 6}, 2)));
        try { maxSlidingWindow(a, 0); } catch (IllegalArgumentException e) { System.out.println("  k = 0 rejected: " + e.getMessage()); }
        try { maxSlidingWindow(a, 20); } catch (IllegalArgumentException e) { System.out.println("  k = 20 rejected: " + e.getMessage()); }

        Random rnd = new Random(3);
        boolean ok = true;
        for (int t = 0; t < 1000 && ok; t++) {
            int n = 1 + rnd.nextInt(40);
            int[] x = new int[n];
            for (int i = 0; i < n; i++) x[i] = rnd.nextInt(21) - 10;
            int k = 1 + rnd.nextInt(n);
            ok = Arrays.equals(maxSlidingWindow(x, k), bruteForce(x, k));
        }
        System.out.println("  Randomised test (1000 cases) vs brute force: " + (ok ? "PASS" : "FAIL"));
    }
}
