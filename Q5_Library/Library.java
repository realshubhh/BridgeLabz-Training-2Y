import java.util.*;

/**
 * Q5 - Doubly Linked List: Library Management System
 * Book(title, author, genre, id, available). Head + tail + size are maintained.
 */
public class Library {

    static class Book {
        String title, author, genre; int id; boolean available;
        Book next, prev;
        Book(String title, String author, String genre, int id, boolean available) {
            this.title = title; this.author = author; this.genre = genre; this.id = id; this.available = available;
        }
        @Override public String toString() {
            return String.format("ID: %-4d | %-26s | %-18s | %-10s | %s", id, title, author, genre,
                    available ? "Available" : "Issued");
        }
    }

    private Book head, tail;
    private int size;

    private Book findById(int id) {
        for (Book b = head; b != null; b = b.next) if (b.id == id) return b;
        return null;
    }

    private boolean canAdd(int id) {
        if (findById(id) != null) { System.out.println("  [!] Book ID " + id + " already exists."); return false; }
        return true;
    }

    // ---------- 1. add
    public boolean addAtBeginning(String t, String a, String g, int id, boolean av) {
        if (!canAdd(id)) return false;
        Book b = new Book(t, a, g, id, av);
        if (head == null) head = tail = b;
        else { b.next = head; head.prev = b; head = b; }
        size++;
        return true;
    }

    public boolean addAtEnd(String t, String a, String g, int id, boolean av) {
        if (!canAdd(id)) return false;
        Book b = new Book(t, a, g, id, av);
        if (tail == null) head = tail = b;
        else { tail.next = b; b.prev = tail; tail = b; }
        size++;
        return true;
    }

    public boolean addAtPosition(int pos, String t, String a, String g, int id, boolean av) {
        if (pos < 1 || pos > size + 1) { System.out.println("  [!] Invalid position " + pos); return false; }
        if (pos == 1) return addAtBeginning(t, a, g, id, av);
        if (pos == size + 1) return addAtEnd(t, a, g, id, av);
        if (!canAdd(id)) return false;
        Book cur = head;
        for (int i = 1; i < pos; i++) cur = cur.next;          // node currently at 'pos'
        Book b = new Book(t, a, g, id, av);
        b.prev = cur.prev; b.next = cur;
        cur.prev.next = b; cur.prev = b;
        size++;
        return true;
    }

    // ---------- 2. remove
    public boolean removeById(int id) {
        if (head == null) { System.out.println("  [!] Library is empty."); return false; }
        Book b = findById(id);
        if (b == null) { System.out.println("  [!] Book ID " + id + " not found."); return false; }
        if (b.prev != null) b.prev.next = b.next; else head = b.next;
        if (b.next != null) b.next.prev = b.prev; else tail = b.prev;
        b.next = b.prev = null;
        size--;
        return true;
    }

    // ---------- 3. search (case-insensitive, partial match)
    public List<Book> searchByTitle(String text) {
        List<Book> res = new ArrayList<>();
        for (Book b = head; b != null; b = b.next) if (b.title.toLowerCase().contains(text.toLowerCase())) res.add(b);
        return res;
    }

    public List<Book> searchByAuthor(String text) {
        List<Book> res = new ArrayList<>();
        for (Book b = head; b != null; b = b.next) if (b.author.toLowerCase().contains(text.toLowerCase())) res.add(b);
        return res;
    }

    // ---------- 4. update availability
    public boolean updateAvailability(int id, boolean available) {
        Book b = findById(id);
        if (b == null) { System.out.println("  [!] Book ID " + id + " not found."); return false; }
        b.available = available;
        return true;
    }

    // ---------- 5. display
    public void displayForward() {
        System.out.println("  --- Forward ---");
        if (head == null) System.out.println("  (no books)");
        for (Book b = head; b != null; b = b.next) System.out.println("  " + b);
    }

    public void displayReverse() {
        System.out.println("  --- Reverse ---");
        if (tail == null) System.out.println("  (no books)");
        for (Book b = tail; b != null; b = b.prev) System.out.println("  " + b);
    }

    // ---------- 6. count
    public int count() { return size; }

    private static void print(List<Book> l) {
        if (l.isEmpty()) System.out.println("  No matching books.");
        for (Book b : l) System.out.println("  " + b);
    }

    public static void main(String[] args) {
        Library lib = new Library();
        lib.addAtEnd("Clean Code", "Robert C. Martin", "Tech", 1, true);
        lib.addAtEnd("The Alchemist", "Paulo Coelho", "Fiction", 2, true);
        lib.addAtBeginning("Wings of Fire", "A.P.J. Abdul Kalam", "Biography", 3, true);
        lib.addAtPosition(2, "Clean Architecture", "Robert C. Martin", "Tech", 4, false);
        lib.addAtPosition(5, "Eleven Minutes", "Paulo Coelho", "Fiction", 5, true);
        lib.addAtEnd("Dup", "X", "Y", 3, true);                      // duplicate ID
        System.out.println("== Forward / reverse ==");
        lib.displayForward();
        lib.displayReverse();
        System.out.println("Total books: " + lib.count());

        System.out.println("\n== Search title 'clean' ==");
        print(lib.searchByTitle("clean"));
        System.out.println("== Search author 'paulo' ==");
        print(lib.searchByAuthor("paulo"));
        System.out.println("== Search title 'zzz' ==");
        print(lib.searchByTitle("zzz"));

        System.out.println("\n== Update availability: ID 4 -> Available, ID 2 -> Issued ==");
        lib.updateAvailability(4, true);
        lib.updateAvailability(2, false);
        lib.updateAvailability(42, true);
        lib.displayForward();

        System.out.println("\n== Remove head (3), tail (5), middle (4), missing (42) ==");
        lib.removeById(3); lib.removeById(5); lib.removeById(4); lib.removeById(42);
        lib.displayForward();
        lib.displayReverse();
        System.out.println("Total books: " + lib.count());
        lib.removeById(1); lib.removeById(2);
        lib.displayForward();
        System.out.println("Total books: " + lib.count());
    }
}
