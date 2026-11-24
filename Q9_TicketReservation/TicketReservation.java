import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Q9 - Circular Linked List: Online Ticket Reservation System
 * Ticket(ticketId, customer, movie, seat, bookingTime). Tail pointer only (head = tail.next).
 * A running 'count' makes "total booked tickets" O(1), and every traversal is bounded by it.
 */
public class TicketReservation {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");

    static class Ticket {
        int id; String customer, movie, seat; LocalDateTime time;
        Ticket next;
        Ticket(int id, String customer, String movie, String seat, LocalDateTime time) {
            this.id = id; this.customer = customer; this.movie = movie; this.seat = seat; this.time = time;
        }
        @Override public String toString() {
            return String.format("Ticket %-3d | %-8s | %-14s | Seat %-4s | %s", id, customer, movie, seat, time.format(FMT));
        }
    }

    private Ticket tail;
    private int count;

    // ---------- 1. add at end
    public boolean book(int id, String customer, String movie, String seat, LocalDateTime time) {
        if (tail != null) {
            Ticket t = tail.next;
            for (int i = 0; i < count; i++, t = t.next) {
                if (t.id == id) { System.out.println("  [!] Ticket ID " + id + " already exists."); return false; }
                if (t.movie.equalsIgnoreCase(movie) && t.seat.equalsIgnoreCase(seat)) {
                    System.out.println("  [!] Seat " + seat + " for \"" + movie + "\" is already booked."); return false;
                }
            }
        }
        Ticket n = new Ticket(id, customer, movie, seat, time);
        if (tail == null) { n.next = n; tail = n; }
        else { n.next = tail.next; tail.next = n; tail = n; }
        count++;
        return true;
    }

    // ---------- 2. remove by id
    public boolean cancel(int id) {
        if (tail == null) { System.out.println("  [!] No tickets booked."); return false; }
        Ticket prev = tail, cur = tail.next;
        for (int i = 0; i < count; i++) {
            if (cur.id == id) {
                if (count == 1) tail = null;
                else { prev.next = cur.next; if (cur == tail) tail = prev; }
                cur.next = null;
                count--;
                return true;
            }
            prev = cur; cur = cur.next;
        }
        System.out.println("  [!] Ticket " + id + " not found.");
        return false;
    }

    // ---------- 3. display
    public void display() {
        if (tail == null) { System.out.println("  (no tickets)"); return; }
        Ticket t = tail.next;
        for (int i = 0; i < count; i++, t = t.next) System.out.println("  " + t);
        System.out.println("  (last ticket " + tail.id + " links back to first ticket " + tail.next.id + ")");
    }

    // ---------- 4. search
    public List<Ticket> searchByCustomer(String name) {
        List<Ticket> res = new ArrayList<>();
        if (tail == null) return res;
        Ticket t = tail.next;
        for (int i = 0; i < count; i++, t = t.next) if (t.customer.equalsIgnoreCase(name)) res.add(t);
        return res;
    }

    public List<Ticket> searchByMovie(String movie) {
        List<Ticket> res = new ArrayList<>();
        if (tail == null) return res;
        Ticket t = tail.next;
        for (int i = 0; i < count; i++, t = t.next) if (t.movie.equalsIgnoreCase(movie)) res.add(t);
        return res;
    }

    // ---------- 5. total
    public int totalBooked() { return count; }

    private static void print(List<Ticket> l) {
        if (l.isEmpty()) System.out.println("  No matching tickets.");
        for (Ticket t : l) System.out.println("  " + t);
    }

    public static void main(String[] args) {
        TicketReservation tr = new TicketReservation();
        tr.book(1, "Aarav", "Inception", "A1", LocalDateTime.of(2026, 11, 24, 10, 15));
        tr.book(2, "Diya", "Interstellar", "B4", LocalDateTime.of(2026, 11, 24, 10, 20));
        tr.book(3, "Aarav", "Dangal", "C2", LocalDateTime.of(2026, 11, 24, 11, 5));
        tr.book(4, "Rohan", "Inception", "A2", LocalDateTime.of(2026, 11, 24, 11, 30));
        tr.book(2, "Dup", "Dangal", "D1", LocalDateTime.of(2026, 11, 24, 12, 0));          // duplicate ID
        tr.book(5, "Meera", "Inception", "A1", LocalDateTime.of(2026, 11, 24, 12, 5));     // seat clash
        System.out.println("== Booked tickets ==");
        tr.display();
        System.out.println("Total booked: " + tr.totalBooked());

        System.out.println("\n== Search by customer 'aarav' ==");
        print(tr.searchByCustomer("aarav"));
        System.out.println("== Search by movie 'Inception' ==");
        print(tr.searchByMovie("Inception"));
        System.out.println("== Search by movie 'Avatar' ==");
        print(tr.searchByMovie("Avatar"));

        System.out.println("\n== Cancel first (1), last (4) and a missing ID (99) ==");
        tr.cancel(1); tr.cancel(4); tr.cancel(99);
        tr.display();
        System.out.println("Total booked: " + tr.totalBooked());

        System.out.println("\n== Cancel the rest, then cancel on empty ==");
        tr.cancel(2); tr.cancel(3);
        tr.display();
        tr.cancel(2);
        System.out.println("Total booked: " + tr.totalBooked());
    }
}
