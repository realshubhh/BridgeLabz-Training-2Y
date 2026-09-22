# Smart Library Management System — DSA Practical Assignment

A single-file Java solution implementing all 5 required tasks on an
in-memory array of `Book` records, exactly matching the assignment's
method signatures and sample test case.

## Files

| File                                | Purpose                                   |
|--------------------------------------|--------------------------------------------|
| `LibraryManagementSystem.java`       | Complete solution (Book class, 5 methods, demo `main`) |
| `EdgeCaseTests.java`                 | Extra tests for edge cases (no duplicates, no matches, target unreachable, etc.) |
| `README.md`                          | This file                                 |

## How to compile & run

```bash
javac LibraryManagementSystem.java
java LibraryManagementSystem
```

To run the edge-case tests:

```bash
javac EdgeCaseTests.java
java EdgeCaseTests
```

## Task-by-task explanation

### Task 1 — `removeDuplicates(Book[] books, int n)`
Two-pointer in-place technique (classic "remove duplicates from sorted
array"). Pointer `i` marks the last confirmed-unique element; pointer
`j` scans forward. Whenever a new `bookId` is found, it's copied into
slot `i+1`. No extra array is allocated.
- **Time:** O(N) &nbsp;&nbsp; **Space:** O(1)

### Task 2 — `searchByTitle(Book[] books, int count, String query)`
Linear scan over the array, comparing `title.toLowerCase()` against
`query.toLowerCase()` using `String.contains()` so a partial,
case-insensitive match is enough (e.g. `"java"` matches
`"Java Programming"` and `"ADVANCED JAVA"`).
- **Time:** O(N)

### Task 3 — `sortByPrice(Book[] books, int count)`
Standard **selection sort**: for each position `i`, find the index of
the minimum-price element in the unsorted remainder and swap it into
place. A swap is counted only when `minIndex != i` (i.e. an element
actually moved).
- **Time:** O(N²) &nbsp;&nbsp; **Space:** O(1)

### Task 4 — `searchByPrice(Book[] books, int count, double targetPrice)`
**Binary search** over the array, which must already be sorted by
price (Task 3 runs first). Uses `Double.compare()` for a safe exact
match check and returns the index, or `-1` if absent.
- **Time:** O(log N)

### Task 5 — `minBooksForTargetCost(Book[] books, int count, double targetCost)`
**Sliding window** technique. Expand the window with a `right`
pointer, adding each book's price to `currentSum`. As soon as
`currentSum >= targetCost`, record the window length and shrink from
the `left` to look for a smaller valid window. Returns `0` if no
window ever reaches the target.
- **Time:** O(N) &nbsp;&nbsp; **Space:** O(1)

> Note: because Task 5 is demonstrated *after* Task 3/4 in `main()`,
> it runs on the price-sorted array — matching the assignment's
> sample output (window = Database Systems + Python Guide = Rs. 1100).
> If your instructor wants Task 5 to run on the *original* (unsorted)
> order instead, simply call it before `sortByPrice()` — the method
> itself is order-agnostic and works on whatever order the array is in.

## Sample output

Running `java LibraryManagementSystem` reproduces the assignment's
expected output exactly:

```
1. After Task 1 (Remove Duplicates):
Unique Books Count: 5
...
Total Swaps: 2
...
Result: Book found at index 2: [104] Database Systems - Rs. 500.0
...
Minimum Consecutive Books Needed: 2
```

## Complexity summary

| Task | Method                   | Time      | Space |
|------|---------------------------|-----------|-------|
| 1    | `removeDuplicates`        | O(N)      | O(1)  |
| 2    | `searchByTitle`           | O(N)      | O(1)  |
| 3    | `sortByPrice`              | O(N²)     | O(1)  |
| 4    | `searchByPrice`            | O(log N)  | O(1)  |
| 5    | `minBooksForTargetCost`    | O(N)      | O(1)  |
