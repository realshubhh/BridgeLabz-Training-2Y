import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddresses {

    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(String s, int startIndex, List<String> currentSegments, List<String> result) {
        if (currentSegments.size() == 4) {
            if (startIndex == s.length()) {
                result.add(String.join(".", currentSegments));
            }
            return;
        }

        for (int length = 1; length <= 3 && startIndex + length <= s.length(); length++) {
            String segment = s.substring(startIndex, startIndex + length);

            if (isValidSegment(segment)) {
                currentSegments.add(segment);
                backtrack(s, startIndex + length, currentSegments, result);
                currentSegments.remove(currentSegments.size() - 1);
            }
        }
    }

    private boolean isValidSegment(String segment) {
        if (segment.length() > 1 && segment.charAt(0) == '0') {
            return false;
        }

        int value = Integer.parseInt(segment);
        return value >= 0 && value <= 255;
    }

    public static void main(String[] args) {
        String s = "25525511135";

        RestoreIPAddresses solution = new RestoreIPAddresses();
        List<String> result = solution.restoreIpAddresses(s);

        System.out.println("Restored IP addresses: " + result);
    }
}
