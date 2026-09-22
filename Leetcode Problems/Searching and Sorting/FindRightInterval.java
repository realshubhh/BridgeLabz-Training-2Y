import java.util.*;

public class FindRightInterval {
    public static int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);

        int[][] starts = new int[n][2];

        for (int i = 0; i < n; i++) {
            starts[i][0] = intervals[i][0];
            starts[i][1] = i;
        }

        Arrays.sort(starts, Comparator.comparingInt(a -> a[0]));

        for (int i = 0; i < n; i++) {
            int target = intervals[i][1];
            int left = 0, right = n - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;

                if (starts[mid][0] >= target) {
                    result[i] = starts[mid][1];
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[][] intervals = {{3, 4}, {2, 3}, {1, 2}};

        System.out.println(Arrays.toString(findRightInterval(intervals)));
    }
}
