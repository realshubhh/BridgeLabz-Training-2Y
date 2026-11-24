import java.util.*;

/**
 * S5 - Circular Tour (gas station problem), solved with a QUEUE as the hint suggests.
 * The queue holds the pumps of the tour currently being attempted; 'surplus' = petrol collected - distance used.
 *   - add the next pump to the back of the queue
 *   - while surplus < 0 : the tour cannot start at the front pump, so remove it from the front
 *   - if the queue holds all n pumps with surplus >= 0, its front is a valid starting pump
 * Each pump is added at most twice (2n steps) and removed at most once -> O(n) time, O(n) space.
 * Returns the 0-based index of a starting pump, or -1 if the tour is impossible.
 */
public class CircularTour {

    static int findStart(int[] petrol, int[] dist) {
        if (petrol.length != dist.length) throw new IllegalArgumentException("arrays must have equal length");
        int n = petrol.length;
        if (n == 0) return -1;
        Queue<Integer> q = new ArrayDeque<>();
        long surplus = 0;
        int next = 0;
        for (int step = 0; step < 2 * n; step++) {
            q.add(next);
            surplus += petrol[next] - dist[next];
            next = (next + 1) % n;
            while (surplus < 0 && !q.isEmpty()) {                        // drop starting points that fail
                int f = q.poll();
                surplus -= petrol[f] - dist[f];
            }
            if (q.size() == n) return q.peek();
        }
        return -1;
    }

    static boolean isValidStart(int[] p, int[] d, int s) {                // brute-force checker
        long tank = 0;
        for (int i = 0; i < p.length; i++) {
            int j = (s + i) % p.length;
            tank += p[j] - d[j];
            if (tank < 0) return false;
        }
        return true;
    }

    static boolean anyStartExists(int[] p, int[] d) {
        for (int s = 0; s < p.length; s++) if (isValidStart(p, d, s)) return true;
        return false;
    }

    public static void main(String[] args) {
        int[][][] tests = {
            { {4, 6, 7, 4}, {6, 5, 3, 5} },
            { {6, 3, 7}, {4, 6, 3} },
            { {1, 2, 3}, {3, 3, 3} },
            { {2, 2, 2}, {2, 2, 2} },
            { {5}, {4} },
            { {5}, {6} }
        };
        for (int[][] t : tests) {
            int s = findStart(t[0], t[1]);
            System.out.println("  petrol " + Arrays.toString(t[0]) + " dist " + Arrays.toString(t[1]) + " -> "
                    + (s == -1 ? "no tour possible" : "start at pump " + s));
        }

        Random rnd = new Random(9);
        boolean ok = true;
        for (int t = 0; t < 2000 && ok; t++) {
            int n = 1 + rnd.nextInt(8);
            int[] p = new int[n], d = new int[n];
            for (int i = 0; i < n; i++) { p[i] = rnd.nextInt(10); d[i] = rnd.nextInt(10); }
            int s = findStart(p, d);
            ok = (s == -1) ? !anyStartExists(p, d) : isValidStart(p, d, s);
        }
        System.out.println("  Randomised test (2000 cases) vs brute force: " + (ok ? "PASS" : "FAIL"));
    }
}
