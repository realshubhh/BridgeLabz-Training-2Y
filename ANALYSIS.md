# Stacks, Queues, Hash Maps & Hash Functions: Analysis and Solutions

**Name:** Shubh Goyal | B.Tech CSE (Business Informatics), GLA University
**Language:** Java (JDK 11+). Every program has a `main()` that demonstrates the problem and checks the result against a brute-force or library reference. Real output is saved in `output.txt` in each folder.

---

## 1. Summary table

| # | Problem | Data structure | Time | Space | Verified by |
|---|---|---|---|---|---|
| S1 | Queue using two stacks | 2 × `ArrayDeque` as stacks | enqueue O(1), dequeue **amortized O(1)** | O(n) | 200,000 random ops vs `ArrayDeque` queue |
| S2 | Sort a stack (recursion) | The stack itself + call stack | O(n²) | O(n) recursion depth | 300 random stacks vs `Arrays.sort` |
| S3 | Stock span | Stack of indices | **O(n)** | O(n) | 500 random arrays vs O(n²) brute force |
| S4 | Sliding window maximum | Deque of indices | **O(n)** | O(k) | 1000 random cases vs brute force |
| S5 | Circular tour | Queue of current pumps | **O(n)** | O(n) | 2000 random cases vs try-every-start |
| H1 | All zero-sum subarrays | `HashMap<prefix sum, indices>` | O(n + K), K = answers | O(n) | 1000 arrays vs O(n²) |
| H2 | Pair with given sum | `HashSet` | O(n) avg | O(n) | 3000 cases vs brute force |
| H3 | Longest consecutive sequence | `HashSet` | **O(n)** | O(n) | 2000 arrays vs sorting approach |
| H4 | Custom hash map | Array of linked lists (chaining) | O(1) avg per op | O(n) | 200,000 ops vs `java.util.HashMap` |
| H5 | Two Sum (indices) | `HashMap<value, index>` | O(n) | O(n) | 3000 cases vs brute force |

---

## 2. Problem-by-problem analysis

### S1: Queue Using Stacks
- **Idea:** `inStack` takes every enqueue. `outStack` serves every dequeue/peek. When `outStack` is empty, move *all* of `inStack` into it, which reverses the order so the oldest element is on top.
- **Why amortized O(1):** each element is moved at most once. Measured: 100,000 enqueues + 100,000 dequeues caused exactly **100,000 transfers** (1 per element).
- **Naive alternative:** moving elements back and forth on every operation makes each dequeue O(n).
- **Edge cases handled:** dequeue/peek on an empty queue throws `NoSuchElementException` (underflow check); interleaved enqueue/dequeue keeps FIFO order, because the transfer happens only when `outStack` is empty.

### S2: Sort a Stack Using Recursion
- **Idea:** `sortStack` pops one element `x`, recursively sorts the rest, then `sortedInsert` puts `x` back in the right place (popping larger elements and restoring them on the way back).
- **Result order:** the largest element ends on top (bottom → top is ascending).
- **Complexity:** O(n²) time (n inserts, each up to n steps); O(n) call-stack depth.
- **Measured limit:** worst case (top = smallest) took 22 ms for 1,000 elements, 209 ms for 10,000 and 876 ms for 20,000, which shows the quadratic growth. At **50,000 elements the program threw `StackOverflowError`** with the default JVM stack size.
- **Takeaway:** the base cases (`isEmpty`) are essential, and recursion depth limits the input size. Use a library sort for large data.
- **Duplicates:** handled, because `peek() <= x` stops at equal values.

### S3: Stock Span
- **Idea:** keep a stack of indices whose prices are strictly decreasing. For day `i`, pop every index with price ≤ today's price. The remaining top is the last *higher* price, so span = `i - top` (or `i + 1` if the stack is empty).
- **Why O(n):** each index is pushed once and popped at most once.
- **Example:** `[100, 80, 60, 70, 60, 75, 85]` → `[1, 1, 1, 2, 1, 4, 6]` (day-by-day stack trace is in the output).
- **Edge cases:** equal prices count (`<=`, so `[5,5,5]` → `[1,2,3]`); empty and single-element arrays.

### S4: Sliding Window Maximum
- **Idea:** the deque holds indices of the window in decreasing order of value, so the front is always the maximum. For each `i`: drop the front if it left the window, drop back elements ≤ `a[i]` (they can never be a maximum again), then add `i`.
- **Why O(n):** each index is added once and removed once. The brute force is O(n·k).
- **Example:** `[1,3,-1,-3,5,3,6,7]`, k = 3 → `[3,3,5,5,6,7]`.
- **Edge cases:** k = 1 (returns the array), k = n (a single maximum), all-equal values, descending input, and invalid k (0 or larger than n) rejected with `IllegalArgumentException`.

### S5: Circular Tour (Petrol Pumps)
- **Idea:** the queue holds the pumps of the tour being tried, with a running `surplus`. Add the next pump to the back. While the surplus is negative, the front pump cannot be the start, so remove it. When the queue holds all n pumps with surplus ≥ 0, the front is the answer.
- **Why O(n):** at most 2n additions and n removals.
- **Example:** petrol `[4,6,7,4]`, distance `[6,5,3,5]` → start at pump **1** (hand-checked: surpluses 1, 5, 4, 2, never negative).
- **Edge cases:** total petrol < total distance → `-1`; a single pump; mismatched array lengths rejected; `long` surplus avoids overflow.

### H1: All Subarrays with Zero Sum
- **Idea:** subarray `(s..e)` has sum 0 exactly when `prefix[e] == prefix[s-1]`. The map stores every index where each prefix sum occurred (the empty prefix at index −1 is pre-loaded).
- **Why the hint says "frequency" and the code stores a list:** a frequency counts how many zero-sum subarrays exist, but listing them needs the indices. Storing index lists gives both.
- **Complexity:** O(n + K), K = number of subarrays reported. `[0,0,0]` has 6, so the output itself can be quadratic.
- **Example:** `[6,3,-1,-3,4,-2,2,4,6,-1]` → `[-1,-3,4]`, `[-1,-3,4,-2,2]`, `[-2,2]`.

### H2: Pair With Given Sum
- **Idea:** for each `x`, check whether `target - x` is already in the set, then insert `x`. Inserting *after* the check prevents pairing an element with itself, while `[3,3]` with target 6 still works.
- **Edge cases:** negative numbers, empty or single-element arrays, and overflow (e.g. `Integer.MAX_VALUE + 1` against `Integer.MIN_VALUE`) is avoided by using `long`.

### H3: Longest Consecutive Sequence
- **Idea:** put all values in a set. Only start counting at a value `x` where `x-1` is *not* in the set (the start of a run), then walk upward.
- **Why O(n) and not O(n²):** the inner `while` only runs from run starts, so each element is visited at most twice in total.
- **Example:** `[0,3,7,2,5,8,4,6,0,1]` → length 9 (0..8). Duplicates are ignored by the set.
- **Edge cases:** empty array → 0; `Integer.MIN_VALUE` / `MAX_VALUE` (stored as `long` so `x-1` doesn't wrap).

### H4: Custom Hash Map
- **Design:** array of singly linked lists (**separate chaining**). The table length is always a power of two, so `index = hash & (length-1)`. The hash is spread with `h ^ (h >>> 16)`. The table doubles and rehashes when `size > 0.75 × capacity`, and each node stores its hash so rehashing never recalls `hashCode()`.
- **Operations:** `put` (insert or update, returns the old value), `get`, `remove` (unlinks with a `prev` pointer), `containsKey`, `size`, plus null-key support.
- **Collision demo:** `"Aa"`, `"BB"` and `"C#"` all have `hashCode` 2112, so they share bucket 0. All three are stored and retrieved correctly, and removing one leaves the others intact.
- **Resizing:** inserting 1,000 keys into a map that started at capacity 4 caused 9 resizes (final capacity 2048) and a **longest chain of 1**, so lookups stayed O(1).
- **Complexity:** O(1) average per operation; worst case O(n) if every key collides (a good hash and the load factor prevent this in practice).
- **Verification:** 200,000 random put/get/remove operations produced the same results as `java.util.HashMap`.

### H5: Two Sum
- **Idea:** map `value → index`. For each `i`, if `target - a[i]` is in the map, its stored index is the partner. The lookup happens before the insert, so the same element is never used twice.
- **Difference from H2:** H2 only answers yes/no (a set is enough), while H5 returns indices, so the map must store them.
- **Example:** `[2,7,11,15]`, target 9 → `[0, 1]`; `[3,3]`, target 6 → `[0, 1]`.

---

## 3. How the "Best Practices" sheet was applied

| Best practice | Where / how |
|---|---|
| Use stacks for reversible/nested problems | S1 (order reversal), S2, S3 |
| Use `Deque` instead of the legacy `Stack` class | `ArrayDeque` is the stack in S1, S2, S3 |
| Check stack/queue underflow | S1 throws a clear `NoSuchElementException`; S2 returns on an empty stack; S4 and S5 validate inputs |
| Clear base case in recursion | S2: `isEmpty()` in `sortStack`, and `isEmpty() \|\| top <= x` in `sortedInsert` |
| Choose the right queue type | S1/S5: simple queue; S4: **deque** (both ends needed) |
| Avoid polling empty queues | S5 and S4 check `isEmpty()` before `peek`/`poll` |
| Use the Collections framework | `ArrayDeque`, `HashMap`, `HashSet` everywhere except H4, which is a from-scratch implementation by design |
| Thread safety | Not needed for single-threaded solutions. For concurrent use the sheet suggests `ConcurrentLinkedDeque`, `BlockingQueue` or `ConcurrentLinkedQueue` |

Two practices from the sheet were not implemented: the **min/max auxiliary stack** and **lazy deletion for priority queues**, because none of the ten problems needs them.

---

## 4. Findings worth mentioning
1. **Amortized vs worst case (S1):** a single dequeue can cost O(n), but the total cost over n operations is O(n).
2. **Monotonic stack/deque (S3, S4):** the "pop smaller elements" trick turns O(n²) / O(n·k) brute forces into O(n).
3. **Hash maps trade space for time (H1–H5):** each problem becomes O(n) time at the cost of O(n) extra memory.
4. **Recursion has a size limit (S2):** quadratic time and `StackOverflowError` at ~50,000 elements, measured above.
5. **Integer overflow:** H2, H3, H5 and S5 use `long` internally for sums and neighbours.
