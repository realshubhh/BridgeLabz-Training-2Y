import java.util.*;

/**
 * Q4 - Singly Linked List: Inventory Management System
 * Item(name, id, quantity, price). Sorting uses MERGE SORT on the linked list (O(n log n), stable, no extra array).
 */
public class Inventory {

    static class Item {
        String name; int id; int qty; double price;
        Item next;
        Item() { }
        Item(String name, int id, int qty, double price) {
            this.name = name; this.id = id; this.qty = qty; this.price = price;
        }
        @Override public String toString() {
            return String.format("ID: %-4d | %-12s | Qty: %-4d | Price: %9.2f | Value: %10.2f", id, name, qty, price, qty * price);
        }
    }

    private Item head, tail;
    private int size;

    private Item findById(int id) {
        for (Item i = head; i != null; i = i.next) if (i.id == id) return i;
        return null;
    }

    private boolean canAdd(int id, int qty, double price) {
        if (findById(id) != null) { System.out.println("  [!] Item ID " + id + " already exists."); return false; }
        if (qty < 0 || price < 0) { System.out.println("  [!] Quantity and price cannot be negative."); return false; }
        return true;
    }

    // ---------- 1. add
    public boolean addAtBeginning(String n, int id, int q, double p) {
        if (!canAdd(id, q, p)) return false;
        Item it = new Item(n, id, q, p);
        it.next = head; head = it;
        if (tail == null) tail = it;
        size++;
        return true;
    }

    public boolean addAtEnd(String n, int id, int q, double p) {
        if (!canAdd(id, q, p)) return false;
        Item it = new Item(n, id, q, p);
        if (tail == null) head = tail = it; else { tail.next = it; tail = it; }
        size++;
        return true;
    }

    public boolean addAtPosition(int pos, String n, int id, int q, double p) {
        if (pos < 1 || pos > size + 1) { System.out.println("  [!] Invalid position " + pos); return false; }
        if (pos == 1) return addAtBeginning(n, id, q, p);
        if (pos == size + 1) return addAtEnd(n, id, q, p);
        if (!canAdd(id, q, p)) return false;
        Item prev = head;
        for (int i = 1; i < pos - 1; i++) prev = prev.next;
        Item it = new Item(n, id, q, p);
        it.next = prev.next; prev.next = it;
        size++;
        return true;
    }

    // ---------- 2. remove
    public boolean removeById(int id) {
        if (head == null) { System.out.println("  [!] Inventory is empty."); return false; }
        Item del;
        if (head.id == id) { del = head; head = head.next; if (head == null) tail = null; }
        else {
            Item prev = head;
            while (prev.next != null && prev.next.id != id) prev = prev.next;
            if (prev.next == null) { System.out.println("  [!] Item ID " + id + " not found."); return false; }
            del = prev.next; prev.next = del.next;
            if (del == tail) tail = prev;
        }
        del.next = null;
        size--;
        return true;
    }

    // ---------- 3. update quantity
    public boolean updateQuantity(int id, int newQty) {
        if (newQty < 0) { System.out.println("  [!] Quantity cannot be negative."); return false; }
        Item it = findById(id);
        if (it == null) { System.out.println("  [!] Item ID " + id + " not found."); return false; }
        it.qty = newQty;
        return true;
    }

    // ---------- 4. search
    public Item searchById(int id) { return findById(id); }

    public List<Item> searchByName(String name) {
        List<Item> res = new ArrayList<>();
        for (Item i = head; i != null; i = i.next) if (i.name.equalsIgnoreCase(name)) res.add(i);
        return res;
    }

    // ---------- 5. total value
    public double totalValue() {
        double total = 0;
        for (Item i = head; i != null; i = i.next) total += i.qty * i.price;
        return total;
    }

    // ---------- 6. sort (merge sort)
    public void sort(String field, boolean ascending) {
        Comparator<Item> cmp;
        if (field.equalsIgnoreCase("name")) cmp = (a, b) -> a.name.compareToIgnoreCase(b.name);
        else if (field.equalsIgnoreCase("price")) cmp = (a, b) -> Double.compare(a.price, b.price);
        else { System.out.println("  [!] Sort field must be 'name' or 'price'."); return; }
        if (!ascending) cmp = cmp.reversed();

        head = mergeSort(head, cmp);
        tail = head;                                       // tail changed -> recompute
        while (tail != null && tail.next != null) tail = tail.next;
    }

    private Item mergeSort(Item h, Comparator<Item> cmp) {
        if (h == null || h.next == null) return h;
        Item slow = h, fast = h.next;                      // find middle
        while (fast != null && fast.next != null) { slow = slow.next; fast = fast.next.next; }
        Item second = slow.next;
        slow.next = null;                                  // split
        return merge(mergeSort(h, cmp), mergeSort(second, cmp), cmp);
    }

    private Item merge(Item a, Item b, Comparator<Item> cmp) {
        Item dummy = new Item(), t = dummy;
        while (a != null && b != null) {
            if (cmp.compare(a, b) <= 0) { t.next = a; a = a.next; }   // <= keeps the sort stable
            else { t.next = b; b = b.next; }
            t = t.next;
        }
        t.next = (a != null) ? a : b;
        return dummy.next;
    }

    public void display() {
        if (head == null) { System.out.println("  (inventory empty)"); return; }
        for (Item i = head; i != null; i = i.next) System.out.println("  " + i);
    }

    public static void main(String[] args) {
        Inventory inv = new Inventory();
        inv.addAtEnd("Keyboard", 101, 15, 1250.50);
        inv.addAtEnd("Mouse", 102, 40, 499.00);
        inv.addAtBeginning("Monitor", 103, 8, 9800.00);
        inv.addAtPosition(2, "Webcam", 104, 12, 2300.75);
        inv.addAtEnd("Laptop", 105, 5, 55000.00);
        inv.addAtEnd("Mouse", 101, 1, 1);                 // duplicate ID
        inv.addAtEnd("Bad", 106, -2, 10);                 // negative qty
        System.out.println("== Inventory ==");
        inv.display();
        System.out.printf("Total inventory value: %.2f%n", inv.totalValue());

        System.out.println("\n== Update quantity of ID 102 to 25 ==");
        inv.updateQuantity(102, 25);
        System.out.println("  " + inv.searchById(102));

        System.out.println("\n== Search ==");
        System.out.println("  By ID 104 : " + inv.searchById(104));
        System.out.println("  By name 'laptop': " + inv.searchByName("laptop"));
        System.out.println("  By ID 999 : " + inv.searchById(999));

        System.out.println("\n== Sort by name ascending ==");
        inv.sort("name", true); inv.display();
        System.out.println("\n== Sort by name descending ==");
        inv.sort("name", false); inv.display();
        System.out.println("\n== Sort by price ascending ==");
        inv.sort("price", true); inv.display();
        System.out.println("\n== Sort by price descending ==");
        inv.sort("price", false); inv.display();

        System.out.println("\n== Remove (head, tail, missing) then add at end to test tail pointer after sort ==");
        inv.removeById(105);              // head after price-desc sort
        inv.removeById(101);              // tail after price-desc sort
        inv.removeById(777);
        inv.addAtEnd("Headset", 110, 20, 899);
        inv.display();
        System.out.printf("Total inventory value: %.2f%n", inv.totalValue());
    }
}
