import java.util.*;

/**
 * H1 - Find ALL subarrays with zero sum.
 * Let prefix[i] = a[0] + ... + a[i]. Subarray (s..e) has sum 0  <=>  prefix[e] == prefix[s-1].
 * The hash map stores, for every prefix-sum value, the list of indices where it occurred
 * (the empty prefix, sum 0 at index -1, is pre-loaded). Whenever the current prefix sum was
 * seen before at indices i1, i2, ..., each one gives a zero-sum subarray (i+1 .. current).
 * Time O(n + K) for K subarrays reported, space O(n). 'long' avoids overflow.
 */
public class ZeroSumSubarrays {

    static List<int[]> zeroSumSubarrays(int[] a) {
        List<int[]> result = new ArrayList<>();
        Map<Long, List<Integer>> seen = new HashMap<>();
        seen.computeIfAbsent(0L, k -> new ArrayList<>()).add(-1);          // empty prefix
        long sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i];
            List<Integer> earlier = seen.get(sum);
            if (earlier != null) for (int start : earlier) result.add(new int[]{start + 1, i});
            seen.computeIfAbsent(sum, k -> new ArrayList<>()).add(i);
        }
        return result;
    }

    static List<int[]> bruteForce(int[] a) {
        List<int[]> r = new ArrayList<>();
        for (int i = 0; i < a.length; i++) { long s = 0; for (int j = i; j < a.length; j++) { s += a[j]; if (s == 0) r.add(new int[]{i, j}); } }
        return r;
    }

    static String show(int[] a, List<int[]> subs) {
        if (subs.isEmpty()) return "  none";
        StringBuilder sb = new StringBuilder();
        for (int[] s : subs) sb.append("  [").append(s[0]).append("..").append(s[1]).append("] = ")
                .append(Arrays.toString(Arrays.copyOfRange(a, s[0], s[1] + 1))).append("\n");
        return sb.toString().stripTrailing();
    }

    static Set<String> asSet(List<int[]> l) { Set<String> s = new TreeSet<>(); for (int[] x : l) s.add(x[0] + "-" + x[1]); return s; }

    public static void main(String[] args) {
        int[][] tests = { {6, 3, -1, -3, 4, -2, 2, 4, 6, -1}, {4, 2, -3, 1, 6}, {0, 0, 0}, {1, 2, 3}, {} };
        for (int[] a : tests) {
            List<int[]> r = zeroSumSubarrays(a);
            System.out.println("array " + Arrays.toString(a) + " -> " + r.size() + " subarray(s)");
            System.out.println(show(a, r));
        }
        Random rnd = new Random(21);
        boolean ok = true;
        for (int t = 0; t < 1000 && ok; t++) {
            int[] a = new int[rnd.nextInt(25)];
            for (int i = 0; i < a.length; i++) a[i] = rnd.nextInt(7) - 3;
            ok = asSet(zeroSumSubarrays(a)).equals(asSet(bruteForce(a))) && zeroSumSubarrays(a).size() == bruteForce(a).size();
        }
        System.out.println("Randomised test (1000 arrays) vs brute force: " + (ok ? "PASS" : "FAIL"));
    }
}
