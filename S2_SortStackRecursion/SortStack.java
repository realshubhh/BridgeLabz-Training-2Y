import java.util.*;

/**
 * S2 - Sort a stack using recursion only (no loops, no extra data structure).
 * After sorting, the LARGEST element is on top (reading bottom -> top gives ascending order).
 *
 *   sortStack(s):   if empty -> return (BASE CASE)
 *                   x = pop; sortStack(s); sortedInsert(s, x)
 *   sortedInsert(s,x): if empty or top <= x -> push x (BASE CASE)
 *                   else t = pop; sortedInsert(s,x); push t
 * Time O(n^2), extra space O(n) for the recursion stack.
 */
public class SortStack {

    static void sortStack(Deque<Integer> s) {
        if (s.isEmpty()) return;                       // base case
        int x = s.pop();
        sortStack(s);                                   // sort the smaller problem
        sortedInsert(s, x);                             // put x in its correct place
    }

    static void sortedInsert(Deque<Integer> s, int x) {
        if (s.isEmpty() || s.peek() <= x) { s.push(x); return; }   // base case
        int top = s.pop();
        sortedInsert(s, x);
        s.push(top);                                    // restore on the way back
    }

    static String bottomToTop(Deque<Integer> s) {
        StringBuilder sb = new StringBuilder("[");
        Iterator<Integer> it = s.descendingIterator();   // descendingIterator = bottom -> top
        while (it.hasNext()) sb.append(it.next()).append(it.hasNext() ? ", " : "");
        return sb.append("]").toString();
    }

    static Deque<Integer> stackOf(int... a) {            // pushes in the given order (last = top)
        Deque<Integer> s = new ArrayDeque<>();
        for (int x : a) s.push(x);
        return s;
    }

    public static void main(String[] args) {
        int[][] tests = { {34, 3, 31, 98, 92, 23}, {5, 4, 3, 2, 1}, {1, 2, 3}, {7, 7, 2, 7, 2}, {42}, {} };
        for (int[] t : tests) {
            Deque<Integer> s = stackOf(t);
            String before = bottomToTop(s);
            sortStack(s);
            System.out.println("  " + before + "  ->  " + bottomToTop(s) + "   (top = " + (s.isEmpty() ? "none" : s.peek()) + ")");
        }

        System.out.println("\n== Randomised test (300 stacks) against Arrays.sort ==");
        Random rnd = new Random(11);
        boolean ok = true;
        for (int t = 0; t < 300 && ok; t++) {
            int n = rnd.nextInt(40);
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = rnd.nextInt(20) - 10;
            Deque<Integer> s = stackOf(a);
            sortStack(s);
            int[] expected = a.clone(); Arrays.sort(expected);
            Iterator<Integer> it = s.descendingIterator();
            for (int i = 0; i < n && ok; i++) ok = it.next() == expected[i];
        }
        System.out.println("  " + (ok ? "PASS" : "FAIL"));
    }
}
