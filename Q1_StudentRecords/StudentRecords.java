/**
 * Q1 - Singly Linked List: Student Record Management
 * Node = Student(rollNo, name, age, grade). Head = first student, last node's next = null.
 * A tail pointer is kept so adding at the end is O(1).
 */
public class StudentRecords {

    static class Student {
        int rollNo; String name; int age; String grade;
        Student next;
        Student(int rollNo, String name, int age, String grade) {
            this.rollNo = rollNo; this.name = name; this.age = age; this.grade = grade;
        }
        @Override public String toString() {
            return String.format("Roll: %-4d | Name: %-10s | Age: %-3d | Grade: %s", rollNo, name, age, grade);
        }
    }

    private Student head, tail;
    private int size;

    // ---------- helpers
    private Student find(int rollNo) {
        for (Student s = head; s != null; s = s.next) if (s.rollNo == rollNo) return s;
        return null;
    }

    private boolean canAdd(int rollNo) {
        if (find(rollNo) != null) { System.out.println("  [!] Roll number " + rollNo + " already exists."); return false; }
        return true;
    }

    // ---------- 1. add
    public boolean addAtBeginning(int roll, String name, int age, String grade) {
        if (!canAdd(roll)) return false;
        Student s = new Student(roll, name, age, grade);
        s.next = head;
        head = s;
        if (tail == null) tail = s;                // list was empty
        size++;
        return true;
    }

    public boolean addAtEnd(int roll, String name, int age, String grade) {
        if (!canAdd(roll)) return false;
        Student s = new Student(roll, name, age, grade);
        if (tail == null) head = tail = s;
        else { tail.next = s; tail = s; }
        size++;
        return true;
    }

    /** position is 1-based: 1 = beginning, size+1 = end. */
    public boolean addAtPosition(int pos, int roll, String name, int age, String grade) {
        if (pos < 1 || pos > size + 1) { System.out.println("  [!] Invalid position " + pos + " (valid: 1.." + (size + 1) + ")"); return false; }
        if (pos == 1) return addAtBeginning(roll, name, age, grade);
        if (pos == size + 1) return addAtEnd(roll, name, age, grade);
        if (!canAdd(roll)) return false;
        Student prev = head;
        for (int i = 1; i < pos - 1; i++) prev = prev.next;   // stop at node before the position
        Student s = new Student(roll, name, age, grade);
        s.next = prev.next;
        prev.next = s;
        size++;
        return true;
    }

    // ---------- 2. delete
    public boolean deleteByRoll(int roll) {
        if (head == null) { System.out.println("  [!] List is empty."); return false; }
        Student del;
        if (head.rollNo == roll) {                  // deleting head
            del = head;
            head = head.next;
            if (head == null) tail = null;          // list became empty
        } else {
            Student prev = head;
            while (prev.next != null && prev.next.rollNo != roll) prev = prev.next;
            if (prev.next == null) { System.out.println("  [!] Roll " + roll + " not found."); return false; }
            del = prev.next;
            prev.next = del.next;
            if (del == tail) tail = prev;           // deleting tail
        }
        del.next = null;                            // help GC / clear pointer
        size--;
        return true;
    }

    // ---------- 3. search
    public Student search(int roll) { return find(roll); }

    // ---------- 4. display
    public void display() {
        if (head == null) { System.out.println("  (no records)"); return; }
        for (Student s = head; s != null; s = s.next) System.out.println("  " + s);
        System.out.println("  Total students: " + size);
    }

    // ---------- 5. update grade
    public boolean updateGrade(int roll, String newGrade) {
        Student s = find(roll);
        if (s == null) { System.out.println("  [!] Roll " + roll + " not found."); return false; }
        s.grade = newGrade;
        return true;
    }

    public static void main(String[] args) {
        StudentRecords list = new StudentRecords();

        System.out.println("== Add at end / beginning / position ==");
        list.addAtEnd(101, "Aarav", 19, "A");
        list.addAtEnd(103, "Diya", 20, "B+");
        list.addAtBeginning(100, "Rohan", 19, "A+");
        list.addAtPosition(3, 102, "Meera", 21, "B");      // between 101 and 103
        list.addAtPosition(1, 99, "Kabir", 20, "C");        // acts as add-at-beginning
        list.addAtPosition(6, 104, "Sana", 19, "A");        // acts as add-at-end
        list.addAtEnd(101, "Duplicate", 18, "F");           // duplicate roll -> rejected
        list.addAtPosition(9, 105, "Invalid", 18, "F");     // invalid position -> rejected
        list.display();

        System.out.println("\n== Search ==");
        Student s = list.search(102);
        System.out.println(s != null ? "  Found: " + s : "  Not found");
        System.out.println(list.search(555) != null ? "  Found" : "  Roll 555 not found");

        System.out.println("\n== Update grade of roll 103 to A ==");
        list.updateGrade(103, "A");
        System.out.println("  " + list.search(103));

        System.out.println("\n== Delete head (99), middle (102), tail (104), missing (555) ==");
        list.deleteByRoll(99);
        list.deleteByRoll(102);
        list.deleteByRoll(104);
        list.deleteByRoll(555);
        list.display();

        System.out.println("\n== Delete all, then delete on empty list ==");
        list.deleteByRoll(100); list.deleteByRoll(101); list.deleteByRoll(103);
        list.display();
        list.deleteByRoll(100);
    }
}
