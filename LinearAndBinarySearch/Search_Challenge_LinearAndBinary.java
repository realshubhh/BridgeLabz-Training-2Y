import java.util.Arrays;
import java.util.Scanner;

public class Search_Challenge_LinearAndBinary {

    public static int findFirstMissingPositive(int[] arr) {
        int n = arr.length;
        boolean[] visited = new boolean[n + 1];

        for (int num : arr) {
            if (num > 0 && num <= n) {
                visited[num] = true;
            }
        }

        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                return i;
            }
        }

        return n + 1;
    }

    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of elements: ");
        int n = sc.nextInt();
        int[] arr = new int[n];

        System.out.println("Enter the elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        int firstMissingPositive = findFirstMissingPositive(arr);
        System.out.println("First missing positive integer: " + firstMissingPositive);

        int[] sortedArr = arr.clone();
        Arrays.sort(sortedArr);

        System.out.print("Enter the target number to search: ");
        int target = sc.nextInt();

        int index = binarySearch(sortedArr, target);
        if (index != -1) {
            System.out.println("Target found at index (in sorted array): " + index);
        } else {
            System.out.println("Target not found.");
        }
        sc.close();
    }
}
