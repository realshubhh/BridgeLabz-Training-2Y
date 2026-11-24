import java.time.LocalDate;
import java.util.*;

/**
 * Q3 - Circular Linked List: Task Scheduler
 * Task(id, name, priority, dueDate). Only a TAIL pointer is stored; head = tail.next.
 * 'current' marks the task being viewed. Priority: 1 = highest.
 * All traversals are bounded by 'size' so they stop after exactly one full cycle (no infinite loop).
 */
public class TaskScheduler {

    static class Task {
        int id; String name; int priority; LocalDate due;
        Task next;
        Task(int id, String name, int priority, LocalDate due) {
            this.id = id; this.name = name; this.priority = priority; this.due = due;
        }
        @Override public String toString() {
            return String.format("ID: %-3d | %-18s | Priority: %d | Due: %s", id, name, priority, due);
        }
    }

    private Task tail;      // tail.next == head
    private Task current;
    private int size;

    private Task find(int id) {
        if (tail == null) return null;
        Task t = tail.next;
        for (int i = 0; i < size; i++, t = t.next) if (t.id == id) return t;
        return null;
    }

    private boolean canAdd(int id) {
        if (find(id) != null) { System.out.println("  [!] Task ID " + id + " already exists."); return false; }
        return true;
    }

    // ---------- 1. add
    public boolean addAtBeginning(int id, String name, int pr, LocalDate due) {
        if (!canAdd(id)) return false;
        Task t = new Task(id, name, pr, due);
        if (tail == null) { t.next = t; tail = t; current = t; }
        else { t.next = tail.next; tail.next = t; }          // new head; tail unchanged
        size++;
        return true;
    }

    public boolean addAtEnd(int id, String name, int pr, LocalDate due) {
        if (!canAdd(id)) return false;
        Task t = new Task(id, name, pr, due);
        if (tail == null) { t.next = t; tail = t; current = t; }
        else { t.next = tail.next; tail.next = t; tail = t; } // new tail, still points to head
        size++;
        return true;
    }

    public boolean addAtPosition(int pos, int id, String name, int pr, LocalDate due) {
        if (pos < 1 || pos > size + 1) { System.out.println("  [!] Invalid position " + pos); return false; }
        if (pos == 1) return addAtBeginning(id, name, pr, due);
        if (pos == size + 1) return addAtEnd(id, name, pr, due);
        if (!canAdd(id)) return false;
        Task prev = tail.next;
        for (int i = 1; i < pos - 1; i++) prev = prev.next;
        Task t = new Task(id, name, pr, due);
        t.next = prev.next; prev.next = t;
        size++;
        return true;
    }

    // ---------- 2. remove
    public boolean removeById(int id) {
        if (tail == null) { System.out.println("  [!] Scheduler is empty."); return false; }
        Task prev = tail, cur = tail.next;                    // prev always trails cur
        for (int i = 0; i < size; i++) {
            if (cur.id == id) {
                if (size == 1) { tail = null; current = null; }
                else {
                    prev.next = cur.next;                      // bypass node, circle stays closed
                    if (cur == tail) tail = prev;
                    if (cur == current) current = cur.next;
                }
                cur.next = null;
                size--;
                return true;
            }
            prev = cur; cur = cur.next;
        }
        System.out.println("  [!] Task ID " + id + " not found.");
        return false;
    }

    // ---------- 3. view current and move to next
    public void viewCurrentAndMoveNext() {
        if (current == null) { System.out.println("  [!] No tasks."); return; }
        System.out.println("  Current : " + current);
        current = current.next;
        System.out.println("  Next up : " + current.name);
    }

    // ---------- 4. display from head
    public void displayAll() {
        if (tail == null) { System.out.println("  (no tasks)"); return; }
        Task t = tail.next;
        for (int i = 0; i < size; i++, t = t.next) System.out.println("  " + t + (t == current ? "   <-- current" : ""));
        System.out.println("  (tail.next -> head ID " + tail.next.id + ", total " + size + ")");
    }

    // ---------- 5. search by priority
    public List<Task> searchByPriority(int pr) {
        List<Task> res = new ArrayList<>();
        if (tail == null) return res;
        Task t = tail.next;
        for (int i = 0; i < size; i++, t = t.next) if (t.priority == pr) res.add(t);
        return res;
    }

    public static void main(String[] args) {
        TaskScheduler ts = new TaskScheduler();
        ts.addAtEnd(1, "Write report", 2, LocalDate.of(2026, 12, 1));
        ts.addAtEnd(2, "Team meeting", 1, LocalDate.of(2026, 11, 28));
        ts.addAtBeginning(3, "Pay bills", 1, LocalDate.of(2026, 11, 30));
        ts.addAtPosition(3, 4, "Code review", 3, LocalDate.of(2026, 12, 5));
        ts.addAtEnd(2, "Dup", 1, LocalDate.of(2026, 1, 1));         // duplicate -> rejected
        ts.addAtPosition(9, 9, "Bad pos", 1, LocalDate.of(2026, 1, 1));
        System.out.println("== All tasks from head ==");
        ts.displayAll();

        System.out.println("\n== View current and move next (6 times: loops around) ==");
        for (int i = 0; i < 6; i++) ts.viewCurrentAndMoveNext();

        System.out.println("\n== Search priority 1 ==");
        for (Task t : ts.searchByPriority(1)) System.out.println("  " + t);
        System.out.println("== Search priority 5 ==");
        if (ts.searchByPriority(5).isEmpty()) System.out.println("  No tasks with that priority.");

        System.out.println("\n== Remove tail (ID 2, also the current task), head (ID 3, now current), missing ID ==");
        ts.removeById(2);
        ts.removeById(3);
        ts.removeById(99);
        ts.displayAll();
        ts.removeById(1);
        ts.displayAll();
        System.out.println("\n== Remove last task, then operate on empty scheduler ==");
        ts.removeById(4);
        ts.displayAll();
        ts.viewCurrentAndMoveNext();
        ts.removeById(4);
    }
}
