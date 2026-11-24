import java.util.*;

/**
 * Problem 3 - N-Queens: basic, optimized, first-solution, forbidden squares
 * -------------------------------------------------------------------------
 *   BasicSolver      : 2-D board + O(N) isSafe()                      (from lecture)
 *   Solver           : 1-D array + 3 boolean arrays, O(1) safety check
 *                      - all solutions / first solution only / forbidden squares
 *   bitmaskCount()   : bit-manipulation version (bonus)
 *   traceOptimized() : step-by-step trace for N = 4
 *
 * Compile : javac NQueens.java
 * Run     : java NQueens
 */
public class NQueens {

    // =============================================================== basic version
    static class BasicSolver {
        final int n;
        final int[][] board;
        final List<int[][]> solutions = new ArrayList<>();
        long calls = 0;
        int count = 0;
        final boolean countOnly;

        BasicSolver(int n, boolean countOnly) {
            this.n = n;
            this.board = new int[n][n];
            this.countOnly = countOnly;
        }

        boolean isSafe(int row, int col) {
            for (int i = 0; i < row; i++) {                          // column
                if (board[i][col] == 1) return false;
            }
            for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {   // upper-left
                if (board[i][j] == 1) return false;
            }
            for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {    // upper-right
                if (board[i][j] == 1) return false;
            }
            return true;
        }

        void solve(int row) {
            calls++;
            if (row == n) {                                          // all queens placed
                count++;
                if (!countOnly) {
                    int[][] copy = new int[n][];
                    for (int r = 0; r < n; r++) copy[r] = board[r].clone();
                    solutions.add(copy);
                }
                return;
            }
            for (int col = 0; col < n; col++) {
                if (isSafe(row, col)) {
                    board[row][col] = 1;                             // make choice
                    solve(row + 1);                                  // recurse
                    board[row][col] = 0;                             // undo choice
                }
            }
        }
    }

    // ============================================================ optimized version
    /**
     * board[row]            = column of the queen in that row   (1-D array, O(N) memory)
     * cols[c]               = is column c used?
     * diag1[row + col]      = is the "/"  diagonal used?  (row + col is constant)
     * diag2[row - col+N-1]  = is the "\"  diagonal used?  (row - col is constant, shifted >= 0)
     */
    static class Solver {
        final int n;
        final int[] board;
        final boolean[] cols, diag1, diag2;
        final boolean[][] forbidden;              // null = no forbidden squares
        final boolean firstOnly;
        final List<int[]> solutions = new ArrayList<>();
        long calls = 0;

        Solver(int n, boolean firstOnly, boolean[][] forbidden) {
            this.n = n;
            this.firstOnly = firstOnly;
            this.forbidden = forbidden;
            this.board = new int[n];
            Arrays.fill(board, -1);
            this.cols = new boolean[n];
            this.diag1 = new boolean[2 * n - 1];
            this.diag2 = new boolean[2 * n - 1];
        }

        /** Returns true when the search should stop (first solution found). */
        boolean solve(int row) {
            calls++;
            if (row == n) {
                solutions.add(board.clone());
                return firstOnly;                                    // stop everything if first-only
            }
            for (int col = 0; col < n; col++) {
                if (forbidden != null && forbidden[row][col]) continue;     // NEW constraint
                int d1 = row + col, d2 = row - col + n - 1;
                if (cols[col] || diag1[d1] || diag2[d2]) continue;          // O(1) safety test

                board[row] = col;                                    // make choice
                cols[col] = diag1[d1] = diag2[d2] = true;

                if (solve(row + 1)) return true;                     // propagate "stop"

                cols[col] = diag1[d1] = diag2[d2] = false;           // undo choice
                board[row] = -1;
            }
            return false;
        }
    }

    static Solver optimized(int n)                                { Solver s = new Solver(n, false, null); s.solve(0); return s; }
    static Solver firstSolution(int n)                            { Solver s = new Solver(n, true,  null); s.solve(0); return s; }
    static Solver withForbidden(int n, boolean[][] f, boolean one){ Solver s = new Solver(n, one,   f);    s.solve(0); return s; }

    // ============================================================ bonus: bitmasks
    static long bitmaskCount(int n) {
        int full = (1 << n) - 1;
        return bitmaskSolve(full, 0, 0, 0);
    }

    private static long bitmaskSolve(int full, int cols, int d1, int d2) {
        if (cols == full) return 1;
        long total = 0;
        int free = full & ~(cols | d1 | d2);                         // all safe columns at once
        while (free != 0) {
            int bit = free & -free;                                  // lowest safe column
            free ^= bit;
            total += bitmaskSolve(full, cols | bit, ((d1 | bit) << 1) & full, (d2 | bit) >> 1);
        }
        return total;
    }

    // ================================================================== (c) trace
    static int traceStep = 0;

    static int[] traceOptimized(int n) {
        int[] board = new int[n];
        Arrays.fill(board, -1);
        boolean[] cols = new boolean[n], d1a = new boolean[2 * n - 1], d2a = new boolean[2 * n - 1];
        traceStep = 0;
        traceSolve(0, n, board, cols, d1a, d2a);
        return board;
    }

    private static boolean traceSolve(int row, int n, int[] board,
                                      boolean[] cols, boolean[] diag1, boolean[] diag2) {
        if (row == n) {
            System.out.println("    >>> SOLUTION FOUND: board = " + Arrays.toString(board));
            return true;
        }
        for (int col = 0; col < n; col++) {
            int d1 = row + col, d2 = row - col + n - 1;
            if (cols[col] || diag1[d1] || diag2[d2]) continue;

            board[row] = col;
            cols[col] = diag1[d1] = diag2[d2] = true;
            log("PLACE", row, col, board, cols, diag1, diag2);

            if (traceSolve(row + 1, n, board, cols, diag1, diag2)) return true;

            cols[col] = diag1[d1] = diag2[d2] = false;
            board[row] = -1;
            log("BACKTRACK", row, col, board, cols, diag1, diag2);
        }
        if (row > 0) System.out.println("    (row " + row + ": no safe column -> dead end)");
        return false;
    }

    private static void log(String action, int row, int col, int[] board,
                            boolean[] cols, boolean[] d1, boolean[] d2) {
        traceStep++;
        System.out.printf("%2d. %-9s row=%d col=%d  board=%s  cols=%s  d1=%s  d2=%s%n",
                traceStep, action, row, col, Arrays.toString(board), bits(cols), bits(d1), bits(d2));
    }

    private static String bits(boolean[] a) {
        StringBuilder sb = new StringBuilder();
        for (boolean b : a) sb.append(b ? 'T' : '.');
        return sb.toString();
    }

    // ------------------------------------------------------------------ helpers
    static void printBoard(int[] sol) {
        for (int r = 0; r < sol.length; r++) {
            StringBuilder sb = new StringBuilder();
            for (int c = 0; c < sol.length; c++) sb.append(sol[r] == c ? "Q " : ". ");
            System.out.println(sb.toString().trim());
        }
    }

    static long comb(int n, int k) {
        long result = 1;
        for (int i = 1; i <= k; i++) result = result * (n - k + i) / i;
        return result;
    }

    static long pow(long base, int exp) {
        long r = 1;
        for (int i = 0; i < exp; i++) r *= base;
        return r;
    }

    static long factorial(int n) {
        long r = 1;
        for (int i = 2; i <= n; i++) r *= i;
        return r;
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    // -------------------------------------------------------------------- main
    public static void main(String[] args) {
        // ------------------------------------------------------------ (a) numbers
        System.out.println("=== (a) 8-Queens search-space sizes ===");
        System.out.printf("  Place 8 queens on any of 64 squares  C(64,8) = %,d%n", comb(64, 8));
        System.out.printf("  One queen per row (any column)       8^8     = %,d%n", pow(8, 8));
        System.out.printf("  One per row AND per column           8!      = %,d%n", factorial(8));
        BasicSolver b8 = new BasicSolver(8, false);
        b8.solve(0);
        System.out.printf("  Backtracking (basic) actually visits %,d recursive calls and finds %d solutions%n",
                b8.calls, b8.count);

        // ------------------------------------------------------ correctness checks
        System.out.println("\n=== Solution counts (basic vs optimized vs bitmask) ===");
        int[] known = {0, 1, 0, 0, 2, 10, 4, 40, 92, 352, 724};    // index = N
        for (int k = 1; k <= 10; k++) {
            BasicSolver bs = new BasicSolver(k, true);
            bs.solve(0);
            int o = optimized(k).solutions.size();
            long m = bitmaskCount(k);
            check(bs.count == known[k] && o == known[k] && m == known[k],
                    "mismatch for N=" + k + ": " + bs.count + "/" + o + "/" + m);
            System.out.printf("  N=%-2d -> %-4d solutions   [OK]%n", k, known[k]);
        }

        // -------------------------------------------------- calls: basic vs optimized
        System.out.println("\n=== Recursive calls (all solutions), N=8 ===");
        System.out.println("  basic     : " + b8.calls);
        System.out.println("  optimized : " + optimized(8).calls + " (same tree, but O(1) check instead of O(N))");

        // ------------------------------------------------------------ (c) N=4 trace
        System.out.println("\n=== (c) Trace of optimized algorithm, N=4 ===");
        int[] sol = traceOptimized(4);
        printBoard(sol);

        // --------------------------------------------------------- (d) first only
        System.out.println("\n=== (d) First solution only ===");
        for (int k : new int[]{4, 8, 12, 20}) {
            Solver s = firstSolution(k);
            System.out.printf("  N=%-2d first solution %s  after %d calls%n",
                    k, Arrays.toString(s.solutions.get(0)), s.calls);
        }
        printBoard(firstSolution(8).solutions.get(0));
        System.out.println("  (all-solutions N=8 needs " + optimized(8).calls + " calls)");

        // ----------------------------------------------------------- (e) forbidden
        System.out.println("\n=== (e) N=6 with forbidden squares ===");
        boolean[][] forb = new boolean[6][6];
        int[][] forbList = {{0, 1}, {1, 3}, {2, 5}, {3, 0}};
        for (int[] f : forbList) forb[f[0]][f[1]] = true;

        Solver free = optimized(6);
        Solver restricted = withForbidden(6, forb, false);
        System.out.println("  without forbidden: " + free.solutions.size() + " solutions, " + free.calls + " calls");
        System.out.println("  with    forbidden: " + restricted.solutions.size() + " solutions, "
                + restricted.calls + " calls   forbidden=(0,1) (1,3) (2,5) (3,0)");
        for (int[] s : restricted.solutions) {
            for (int r = 0; r < 6; r++) check(!forb[r][s[r]], "queen on forbidden square");
        }
        Solver one = withForbidden(6, forb, true);
        System.out.println("  first solution: " + Arrays.toString(one.solutions.get(0)));
        printBoard(one.solutions.get(0));

        // impossible case: a whole row forbidden -> immediately no solution
        boolean[][] row0 = new boolean[4][4];
        Arrays.fill(row0[0], true);
        Solver imp = withForbidden(4, row0, false);
        System.out.println("  entire row 0 forbidden -> " + imp.solutions.size() + " solutions, "
                + imp.calls + " calls (instant prune)");

        check(firstSolution(8).calls == 114, "first solution for N=8 should take 114 calls");
        check(b8.calls == 2057, "N=8 should visit 2057 calls");
        System.out.println("\nAll tests passed [OK]");
    }
}
