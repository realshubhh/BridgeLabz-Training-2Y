import java.util.*;

/**
 * Problem 1 - Recursive Directory Size Calculator
 * -----------------------------------------------
 * The directory tree is modelled with a small Node class so the program runs anywhere
 * (no real file system needed). Contains:
 *   (a) recursive solution              dirSize()
 *   (b) recursive solution with trace   dirSizeTraced()
 *   (d) iterative solution (stack)      dirSizeIterative()
 *   (e) cycle-safe versions (symlinks)  dirSizeSafe(), dirSizeFollowLinksSafely()
 *
 * Compile : javac DirectorySize.java
 * Run     : java DirectorySize
 */
public class DirectorySize {

    enum Kind { FILE, DIR, SYMLINK }

    static class Node {
        final String name;
        final int size;                       // KB, only meaningful for files
        final Kind kind;
        final List<Node> children = new ArrayList<>();
        Node target;                          // only for symlinks

        private Node(String name, int size, Kind kind) {
            this.name = name;
            this.size = size;
            this.kind = kind;
        }

        static Node file(String name, int sizeKb) { return new Node(name, sizeKb, Kind.FILE); }
        static Node dir(String name)              { return new Node(name, 0, Kind.DIR); }
        static Node symlink(String name, Node to) {
            Node n = new Node(name, 0, Kind.SYMLINK);
            n.target = to;
            return n;
        }

        Node add(Node... nodes) {
            children.addAll(Arrays.asList(nodes));
            return this;
        }
    }

    // ------------------------------------------------------------ tree builder
    static Node buildProjectTree() {
        return Node.dir("project").add(
            Node.dir("src").add(Node.file("main.java", 100), Node.file("utils.java", 50)),
            Node.dir("docs").add(
                Node.file("readme.txt", 10),
                Node.dir("guides").add(Node.file("setup.pdf", 200))),
            Node.file("config.xml", 20));
    }

    // ------------------------------------------------------------ (a) recursive
    /** Base case: a file returns its own size.
     *  Recursive case: a directory returns the sum of its children's sizes. */
    static int dirSize(Node node) {
        if (node.kind == Kind.FILE) {                   // BASE CASE
            return node.size;
        }
        int total = 0;
        for (Node child : node.children) {              // RECURSIVE CASE
            total += dirSize(child);
        }
        return total;
    }

    // ------------------------------------------------- (b) recursive with tracing
    static int dirSizeTraced(Node node, List<String> stack, int depth) {
        stack.add(node.name);                           // push frame
        String pad = "  ".repeat(depth);
        System.out.println(pad + "CALL   dirSize(" + node.name + ")   stack = ["
                + String.join(" > ", stack) + "]");

        int result;
        if (node.kind == Kind.FILE) {
            result = node.size;
        } else {
            result = 0;
            for (Node child : node.children) {
                result += dirSizeTraced(child, stack, depth + 1);
            }
        }

        System.out.println(pad + "RETURN dirSize(" + node.name + ") = " + result + " KB");
        stack.remove(stack.size() - 1);                 // pop frame
        return result;
    }

    // ------------------------------------------------------ (d) iterative version
    static int dirSizeIterative(Node root) {
        Deque<Node> stack = new ArrayDeque<>();
        stack.push(root);
        int total = 0;
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            if (node.kind == Kind.FILE) {
                total += node.size;
            } else {
                for (Node child : node.children) {
                    stack.push(child);
                }
            }
        }
        return total;
    }

    // --------------------------------------------- (e) cycle-safe (symlink aware)
    /**
     * Extra base cases:
     *  - symlink                                  -> not followed (like `du`) -> 0
     *  - directory already on the current path    -> cycle -> 0
     * `visiting` holds the directories on the CURRENT recursion path
     * (added before recursing, removed after = a tiny backtracking step).
     */
    static int dirSizeSafe(Node node, Set<Node> visiting) {
        if (node.kind == Kind.SYMLINK) return 0;        // BASE CASE 1
        if (node.kind == Kind.FILE)    return node.size; // BASE CASE 2
        if (visiting.contains(node))   return 0;        // BASE CASE 3 (cycle guard)

        visiting.add(node);
        int total = 0;
        for (Node child : node.children) {
            total += dirSizeSafe(child, visiting);
        }
        visiting.remove(node);
        return total;
    }

    /** Alternative: FOLLOW symlinks but count every real node only once. */
    static int dirSizeFollowLinksSafely(Node node, Set<Node> seen) {
        if (node.kind == Kind.SYMLINK) node = node.target;
        if (!seen.add(node)) return 0;                  // already counted
        if (node.kind == Kind.FILE) return node.size;
        int total = 0;
        for (Node child : node.children) {
            total += dirSizeFollowLinksSafely(child, seen);
        }
        return total;
    }

    /** Naive version that blindly follows symlinks -> infinite recursion on a cycle. */
    static int naiveFollowLinks(Node node) {
        if (node.kind == Kind.SYMLINK) return naiveFollowLinks(node.target);
        if (node.kind == Kind.FILE)    return node.size;
        int total = 0;
        for (Node child : node.children) {
            total += naiveFollowLinks(child);
        }
        return total;
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    // -------------------------------------------------------------------- main
    public static void main(String[] args) {
        Node root = buildProjectTree();

        System.out.println("=== (b) Traced execution ===");
        int total = dirSizeTraced(root, new ArrayList<>(), 0);
        System.out.println("\nTotal size = " + total + " KB\n");

        check(dirSize(root) == 380, "recursive should be 380");
        check(dirSizeIterative(root) == 380, "iterative should be 380");
        check(total == 380, "traced should be 380");
        System.out.println("Recursive == Iterative == 380 KB  [OK]");

        System.out.println("\n=== (e) Cycle test: docs/guides/loop_link -> project ===");
        Node guides = root.children.get(1).children.get(1);
        guides.add(Node.symlink("loop_link", root));

        try {
            naiveFollowLinks(root);
            System.out.println("Naive version finished?! (unexpected)");
        } catch (StackOverflowError e) {
            System.out.println("Naive (follows links)        -> StackOverflowError (infinite cycle)  [expected]");
        }

        int safeSkip   = dirSizeSafe(root, new HashSet<>());
        int safeFollow = dirSizeFollowLinksSafely(root, new HashSet<>());
        System.out.println("Cycle-safe (skip symlinks)   -> " + safeSkip + " KB");
        System.out.println("Cycle-safe (follow, once)    -> " + safeFollow + " KB");
        check(safeSkip == 380 && safeFollow == 380, "safe versions should be 380");
        System.out.println("\nAll tests passed [OK]");
    }
}
