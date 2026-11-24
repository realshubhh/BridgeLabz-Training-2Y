# Problem 3 – N-Queens Optimization and Variants

**Code:** `NQueens.java` (compile: `javac NQueens.java` · run: `java NQueens`) – includes basic, optimized, first-solution, forbidden-squares and bitmask versions, all cross-checked against the known solution counts (N=1…10).

---

## (a) Search-space sizes for 8×8

| Approach | Size | Calculation |
|----------|------|-------------|
| Pure brute force – any 8 of the 64 squares | **4,426,165,368** | C(64, 8) |
| Brute force, lecture definition (N^N) | **16,777,216** | 8⁸ |
| "One queen per row" + distinct columns (lecture's N!) | **40,320** | 8! |
| **Backtracking – actually explored** | **2,057 recursive calls** | measured with the program |

> Note: strictly, "one queen per row" alone gives 8⁸ placements; the lecture's N! figure appears once columns are also forced to be distinct (permutations). Both are listed so either convention can be quoted.

**Estimate of what backtracking explores – reasoning:**
- At each row only *safe* columns are tried, so whole sub-trees are cut when a queen is placed badly
- Number of nodes at each depth (measured): `1, 8, 42, 140, 344, 568, 550, 312, 92` → **total 2,057**
  (the last number, 92, = number of complete solutions)
- 2,057 is only **≈ 5.1 %** of 8! and **≈ 0.012 %** of 8⁸ → backtracking examines a tiny fraction of the space
- The tree is widest around depth 5–6 and then shrinks because few partial boards can still be completed

---

## (b) Optimized solution – 1-D array + diagonal tracking

**Representation**
- `board[row] = col` → 1-D array of size N (queen of each row) – replaces the N×N matrix
- `cols[c]` – is column `c` occupied?
- `diag1[row + col]` – "/" diagonal occupied? (row + col is constant along a "/" diagonal)
- `diag2[row − col + N − 1]` – "\" diagonal occupied? (row − col is constant; `+N−1` keeps the index ≥ 0)
- `diag1` and `diag2` have size **2N − 1**

```
function solve(row):
    if row == N:
        record board; return

    for col in 0 .. N-1:
        d1 = row + col
        d2 = row - col + N - 1
        if cols[col] or diag1[d1] or diag2[d2]:      // O(1) safety test
            continue

        board[row] = col                              // make choice
        cols[col] = diag1[d1] = diag2[d2] = true

        solve(row + 1)                                // recurse

        cols[col] = diag1[d1] = diag2[d2] = false     // undo choice
        board[row] = -1
```

**Improvements over the basic version**

| | Basic (2-D board) | Optimized |
|---|-------------------|-----------|
| `isSafe` cost | O(N) – scans column + 2 diagonals | **O(1)** – 3 array look-ups |
| Memory | O(N²) board | **O(N)** arrays |
| Tree explored | 2,057 calls (N=8) | same 2,057 calls, but each check is N× cheaper |
| Total time | roughly O(N · N!) worst-case work | **O(N!)** worst-case, much smaller in practice |
| Output | N×N matrices | compact array e.g. `[1,3,0,2]` |
| Further speed-up | – | **bitmasks** (`bitmaskCount`) test all columns of a row in one operation |

---

## (c) Trace for N = 4 (optimized)

`d1` and `d2` have size 2N−1 = 7; `T` = occupied, `.` = free. Output of `traceOptimized(4)`:

| Step | Action | Row,Col | `board` | `cols` | `diag1` (r+c) | `diag2` (r−c+3) | Comment |
|------|--------|---------|---------|--------|---------------|-----------------|---------|
| 1 | PLACE | 0,0 | [0,−,−,−] | T... | T...... | ...T... | |
| 2 | PLACE | 1,2 | [0,2,−,−] | T.T. | T..T... | ..TT... | (1,0),(1,1) unsafe |
| – | – | row 2 | | | | | **no safe column → dead end** |
| 3 | **BACKTRACK** | 1,2 | [0,−,−,−] | T... | T...... | ...T... | undo queen of row 1 |
| 4 | PLACE | 1,3 | [0,3,−,−] | T..T | T...T.. | .T.T... | |
| 5 | PLACE | 2,1 | [0,3,1,−] | TT.T | T..TT.. | .T.TT.. | |
| – | – | row 3 | | | | | **no safe column → dead end** |
| 6 | **BACKTRACK** | 2,1 | [0,3,−,−] | T..T | T...T.. | .T.T... | no other column for row 2 |
| 7 | **BACKTRACK** | 1,3 | [0,−,−,−] | T... | T...... | ...T... | row 1 exhausted |
| 8 | **BACKTRACK** | 0,0 | [−,−,−,−] | .... | ....... | ....... | queen at col 0 impossible |
| 9 | PLACE | 0,1 | [1,−,−,−] | .T.. | .T..... | ..T.... | |
| 10 | PLACE | 1,3 | [1,3,−,−] | .T.T | .T..T.. | .TT.... | |
| 11 | PLACE | 2,0 | [1,3,0,−] | TT.T | .TT.T.. | .TT..T. | |
| 12 | PLACE | 3,2 | [1,3,0,2] | TTTT | .TT.TT. | .TT.TT. | **row == N → SOLUTION** |

```
. Q . .
. . . Q
Q . . .
. . Q .
```
- **Backtracking occurs 4 times** (steps 3, 6, 7, 8) – at all of them the diagonal/column flags are reset to exactly their earlier values
- First valid solution: `[1, 3, 0, 2]`. The other solution of 4-Queens is `[2, 0, 3, 1]` (its mirror image)

---

## (d) Finding just ONE solution quickly

**Modifications to the standard algorithm**
1. Make `solve()` **return a boolean**; when `row == N` return `true`, and each caller does
   `if solve(row+1): return true` → the whole recursion stops at once (implemented in `firstSolution()`)
2. **Do not undo** the final successful path (no need to restore state)
3. **Do not store** all solutions / copy boards – keep only the one `board` array
4. Use the O(1) array/bitmask safety check

| N | calls to first solution | calls to enumerate all |
|---|------------------------|------------------------|
| 4 | 9 | – |
| 8 | **114** | 2,057 |
| 12 | 262 | – |

**Further speed-ups (if only one answer is needed):**
- **Constructive formula:** for N ≥ 4 explicit patterns place the queens in O(N) with no search at all (e.g. the "knight-move" pattern: even rows/columns first, then odd, with a small fix-up depending on N mod 6)
- **Heuristics:** try columns starting from the middle, or pick the row with the fewest safe columns first (MRV); **min-conflicts** local search solves N = 1,000,000 in practice
- (For *all* solutions one can instead use symmetry: solve only the left half of row 0 and mirror → ≈ 2× faster)

---

## (e) N-Queens with Forbidden Squares

**Modification** – one extra constraint inside the column loop (`withForbidden()`):

```
forbidden = set of (row, col) pairs            // O(1) lookup (hash set / boolean matrix)

for col in 0 .. N-1:
    if (row, col) in forbidden:   continue     // NEW
    if cols[col] or diag1[row+col] or diag2[row-col+N-1]:  continue
    ... place, recurse, undo ...
```

**Impact on complexity**
- **Worst-case time is still O(N!)** (the forbidden set can only *remove* branches, never add any) and space stays O(N + |forbidden|)
- Each extra check is O(1), so per-node cost does not change
- **Average / practical time improves** because forbidden squares prune extra branches: for N = 6 with 4 forbidden squares the calls drop from **153 → 89** and the solutions from 4 → 3
- A solution may **not exist** (e.g. a whole row forbidden → returns instantly with 0 solutions after 1 call); a quick pre-check "does every row have at least one allowed square?" (O(N²)) detects the simplest impossible cases
- The **mirror-symmetry trick** (solving half of row 0 only) is **no longer valid**, unless the forbidden set is itself symmetric
- Using the variant "place N queens" still means exactly one queen per row; if rows could be skipped the search space would grow (choose a row or skip it) and the problem changes

**Test run (N=6, forbidden = (0,1),(1,3),(2,5),(3,0))** – first solution `[2, 5, 1, 4, 0, 3]`:
```
. . Q . . .
. . . . . Q
. Q . . . .
. . . . Q .
Q . . . . .
. . . Q . .
```
