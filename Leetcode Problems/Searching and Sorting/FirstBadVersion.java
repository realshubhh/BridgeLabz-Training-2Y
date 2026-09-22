public class FirstBadVersion {
    private int firstBad;

    public FirstBadVersion(int firstBad) {
        this.firstBad = firstBad;
    }

    private boolean isBadVersion(int version) {
        return version >= firstBad;
    }

    public int firstBadVersion(int n) {
        int left = 1, right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (isBadVersion(mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    public static void main(String[] args) {
        FirstBadVersion checker = new FirstBadVersion(4);
        System.out.println("First bad version: "
                + checker.firstBadVersion(5));
    }
}
