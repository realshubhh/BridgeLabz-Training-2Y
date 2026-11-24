import java.util.*;

/**
 * S3 - Stock Span. span[i] = number of consecutive days up to and including i with price <= price[i].
 * The stack keeps INDICES of days whose prices are strictly decreasing from bottom to top.
 * For day i: pop every index with price <= price[i]; the remaining top (if any) is the
 * previous day with a HIGHER price, so span = i - topIndex (or i + 1 if the stack is empty).
 * Each index is pushed once and popped at most once -> O(n) time, O(n) space.
 */
public class StockSpan {

    static int[] stockSpan(int[] price) {
        int n = price.length;
        int[] span = new int[n];
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && price[st.peek()] <= price[i]) st.pop();
            span[i] = st.isEmpty() ? i + 1 : i - st.peek();
            st.push(i);
        }
        return span;
    }

    static int[] bruteForce(int[] p) {                       // O(n^2) checker
        int[] s = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            int c = 0;
            for (int j = i; j >= 0 && p[j] <= p[i]; j--) c++;
            s[i] = c;
        }
        return s;
    }

    public static void main(String[] args) {
        int[][] tests = { {100, 80, 60, 70, 60, 75, 85}, {10, 4, 5, 90, 120, 80}, {10, 20, 30, 40}, {40, 30, 20, 10}, {5, 5, 5}, {7}, {} };
        for (int[] p : tests)
            System.out.println("  prices " + Arrays.toString(p) + "\n  spans  " + Arrays.toString(stockSpan(p)) + "\n");

        System.out.println("Day-by-day trace for [100, 80, 60, 70, 60, 75, 85]:");
        int[] p = {100, 80, 60, 70, 60, 75, 85};
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < p.length; i++) {
            while (!st.isEmpty() && p[st.peek()] <= p[i]) st.pop();
            int span = st.isEmpty() ? i + 1 : i - st.peek();
            st.push(i);
            List<Integer> view = new ArrayList<>(st); Collections.reverse(view);
            System.out.printf("  day %d price %-3d span %d   stack (bottom->top, indices) = %s%n", i, p[i], span, view);
        }

        Random rnd = new Random(5);
        boolean ok = true;
        for (int t = 0; t < 500 && ok; t++) {
            int[] a = new int[rnd.nextInt(30)];
            for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(15);
            ok = Arrays.equals(stockSpan(a), bruteForce(a));
        }
        System.out.println("\n  Randomised test (500 arrays) vs brute force: " + (ok ? "PASS" : "FAIL"));
    }
}
