/**
 * Q8 - Doubly Linked List: Undo/Redo for a Text Editor
 * Each node = one state of the text.  prev = undo direction, next = redo direction.
 * 'current' points to the state being shown.  History is capped at 'limit' states:
 * when it overflows, the OLDEST state (head) is dropped.
 * Doing a new action after an undo discards the redo branch (like real editors).
 */
public class UndoRedoEditor {

    static class State {
        String text; State prev, next;
        State(String text) { this.text = text; }
    }

    private State head, tail, current;
    private int size;
    private final int limit;

    public UndoRedoEditor(int limit) {
        if (limit < 1) throw new IllegalArgumentException("limit must be >= 1");
        this.limit = limit;
        head = tail = current = new State("");       // initial empty document
        size = 1;
    }

    // ---------- 1. new state at the end
    public void perform(String newText) {
        // user acted after an undo -> redo history is no longer valid, cut it off
        if (current != tail) {
            for (State s = current.next; s != null; s = s.next) size--;
            current.next.prev = null;
            current.next = null;
            tail = current;
        }
        State s = new State(newText);
        s.prev = tail; tail.next = s; tail = s; current = s;
        size++;

        if (size > limit) {                           // 5. enforce fixed history size
            State old = head;
            head = head.next;
            head.prev = null;
            old.next = null;
            size--;
        }
    }

    /** Convenience: type a word, i.e. new state = current text + word. */
    public void type(String word) {
        perform(current.text.isEmpty() ? word : current.text + " " + word);
    }

    // ---------- 2. undo
    public boolean undo() {
        if (current.prev == null) { System.out.println("  [!] Nothing to undo."); return false; }
        current = current.prev;
        return true;
    }

    public boolean canUndo() { return current.prev != null; }

    // ---------- 3. redo
    public boolean redo() {
        if (current.next == null) { System.out.println("  [!] Nothing to redo."); return false; }
        current = current.next;
        return true;
    }

    // ---------- 4. display
    public void showCurrent() { System.out.println("  Current text: \"" + current.text + "\""); }

    public void showHistory() {
        StringBuilder sb = new StringBuilder("  History (" + size + "/" + limit + "): ");
        for (State s = head; s != null; s = s.next)
            sb.append(s == current ? "[" : "").append("\"").append(s.text).append("\"").append(s == current ? "]" : "").append(s.next != null ? " <-> " : "");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        System.out.println("== Basic typing, undo, redo (limit = 10) ==");
        UndoRedoEditor ed = new UndoRedoEditor(10);
        ed.type("Hello"); ed.type("linked"); ed.type("list");
        ed.showCurrent(); ed.showHistory();
        ed.undo(); ed.showCurrent();
        ed.undo(); ed.showCurrent();
        ed.redo(); ed.showCurrent();

        System.out.println("\n== New action after undo discards the redo branch ==");
        ed.type("world");
        ed.showCurrent(); ed.showHistory();
        ed.redo();                                   // nothing to redo now

        System.out.println("\n== Undo all the way back ==");
        while (ed.canUndo()) ed.undo();
        ed.showCurrent();
        ed.undo();

        System.out.println("\n== History limit: type 12 words with limit 10 ==");
        UndoRedoEditor big = new UndoRedoEditor(10);
        for (int i = 1; i <= 12; i++) big.type("w" + i);
        big.showHistory();
        int steps = 0;
        while (big.canUndo()) { big.undo(); steps++; }
        System.out.println("  Could undo only " + steps + " steps (oldest states were dropped)");
        big.showCurrent();
        for (int i = 0; i < 3; i++) big.redo();
        big.showCurrent();
    }
}
