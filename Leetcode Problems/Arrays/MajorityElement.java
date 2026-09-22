public class MajorityElement {

    public int majorityElement(int[] nums) {
        int candidate = nums[0];
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }

            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    public static void main(String[] args) {
        int[] nums = {2, 2, 1, 1, 1, 2, 2};

        MajorityElement solution = new MajorityElement();
        int result = solution.majorityElement(nums);

        System.out.println("Majority element: " + result);
    }
}
