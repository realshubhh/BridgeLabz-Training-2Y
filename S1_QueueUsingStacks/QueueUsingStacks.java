import java.util.*;

/**
 * S1 - Queue using two stacks.
 *   inStack  : receives every enqueue (push)
 *   outStack : serves every dequeue/peek. When empty, ALL elements of inStack are moved over
 *              (which reverses their order, so the oldest element ends on top).
 * enqueue = O(1); dequeue/peek = amortized O(1) (each element is moved at most once).
 * ArrayDeque is used as the stack (push/pop/peek) as recommended over the legacy java.util.Stack.
 */
public class QueueUsingStacks<T> {
    private final Deque<T> inStack = new ArrayDeque<>();
    private final Deque<T> outStack = new ArrayDeque<>();
    private long transfers = 0;                               // for analysis only

    public void enqueue(T x) { inStack.push(x); }

    private void shiftIfNeeded() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) { outStack.push(inStack.pop()); transfers++; }
        }
    }

    public T dequeue() {
        shiftIfNeeded();
        if (outStack.isEmpty()) throw new NoSuchElementException("Queue underflow: dequeue on empty queue");
        return outStack.pop();
    }

    public T peek() {
        shiftIfNeeded();
        if (outStack.isEmpty()) throw new NoSuchElementException("Queue is empty");
        return outStack.peek();
    }

    public boolean isEmpty() { return inStack.isEmpty() && outStack.isEmpty(); }
    public int size() { return inStack.size() + outStack.size(); }
    public long transfers() { return transfers; }

    public static void main(String[] args) {
        System.out.println("== Basic behaviour ==");
        QueueUsingStacks<Integer> q = new QueueUsingStacks<>();
        q.enqueue(1); q.enqueue(2); q.enqueue(3);
        System.out.println("  peek    -> " + q.peek());
        System.out.println("  dequeue -> " + q.dequeue());
        q.enqueue(4);
        System.out.println("  dequeue -> " + q.dequeue());
        System.out.println("  dequeue -> " + q.dequeue());
        System.out.println("  dequeue -> " + q.dequeue());
        System.out.println("  isEmpty -> " + q.isEmpty());
        try { q.dequeue(); } catch (NoSuchElementException e) { System.out.println("  underflow handled: " + e.getMessage()); }

        System.out.println("\n== Amortized cost: 100000 enqueues then 100000 dequeues ==");
        QueueUsingStacks<Integer> big = new QueueUsingStacks<>();
        int n = 100000;
        for (int i = 0; i < n; i++) big.enqueue(i);
        boolean ordered = true;
        for (int i = 0; i < n; i++) if (big.dequeue() != i) ordered = false;
        System.out.println("  FIFO order kept: " + ordered + ", element transfers = " + big.transfers() + " (= n, so 1 move per element)");

        System.out.println("\n== Randomised test against java.util.ArrayDeque used as a queue ==");
        Random rnd = new Random(7);
        QueueUsingStacks<Integer> mine = new QueueUsingStacks<>();
        Queue<Integer> ref = new ArrayDeque<>();
        boolean ok = true;
        for (int i = 0; i < 200000 && ok; i++) {
            if (rnd.nextInt(3) != 0 || ref.isEmpty()) { int v = rnd.nextInt(1000); mine.enqueue(v); ref.add(v); }
            else ok = mine.dequeue().equals(ref.poll());
            if (ok) ok = mine.size() == ref.size();
        }
        System.out.println("  200000 random operations match reference: " + (ok ? "PASS" : "FAIL"));
    }
}
