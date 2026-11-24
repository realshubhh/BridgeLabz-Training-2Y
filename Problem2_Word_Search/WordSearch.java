import java.util.*;

/**
 * Problem 2 - Word Puzzle (4x4 grid, 8-directional, each cell used once per word)
 * -------------------------------------------------------------------------------
 * Backtracking framework
 *   STATE       : (row, col), index i of the letter to match, visited[][], current path
 *   CHOICES     : the 8 neighbouring cells
 *   CONSTRAINTS : inside grid, not visited, letter == word[i]
 *   GOAL        : last letter matched (i == L-1)
 *
 * Compile : javac WordSearch.java
 * Run     : java WordSearch
 */
public class WordSearch {

    static final char[][] GRID = {
        "CATS".toCharArray(),
        "OREA".toCharArray(),
        "DEAM".toCharArray(),
        "ELLS".toCharArray()
    };

    static final int[][] DIRECTIONS = {
        {-1, -1}, {-1, 0}, {-1, 1},
        { 0, -1},          { 0, 1},
        { 1, -1}, { 1, 0}, { 1, 1}
    };

    // ------------------------------------------------ (a) existence (stop at first)
    /** Returns the path of the first occurrence found, or null if the word is absent. */
    static List<int[]> exist(char[][] grid, String word) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> path = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (dfs(grid, word, r, c, 0, visited, path)) {
                    return path;
                }
            }
        }
        return null;
    }

    private static boolean dfs(char[][] grid, String word, int r, int c, int i,
                               boolean[][] visited, List<int[]> path) {
        // constraints (prune)
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return false;
        if (visited[r][c] || grid[r][c] != word.charAt(i))             return false;

        visited[r][c] = true;                                 // MAKE CHOICE
        path.add(new int[]{r, c});

        if (i == word.length() - 1) return true;              // GOAL: last letter matched

        for (int[] d : DIRECTIONS) {                          // EXPLORE
            if (dfs(grid, word, r + d[0], c + d[1], i + 1, visited, path)) {
                return true;
            }
        }

        visited[r][c] = false;                                // UNDO (backtrack)
        path.remove(path.size() - 1);
        return false;
    }

    // ------------------------------------------- (c) same search with a full trace
    static boolean existTraced(char[][] grid, String word) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> path = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == word.charAt(0)) {
                    System.out.println("--- start candidate (" + r + "," + c + ") '" + grid[r][c] + "' ---");
                }
                if (dfsTraced(grid, word, r, c, 0, 0, visited, path)) return true;
            }
        }
        return false;
    }

    private static boolean dfsTraced(char[][] grid, String word, int r, int c, int i, int depth,
                                     boolean[][] visited, List<int[]> path) {
        String pad = "  ".repeat(depth);
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return false;
        if (visited[r][c])                  return false;
        if (grid[r][c] != word.charAt(i))   return false;       // silent prune (mismatch)

        visited[r][c] = true;
        path.add(new int[]{r, c});
        System.out.println(pad + "CHOOSE  (" + r + "," + c + ") = '" + grid[r][c]
                + "'  matches word[" + i + "]   path=" + pathToString(path));

        if (i == word.length() - 1) {
            System.out.println(pad + "GOAL reached, path = " + pathToString(path));
            return true;
        }
        for (int[] d : DIRECTIONS) {
            if (dfsTraced(grid, word, r + d[0], c + d[1], i + 1, depth + 1, visited, path)) {
                return true;
            }
        }
        visited[r][c] = false;
        path.remove(path.size() - 1);
        System.out.println(pad + "UNDO    (" + r + "," + c + ") = '" + grid[r][c]
                + "'  dead end -> backtrack   path=" + pathToString(path));
        return false;
    }

    // ------------------------------------------------ (e) ALL ways to form the word
    static List<List<int[]>> findAllPaths(char[][] grid, String word) {
        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<int[]> path = new ArrayList<>();
        List<List<int[]>> allPaths = new ArrayList<>();          // extra data structure

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                collect(grid, word, r, c, 0, visited, path, allPaths);
            }
        }
        return allPaths;
    }

    private static void collect(char[][] grid, String word, int r, int c, int i,
                                boolean[][] visited, List<int[]> path, List<List<int[]>> all) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
        if (visited[r][c] || grid[r][c] != word.charAt(i))             return;

        visited[r][c] = true;
        path.add(new int[]{r, c});
        if (i == word.length() - 1) {
            all.add(new ArrayList<>(path));                      // store a COPY, keep searching
        } else {
            for (int[] d : DIRECTIONS) {
                collect(grid, word, r + d[0], c + d[1], i + 1, visited, path, all);
            }
        }
        visited[r][c] = false;                                   // backtrack
        path.remove(path.size() - 1);
    }

    // ------------------------------------------------------------------ helpers
    static String pathToString(List<int[]> path) {
        StringBuilder sb = new StringBuilder("[");
        for (int k = 0; k < path.size(); k++) {
            if (k > 0) sb.append(", ");
            sb.append("(").append(path.get(k)[0]).append(",").append(path.get(k)[1]).append(")");
        }
        return sb.append("]").toString();
    }

    /** Grid with the path letters numbered 1..L. */
    static String showPath(char[][] grid, List<int[]> path) {
        String[][] out = new String[grid.length][grid[0].length];
        for (String[] row : out) Arrays.fill(row, ".");
        for (int k = 0; k < path.size(); k++) {
            int r = path.get(k)[0], c = path.get(k)[1];
            out[r][c] = "" + grid[r][c] + (k + 1);
        }
        StringBuilder sb = new StringBuilder();
        for (String[] row : out) {
            for (String cell : row) sb.append(String.format("%-3s ", cell));
            sb.append("\n");
        }
        return sb.toString();
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    // -------------------------------------------------------------------- main
    public static void main(String[] args) {
        String word = "DREAM";
        System.out.println("Grid:");
        for (char[] row : GRID) {
            System.out.println("   " + new String(row).replaceAll(".(?=.)", "$0 "));
        }

        System.out.println("\n=== (c) Trace ===");
        existTraced(GRID, word);

        List<int[]> p = exist(GRID, word);
        System.out.println("\nexist('" + word + "') -> " + (p != null) + ", path = " + (p == null ? "[]" : pathToString(p)));
        check(p != null, "DREAM must exist");

        System.out.println("\n=== (e) All paths ===");
        List<List<int[]>> paths = findAllPaths(GRID, word);
        System.out.println("Total ways to form '" + word + "': " + paths.size());
        for (int k = 0; k < paths.size(); k++) {
            System.out.println("\nWay " + (k + 1) + ": " + pathToString(paths.get(k)));
            System.out.print(showPath(GRID, paths.get(k)));
        }

        // tests
        check(exist(GRID, "XYZ") == null,   "XYZ must not exist");
        check(exist(GRID, "DREAD") == null, "DREAD must not exist");
        check(exist(GRID, "CAT") != null,   "CAT must exist");
        check(paths.size() == 3,            "DREAM must have exactly 3 paths");
        System.out.println("\nAll tests passed [OK]");
    }
}
