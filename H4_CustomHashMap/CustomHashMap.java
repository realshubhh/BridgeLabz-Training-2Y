import java.util.*;

/**
 * H4 - Custom HashMap with SEPARATE CHAINING (array of singly linked lists).
 *   - table length is always a power of two, so index = hash & (length - 1) (fast modulo)
 *   - hash spreading: h ^ (h >>> 16) mixes high bits into low bits to reduce collisions
 *   - resize (double + rehash) when size > 0.75 * capacity, keeping chains short -> O(1) average
 *   - supports null keys (stored with hash 0), put (insert/update), get, remove, containsKey, size
 */
public class CustomHashMap<K, V> {

    private static class Node<K, V> {
        final K key; V value; final int hash; Node<K, V> next;
        Node(K key, V value, int hash, Node<K, V> next) { this.key = key; this.value = value; this.hash = hash; this.next = next; }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<K, V>[] table;
    private int size;
    private int resizes;

    public CustomHashMap() { this(DEFAULT_CAPACITY); }

    @SuppressWarnings("unchecked")
    public CustomHashMap(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException("capacity must be >= 1");
        int c = 1;
        while (c < capacity) c <<= 1;                          // round up to power of two
        table = (Node<K, V>[]) new Node[c];
    }

    private static int hash(Object key) {
        if (key == null) return 0;
        int h = key.hashCode();
        return h ^ (h >>> 16);
    }

    private static int indexFor(int hash, int length) { return hash & (length - 1); }

    private Node<K, V> findNode(Object key) {
        int h = hash(key);
        for (Node<K, V> n = table[indexFor(h, table.length)]; n != null; n = n.next)
            if (n.hash == h && Objects.equals(n.key, key)) return n;
        return null;
    }

    /** Inserts or updates; returns the previous value (or null). */
    public V put(K key, V value) {
        Node<K, V> existing = findNode(key);
        if (existing != null) { V old = existing.value; existing.value = value; return old; }
        int h = hash(key);
        int idx = indexFor(h, table.length);
        table[idx] = new Node<>(key, value, h, table[idx]);   // insert at head of chain: O(1)
        size++;
        if (size > LOAD_FACTOR * table.length) resize();
        return null;
    }

    public V get(Object key) {
        Node<K, V> n = findNode(key);
        return n == null ? null : n.value;
    }

    public boolean containsKey(Object key) { return findNode(key) != null; }

    /** Removes the key; returns its value (or null if absent). */
    public V remove(Object key) {
        int h = hash(key);
        int idx = indexFor(h, table.length);
        Node<K, V> prev = null, cur = table[idx];
        while (cur != null) {
            if (cur.hash == h && Objects.equals(cur.key, key)) {
                if (prev == null) table[idx] = cur.next; else prev.next = cur.next;   // unlink
                cur.next = null;
                size--;
                return cur.value;
            }
            prev = cur; cur = cur.next;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] old = table;
        Node<K, V>[] bigger = (Node<K, V>[]) new Node[old.length * 2];
        for (Node<K, V> head : old) {
            for (Node<K, V> n = head; n != null; ) {
                Node<K, V> next = n.next;
                int idx = indexFor(n.hash, bigger.length);   // stored hash: no hashCode() recomputation
                n.next = bigger[idx];
                bigger[idx] = n;
                n = next;
            }
        }
        table = bigger;
        resizes++;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public int capacity() { return table.length; }
    public int resizeCount() { return resizes; }

    public int longestChain() {
        int best = 0;
        for (Node<K, V> head : table) { int c = 0; for (Node<K, V> n = head; n != null; n = n.next) c++; best = Math.max(best, c); }
        return best;
    }

    public void printBuckets() {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            StringBuilder sb = new StringBuilder("  bucket " + i + ": ");
            for (Node<K, V> n = table[i]; n != null; n = n.next) sb.append("[").append(n.key).append("=").append(n.value).append("]").append(n.next != null ? " -> " : "");
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {
        CustomHashMap<String, Integer> m = new CustomHashMap<>(8);
        System.out.println("== Basic operations ==");
        m.put("apple", 10); m.put("banana", 20); m.put("cherry", 30);
        System.out.println("  get(banana) = " + m.get("banana") + ", get(grape) = " + m.get("grape"));
        System.out.println("  put(apple, 99) returns old value " + m.put("apple", 99) + ", now " + m.get("apple") + ", size " + m.size());
        System.out.println("  remove(banana) = " + m.remove("banana") + ", remove(banana) again = " + m.remove("banana") + ", size " + m.size());
        m.put(null, 0);
        System.out.println("  null key supported: get(null) = " + m.get(null) + ", containsKey(null) = " + m.containsKey(null));

        System.out.println("\n== Collision demo: \"Aa\", \"BB\" and \"C#\" all have hashCode " + "Aa".hashCode() + " ==");
        CustomHashMap<String, String> c = new CustomHashMap<>(16);
        c.put("Aa", "first"); c.put("BB", "second"); c.put("C#", "third");
        c.printBuckets();
        System.out.println("  get(Aa) = " + c.get("Aa") + ", get(BB) = " + c.get("BB") + ", remove(Aa) -> " + c.remove("Aa") + ", get(BB) still = " + c.get("BB"));

        System.out.println("\n== Resizing: insert 1000 integer keys into a map that starts with capacity 4 ==");
        CustomHashMap<Integer, Integer> big = new CustomHashMap<>(4);
        for (int i = 0; i < 1000; i++) big.put(i, i * i);
        System.out.println("  size " + big.size() + ", capacity " + big.capacity() + ", resizes " + big.resizeCount() + ", longest chain " + big.longestChain());

        System.out.println("\n== Randomised test: 200000 put/get/remove operations vs java.util.HashMap ==");
        Random rnd = new Random(1);
        CustomHashMap<Integer, Integer> mine = new CustomHashMap<>();
        Map<Integer, Integer> ref = new HashMap<>();
        boolean ok = true;
        for (int i = 0; i < 200000 && ok; i++) {
            int k = rnd.nextInt(500), op = rnd.nextInt(3);
            if (op == 0) { int v = rnd.nextInt(); ok = Objects.equals(mine.put(k, v), ref.put(k, v)); }
            else if (op == 1) ok = Objects.equals(mine.get(k), ref.get(k));
            else ok = Objects.equals(mine.remove(k), ref.remove(k));
            ok = ok && mine.size() == ref.size();
        }
        System.out.println("  " + (ok ? "PASS" : "FAIL") + " (final size " + mine.size() + ")");
    }
}
