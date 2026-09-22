import java.util.*;

public class AdvantageShuffle {
    public static int[] advantageCount(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int[] result = new int[n];
        int[][] indexed = new int[n][2];

        for (int i = 0; i < n; i++) {
            indexed[i][0] = nums2[i];
            indexed[i][1] = i;
        }

        Arrays.sort(nums1);
        Arrays.sort(indexed, Comparator.comparingInt(a -> a[0]));

        int low = 0, high = n - 1;

        for (int i = n - 1; i >= 0; i--) {
            if (nums1[high] > indexed[i][0]) {
                result[indexed[i][1]] = nums1[high--];
            } else {
                result[indexed[i][1]] = nums1[low++];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 7, 11, 15};
        int[] nums2 = {1, 10, 4, 11};

        System.out.println(Arrays.toString(advantageCount(nums1, nums2)));
    }
}
