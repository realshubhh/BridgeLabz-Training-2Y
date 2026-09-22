import java.util.Locale;

/**
 * DSA Practical Assignment: Smart Library Management System
 * -----------------------------------------------------------
 * Implements 5 core operations on an array of Book records:
 *   1. removeDuplicates      - in-place duplicate removal (two-pointer)
 *   2. searchByTitle         - case-insensitive partial title search
 *   3. sortByPrice           - selection sort (ascending), counts swaps
 *   4. searchByPrice         - binary search, O(log N)
 *   5. minBooksForTargetCost - sliding window, O(N)
 *
 * See Book.java for the data model.
 *
 * Compile:  javac Book.java LibraryManagementSystem.java
 * Run:      java LibraryManagementSystem
 */
public class LibraryManagementSystem {

    // ---------------------------------------------------------------
    // Task 1: Remove Duplicate Books In-Place
    // ---------------------------------------------------------------
    /**
     * Books are sorted by bookId. Removes consecutive duplicate bookIds
     * in-place using two pointers, without allocating a new array.
     *
     * @param books array of books (sorted by bookId)
     * @param n     number of valid entries in the array
     * @return the new count M of unique books (first M slots hold them)
     */
    public static int removeDuplicates(Book[] books, int n) {
        if (n == 0) return 0;

        int i = 0; // index of last unique element
        for (int j = 1; j < n; j++) {
            if (books[j].bookId != books[i].bookId) {
                i++;
                books[i] = books[j];
            }
        }
        return i + 1; // M = new unique count
    }

    // ---------------------------------------------------------------
    // Task 2: Partial Title Search (case-insensitive)
    // ---------------------------------------------------------------
    /**
     * Prints every book whose title contains the query substring,
     * ignoring case.
     */
    public static void searchByTitle(Book[] books, int count, String query) {
        String q = query.toLowerCase(Locale.ROOT);
        System.out.println("Search Results for '" + query + "':");

        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (books[i].title.toLowerCase(Locale.ROOT).contains(q)) {
                System.out.println("- Found: " + books[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("- No books found matching \"" + query + "\"");
        }
    }

    // ---------------------------------------------------------------
    // Task 3: Sort by Price (Selection Sort)
    // ---------------------------------------------------------------
    /**
     * Sorts books[0..count-1] in ascending order of price using
     * selection sort. Returns nothing but prints the total number of
     * swaps performed (a swap that leaves an element in place, i.e.
     * minIndex == i, is not counted).
     */
    public static void sortByPrice(Book[] books, int count) {
        int swaps = 0;

        for (int i = 0; i < count - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < count; j++) {
                if (books[j].price < books[minIndex].price) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Book temp = books[i];
                books[i] = books[minIndex];
                books[minIndex] = temp;
                swaps++;
            }
        }

        System.out.println("Books Sorted by Price:");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + ". " + books[i]);
        }
        System.out.println("Total Swaps: " + swaps);
    }

    // ---------------------------------------------------------------
    // Task 4: Search by Price (Binary Search, O(log N))
    // ---------------------------------------------------------------
    /**
     * Requires books[0..count-1] to already be sorted by price
     * (Task 3 must run first). Returns the index of the book with an
     * exact matching price, or -1 if not found.
     */
    public static int searchByPrice(Book[] books, int count, double targetPrice) {
        int low = 0, high = count - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            double midPrice = books[mid].price;

            if (Double.compare(midPrice, targetPrice) == 0) {
                return mid;
            } else if (midPrice < targetPrice) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    // ---------------------------------------------------------------
    // Task 5: Minimum Consecutive Books for Target Cost (Sliding Window)
    // ---------------------------------------------------------------
    /**
     * Finds the length of the smallest contiguous run of books
     * (in the array's current order) whose combined price is >=
     * targetCost. Runs in O(N) time, O(1) extra space.
     *
     * @return minimum window length, or 0 if no such window exists
     */
    public static int minBooksForTargetCost(Book[] books, int count, double targetCost) {
        int left = 0;
        double currentSum = 0.0;
        int minLength = Integer.MAX_VALUE;

        for (int right = 0; right < count; right++) {
            currentSum += books[right].price;

            while (currentSum >= targetCost) {
                int windowLength = right - left + 1;
                if (windowLength < minLength) {
                    minLength = windowLength;
                }
                currentSum -= books[left].price;
                left++;
            }
        }

        return (minLength == Integer.MAX_VALUE) ? 0 : minLength;
    }

    // ---------------------------------------------------------------
    // Demo / Test Harness
    // ---------------------------------------------------------------
    public static void main(String[] args) {

        // Initial input: 6 books, 1 duplicate (bookId 101 repeated)
        Book[] books = new Book[] {
            new Book(101, "Data Structures",   "Mark",   400.0),
            new Book(101, "Data Structures",   "Mark",   400.0), // duplicate
            new Book(102, "Java Basics",       "James",  300.0),
            new Book(103, "Python Guide",      "Guido",  600.0),
            new Book(104, "Database Systems",  "Raghu",  500.0),
            new Book(105, "Computer Networks", "Andrew", 700.0)
        };
        int n = books.length;

        System.out.println("=================================================");
        System.out.println(" SMART LIBRARY MANAGEMENT SYSTEM - DEMO");
        System.out.println("=================================================\n");

        // ---------------- Task 1 ----------------
        int uniqueCount = removeDuplicates(books, n);
        System.out.println("1. After Task 1 (Remove Duplicates):");
        System.out.println("Unique Books Count: " + uniqueCount);
        System.out.println("Book List:");
        for (int i = 0; i < uniqueCount; i++) {
            System.out.println(books[i]);
        }
        System.out.println();

        // ---------------- Task 2 ----------------
        System.out.println("2. After Task 2 (Search Query: \"data\"):");
        searchByTitle(books, uniqueCount, "data");
        System.out.println();

        // ---------------- Task 3 ----------------
        System.out.println("3. After Task 3 (Sort by Price):");
        sortByPrice(books, uniqueCount);
        System.out.println();

        // ---------------- Task 4 ----------------
        double targetPrice = 500.0;
        System.out.println("4. After Task 4 (Search for Price: " + targetPrice + "):");
        System.out.println("Searching for Price Rs. " + targetPrice + "...");
        int idx = searchByPrice(books, uniqueCount, targetPrice);
        if (idx != -1) {
            System.out.println("Result: Book found at index " + idx + ": " + books[idx]);
        } else {
            System.out.println("Result: No book found with price Rs. " + targetPrice);
        }
        System.out.println();

        // ---------------- Task 5 ----------------
        double targetCost = 1000.0;
        System.out.println("5. After Task 5 (Sliding Window for Target Cost S = Rs. " + targetCost + "):");
        System.out.println("Finding minimum consecutive books whose total price >= Rs. " + targetCost + "...");
        int minBooks = minBooksForTargetCost(books, uniqueCount, targetCost);
        if (minBooks > 0) {
            System.out.println("Minimum Consecutive Books Needed: " + minBooks);
        } else {
            System.out.println("No combination of consecutive books reaches the target cost.");
        }

        System.out.println("\n=================================================");
        System.out.println(" END OF DEMO");
        System.out.println("=================================================");
    }
}
