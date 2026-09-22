import java.util.ArrayList;
import java.util.List;

public class BinaryTreePaths {

    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();

        if (root != null) {
            findPaths(root, String.valueOf(root.val), paths);
        }

        return paths;
    }

    private void findPaths(TreeNode node, String currentPath, List<String> paths) {
        if (node.left == null && node.right == null) {
            paths.add(currentPath);
            return;
        }

        if (node.left != null) {
            findPaths(node.left, currentPath + "->" + node.left.val, paths);
        }

        if (node.right != null) {
            findPaths(node.right, currentPath + "->" + node.right.val, paths);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);

        BinaryTreePaths solution = new BinaryTreePaths();
        List<String> result = solution.binaryTreePaths(root);

        System.out.println("Root-to-leaf paths: " + result);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
    }
}
