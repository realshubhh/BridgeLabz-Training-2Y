import java.util.ArrayDeque;
import java.util.Deque;

public class SimplifyPath {

    public String simplifyPath(String path) {
        Deque<String> stack = new ArrayDeque<>();
        String[] parts = path.split("/");

        for (String part : parts) {
            if (part.isEmpty() || part.equals(".")) {
                continue;
            } else if (part.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pollLast();
                }
            } else {
                stack.addLast(part);
            }
        }

        StringBuilder result = new StringBuilder();
        for (String directory : stack) {
            result.append("/").append(directory);
        }

        return result.length() == 0 ? "/" : result.toString();
    }

    public static void main(String[] args) {
        String path = "/a/./b/../../c/";

        SimplifyPath solution = new SimplifyPath();
        String result = solution.simplifyPath(path);

        System.out.println("Simplified path: " + result);
    }
}
