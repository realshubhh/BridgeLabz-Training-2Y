import java.util.HashSet;
import java.util.Set;

public class CheckIfNAndItsDoubleExist {

    public boolean checkIfExist(int[] arr) {
        Set<Integer> seen = new HashSet<>();

        for (int num : arr) {
            if (seen.contains(num * 2) || (num % 2 == 0 && seen.contains(num / 2))) {
                return true;
            }
            seen.add(num);
        }

        return false;
    }

    public static void main(String[] args) {
        int[] arr = {10, 2, 5, 3};

        CheckIfNAndItsDoubleExist solution = new CheckIfNAndItsDoubleExist();
        boolean result = solution.checkIfExist(arr);

        System.out.println("Does N and its double exist? " + result);
    }
}
