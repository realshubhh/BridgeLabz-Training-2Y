/**
 * Additional edge-case tests for the Library Management System.
 * Not required by the assignment, but useful to demonstrate that
 * each method behaves correctly on boundary inputs.
 *
 * Compile: javac EdgeCaseTests.java
 * Run:     java EdgeCaseTests
 */
public class EdgeCaseTests {

    public static void main(String[] args) {
        testNoDuplicates();
        testAllDuplicates();
        testTitleSearchNoMatch();
        testPriceSearchNotFound();
        testTargetCostUnreachable();
        testSingleBookMeetsTarget();
        System.out.println("\nAll edge-case tests completed.");
    }

    private static void testNoDuplicates() {
        System.out.println("--- Test: No duplicates present ---");
        Book[] books = {
            new Book(1, "Book A", "Author A", 100.0),
            new Book(2, "Book B", "Author B", 200.0),
            new Book(3, "Book C", "Author C", 300.0)
        };
        int result = LibraryManagementSystem.removeDuplicates(books, books.length);
        System.out.println("Expected: 3, Got: " + result);
    }

    private static void testAllDuplicates() {
        System.out.println("\n--- Test: All entries are duplicates ---");
        Book[] books = {
            new Book(1, "Book A", "Author A", 100.0),
            new Book(1, "Book A", "Author A", 100.0),
            new Book(1, "Book A", "Author A", 100.0)
        };
        int result = LibraryManagementSystem.removeDuplicates(books, books.length);
        System.out.println("Expected: 1, Got: " + result);
    }

    private static void testTitleSearchNoMatch() {
        System.out.println("\n--- Test: Title search with no matches ---");
        Book[] books = {
            new Book(1, "Java Basics", "James", 300.0),
            new Book(2, "Python Guide", "Guido", 600.0)
        };
        LibraryManagementSystem.searchByTitle(books, books.length, "ruby");
    }

    private static void testPriceSearchNotFound() {
        System.out.println("\n--- Test: Price search for a value that doesn't exist ---");
        Book[] books = {
            new Book(1, "Book A", "Author A", 100.0),
            new Book(2, "Book B", "Author B", 200.0),
            new Book(3, "Book C", "Author C", 300.0)
        };
        LibraryManagementSystem.sortByPrice(books, books.length);
        int result = LibraryManagementSystem.searchByPrice(books, books.length, 999.0);
        System.out.println("Expected: -1, Got: " + result);
    }

    private static void testTargetCostUnreachable() {
        System.out.println("\n--- Test: Target cost too high to reach ---");
        Book[] books = {
            new Book(1, "Book A", "Author A", 100.0),
            new Book(2, "Book B", "Author B", 200.0)
        };
        int result = LibraryManagementSystem.minBooksForTargetCost(books, books.length, 10000.0);
        System.out.println("Expected: 0, Got: " + result);
    }

    private static void testSingleBookMeetsTarget() {
        System.out.println("\n--- Test: A single book already meets the target ---");
        Book[] books = {
            new Book(1, "Book A", "Author A", 500.0),
            new Book(2, "Book B", "Author B", 100.0)
        };
        int result = LibraryManagementSystem.minBooksForTargetCost(books, books.length, 400.0);
        System.out.println("Expected: 1, Got: " + result);
    }
}
