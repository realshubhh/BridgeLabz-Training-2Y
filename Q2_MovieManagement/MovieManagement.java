import java.util.*;

/**
 * Q2 - Doubly Linked List: Movie Management System
 * Node = Movie(title, director, year, rating) with next/prev pointers. Head AND tail are maintained.
 */
public class MovieManagement {

    static class Movie {
        String title, director; int year; double rating;
        Movie next, prev;
        Movie(String title, String director, int year, double rating) {
            this.title = title; this.director = director; this.year = year; this.rating = rating;
        }
        @Override public String toString() {
            return String.format("%-22s | %-20s | %d | %.1f", title, director, year, rating);
        }
    }

    private Movie head, tail;
    private int size;

    // ---------- helpers
    private Movie findByTitle(String title) {
        for (Movie m = head; m != null; m = m.next) if (m.title.equalsIgnoreCase(title)) return m;
        return null;
    }

    private boolean validRating(double r) {
        if (r < 0 || r > 10) { System.out.println("  [!] Rating must be between 0 and 10."); return false; }
        return true;
    }

    private boolean canAdd(String title, double rating) {
        if (findByTitle(title) != null) { System.out.println("  [!] Movie \"" + title + "\" already exists."); return false; }
        return validRating(rating);
    }

    // ---------- 1. add
    public boolean addAtBeginning(String t, String d, int y, double r) {
        if (!canAdd(t, r)) return false;
        Movie m = new Movie(t, d, y, r);
        if (head == null) head = tail = m;
        else { m.next = head; head.prev = m; head = m; }
        size++;
        return true;
    }

    public boolean addAtEnd(String t, String d, int y, double r) {
        if (!canAdd(t, r)) return false;
        Movie m = new Movie(t, d, y, r);
        if (tail == null) head = tail = m;
        else { tail.next = m; m.prev = tail; tail = m; }
        size++;
        return true;
    }

    public boolean addAtPosition(int pos, String t, String d, int y, double r) {
        if (pos < 1 || pos > size + 1) { System.out.println("  [!] Invalid position " + pos); return false; }
        if (pos == 1) return addAtBeginning(t, d, y, r);
        if (pos == size + 1) return addAtEnd(t, d, y, r);
        if (!canAdd(t, r)) return false;
        Movie cur = head;
        for (int i = 1; i < pos; i++) cur = cur.next;         // cur = node currently at 'pos'
        Movie m = new Movie(t, d, y, r);
        m.prev = cur.prev; m.next = cur;
        cur.prev.next = m; cur.prev = m;
        size++;
        return true;
    }

    // ---------- 2. remove by title
    public boolean removeByTitle(String title) {
        if (head == null) { System.out.println("  [!] List is empty."); return false; }
        Movie m = findByTitle(title);
        if (m == null) { System.out.println("  [!] \"" + title + "\" not found."); return false; }
        if (m.prev != null) m.prev.next = m.next; else head = m.next;   // fix head
        if (m.next != null) m.next.prev = m.prev; else tail = m.prev;   // fix tail
        m.next = m.prev = null;
        size--;
        return true;
    }

    // ---------- 3. search
    public List<Movie> searchByDirector(String director) {
        List<Movie> res = new ArrayList<>();
        for (Movie m = head; m != null; m = m.next) if (m.director.equalsIgnoreCase(director)) res.add(m);
        return res;
    }

    public List<Movie> searchByRating(double rating) {
        List<Movie> res = new ArrayList<>();
        for (Movie m = head; m != null; m = m.next) if (Math.abs(m.rating - rating) < 1e-9) res.add(m);
        return res;
    }

    // ---------- 4. display
    public void displayForward() {
        System.out.println("  --- Forward (head -> tail) ---");
        if (head == null) { System.out.println("  (empty)"); return; }
        for (Movie m = head; m != null; m = m.next) System.out.println("  " + m);
    }

    public void displayReverse() {
        System.out.println("  --- Reverse (tail -> head) ---");
        if (tail == null) { System.out.println("  (empty)"); return; }
        for (Movie m = tail; m != null; m = m.prev) System.out.println("  " + m);
    }

    // ---------- 5. update rating
    public boolean updateRating(String title, double newRating) {
        if (!validRating(newRating)) return false;
        Movie m = findByTitle(title);
        if (m == null) { System.out.println("  [!] \"" + title + "\" not found."); return false; }
        m.rating = newRating;
        return true;
    }

    private static void printList(List<Movie> l) {
        if (l.isEmpty()) System.out.println("  No matching movies.");
        for (Movie m : l) System.out.println("  " + m);
    }

    public static void main(String[] args) {
        MovieManagement mm = new MovieManagement();

        System.out.println("== Add movies ==");
        mm.addAtEnd("Inception", "Christopher Nolan", 2010, 8.8);
        mm.addAtEnd("Interstellar", "Christopher Nolan", 2014, 8.7);
        mm.addAtBeginning("Dangal", "Nitesh Tiwari", 2016, 8.3);
        mm.addAtPosition(2, "3 Idiots", "Rajkumar Hirani", 2009, 8.4);   // goes to position 2
        mm.addAtPosition(5, "Dunkirk", "Christopher Nolan", 2017, 7.8);  // end
        mm.addAtEnd("inception", "Dup", 2000, 5);                        // duplicate title (case-insensitive)
        mm.addAtEnd("Bad", "X", 2000, 11);                               // invalid rating
        mm.displayForward();
        mm.displayReverse();

        System.out.println("\n== Search by director: Christopher Nolan ==");
        printList(mm.searchByDirector("Christopher Nolan"));
        System.out.println("== Search by rating: 8.4 ==");
        printList(mm.searchByRating(8.4));

        System.out.println("\n== Update rating of Dunkirk -> 8.4 ==");
        mm.updateRating("Dunkirk", 8.4);
        printList(mm.searchByRating(8.4));

        System.out.println("\n== Remove head (Dangal), tail (Dunkirk), middle (Inception), missing ==");
        mm.removeByTitle("Dangal");
        mm.removeByTitle("Dunkirk");
        mm.removeByTitle("Inception");
        mm.removeByTitle("Avatar");
        mm.displayForward();
        mm.displayReverse();
    }
}
