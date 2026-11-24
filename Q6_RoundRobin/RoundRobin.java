import java.util.*;

/**
 * Q6 - Circular Linked List: Round Robin CPU Scheduling
 * Process(pid, burst, priority). All processes arrive at time 0.
 *   completion = time the process finishes
 *   turnaround = completion - arrival(0)
 *   waiting    = turnaround - burst
 * Queue is a circular singly linked list with a tail pointer (head = tail.next).
 */
public class RoundRobin {

    static class Process {
        int pid, burst, priority, remaining;
        int completion, waiting, turnaround;
        Process next;
        Process(int pid, int burst, int priority) {
            this.pid = pid; this.burst = burst; this.priority = priority; this.remaining = burst;
        }
    }

    private Process tail;
    private int size;
    private final List<Process> finished = new ArrayList<>();

    // ---------- 1. add at end
    public boolean addProcess(int pid, int burst, int priority) {
        if (burst <= 0) { System.out.println("  [!] Burst time must be positive."); return false; }
        if (find(pid) != null) { System.out.println("  [!] Process " + pid + " already exists."); return false; }
        Process p = new Process(pid, burst, priority);
        if (tail == null) { p.next = p; tail = p; }
        else { p.next = tail.next; tail.next = p; tail = p; }
        size++;
        return true;
    }

    private Process find(int pid) {
        if (tail == null) return null;
        Process p = tail.next;
        for (int i = 0; i < size; i++, p = p.next) if (p.pid == pid) return p;
        return null;
    }

    // ---------- 2. remove by pid; returns the node that followed it (null if the queue became empty)
    public Process removeById(int pid) {
        Process target = find(pid);
        if (target == null) { System.out.println("  [!] Process " + pid + " not found."); return null; }
        if (size == 1) { tail = null; size = 0; target.next = null; return null; }
        Process prev = tail;
        while (prev.next != target) prev = prev.next;           // find predecessor
        Process after = target.next;
        prev.next = after;
        if (target == tail) tail = prev;
        target.next = null;
        size--;
        return after;
    }

    // ---------- 4. display queue
    private String queueToString() {
        if (tail == null) return "[empty]";
        StringBuilder sb = new StringBuilder("[");
        Process p = tail.next;
        for (int i = 0; i < size; i++, p = p.next) sb.append(i > 0 ? ", " : "").append("P").append(p.pid).append("(rem=").append(p.remaining).append(")");
        return sb.append("]  (circular: tail -> head)").toString();
    }

    // ---------- 3. simulate
    public void simulate(int quantum) {
        if (tail == null) { System.out.println("  [!] No processes to schedule."); return; }
        if (quantum <= 0) { System.out.println("  [!] Quantum must be positive."); return; }
        int time = 0, round = 0;
        Process cur = tail.next;
        System.out.println("  Time quantum = " + quantum);
        System.out.println("  Initial queue: " + queueToString());

        while (tail != null) {
            round++;
            int inThisRound = size;                              // one round = one pass over the current processes
            System.out.println("\n  --- Round " + round + " ---");
            for (int i = 0; i < inThisRound && tail != null; i++) {
                int run = Math.min(quantum, cur.remaining);
                System.out.printf("  t=%-3d P%d runs %d unit(s)", time, cur.pid, run);
                time += run;
                cur.remaining -= run;
                if (cur.remaining == 0) {
                    cur.completion = time;
                    cur.turnaround = cur.completion;
                    cur.waiting = cur.turnaround - cur.burst;
                    System.out.println("  -> finished at t=" + time + ", removed");
                    finished.add(cur);
                    cur = removeById(cur.pid);                   // advance to the next process automatically
                } else {
                    System.out.println("  -> remaining " + cur.remaining);
                    cur = cur.next;
                }
            }
            System.out.println("  Queue after round " + round + ": " + queueToString());
        }
    }

    // ---------- 5. statistics
    public void printStatistics() {
        finished.sort(Comparator.comparingInt(p -> p.pid));
        System.out.println("\n  PID | Burst | Priority | Completion | Turnaround | Waiting");
        double sw = 0, st = 0;
        for (Process p : finished) {
            System.out.printf("  P%-2d | %5d | %8d | %10d | %10d | %7d%n", p.pid, p.burst, p.priority, p.completion, p.turnaround, p.waiting);
            sw += p.waiting; st += p.turnaround;
        }
        System.out.printf("  Average waiting time    = %.2f%n", sw / finished.size());
        System.out.printf("  Average turn-around time = %.2f%n", st / finished.size());
    }

    public static void main(String[] args) {
        RoundRobin rr = new RoundRobin();
        rr.addProcess(1, 10, 2);
        rr.addProcess(2, 5, 1);
        rr.addProcess(3, 8, 3);
        rr.addProcess(4, 6, 2);
        rr.addProcess(2, 3, 1);          // duplicate pid
        rr.addProcess(5, 0, 1);          // invalid burst
        rr.removeById(99);               // missing
        rr.simulate(4);
        rr.printStatistics();
    }
}
