# Problem 2 – Backtracking Template Application (Word Puzzle)

**Code:** `WordSearch.java` (compile: `javac WordSearch.java` · run: `java WordSearch`)

```
Grid (row, col from 0):      Target word: "DREAM"
    0 1 2 3
 0  C A T S
 1  O R E A
 2  D E A M
 3  E L L S
```
Moves: horizontal, vertical **and diagonal** (8 directions); each cell used **at most once** per word.

---

## (a) Backtracking algorithm

**Backtracking framework:**

| Component | In this problem |
|-----------|-----------------|
| **State** | current cell `(r, c)`, index `i` of the letter we must match, `visited[][]` matrix, current `path` |
| **Choices** | the 8 neighbouring cells `(r+dr, c+dc)` |
| **Constraints** | inside the grid · not visited yet · `grid[r][c] == word[i]` |
| **Goal** | last letter (`i == L-1`) matched → word formed |
| **Make choice** | `visited[r][c] = true`, push `(r,c)` to path |
| **Undo choice** | `visited[r][c] = false`, pop from path |

```
function exist(grid, word):
    for each cell (r, c) in grid:
        if dfs(r, c, 0): return true
    return false

function dfs(r, c, i):
    // constraints (prune)
    if r,c outside grid:                return false
    if visited[r][c]:                   return false
    if grid[r][c] != word[i]:           return false

    // make choice
    visited[r][c] = true

    // goal
    if i == length(word) - 1:           return true

    // explore all 8 neighbours
    for (dr, dc) in 8 directions:
        if dfs(r+dr, c+dc, i+1):        return true

    // undo choice (backtrack)
    visited[r][c] = false
    return false
```

---

## (b) Decision tree for "DREAM"

- Only **one** `D` exists → (2,0). The other 15 cells fail at depth 0 (letter ≠ 'D') and are pruned immediately.
- `✓` = successful path, `✗` = dead end → backtrack

```
START (2,0) D
  └── (1,1) R            ← only 'R' in the grid, adjacent to D (diagonal)
        ├── (1,2) E
        │     ├── (0,1) A  ✗  no unused 'M' neighbour  → BACKTRACK
        │     ├── (1,3) A
        │     │     └── (2,3) M  ✓  PATH 1
        │     └── (2,2) A
        │           └── (2,3) M  ✓  PATH 2
        └── (2,1) E
              └── (2,2) A
                    └── (2,3) M  ✓  PATH 3
```

**Successful paths (3 in total):**

| Path | Cells | Grid view |
|------|-------|-----------|
| 1 | (2,0)→(1,1)→(1,2)→(1,3)→(2,3) | D ↗ R → E → A ↓ M |
| 2 | (2,0)→(1,1)→(1,2)→(2,2)→(2,3) | D ↗ R → E ↓ A → M |
| 3 | (2,0)→(1,1)→(2,1)→(2,2)→(2,3) | D ↗ R ↓ E → A → M |

**Backtracking point:** `A` at (0,1) – its neighbours are C, T, O, R, E; none is an unused `M`.

---

## (c) Execution trace (program output, stops at the first path)

```
--- start candidate (2,0) 'D' ---
CHOOSE  (2,0) = 'D'  matches word[0]   path=[(2,0)]
  CHOOSE  (1,1) = 'R'  matches word[1]   path=[(2,0),(1,1)]
    CHOOSE  (1,2) = 'E'  matches word[2]   path=[(2,0),(1,1),(1,2)]
      CHOOSE  (0,1) = 'A'  matches word[3]   path=[(2,0),(1,1),(1,2),(0,1)]
      UNDO    (0,1) = 'A'  dead end -> backtrack   path=[(2,0),(1,1),(1,2)]
      CHOOSE  (1,3) = 'A'  matches word[3]   path=[(2,0),(1,1),(1,2),(1,3)]
        CHOOSE  (2,3) = 'M'  matches word[4]   path=[(2,0),(1,1),(1,2),(1,3),(2,3)]
        GOAL reached, path = [(2,0),(1,1),(1,2),(1,3),(2,3)]
```

**How state is restored:**
- On **CHOOSE**: `visited[r][c] = true` and the cell is appended to `path`
- On **UNDO**: `visited[r][c] = false` and the cell is popped, so the cell becomes available for other paths
- After the UNDO of (0,1) the state is exactly what it was before (0,1) was tried: `path = [(2,0),(1,1),(1,2)]`

---

## (d) Worst-case time complexity

Let the grid be **N × M** and the word length **L**.

- Start cells: **N·M**
- First step: up to **8** neighbours; every later step: at most **7** (one neighbour is the cell we just came from, already visited)
- Depth of recursion: **L**
- **Time = O(N · M · 8 · 7^(L−1)) ≈ O(N · M · 8^L)**
- **Space = O(L)** recursion stack + O(N·M) for `visited` (can be reduced to O(1) extra by temporarily overwriting the cell with `#`)
- In practice far smaller because the letter-match constraint **prunes** almost every branch (here: 1 start cell out of 16 survives, and the full tree enters only 11 cells in total)
- Brute force (try every ordered L-cell sequence) would be roughly (N·M)^L = 16^5 = 1 048 576 sequences

---

## (e) Finding ALL ways to form a word

**Modifications:**
1. **Do not return when the goal is reached** – record the path and *keep searching*
2. **Store a copy** of `path` (not a reference, because `path` is modified later)
3. Return nothing (void) instead of true/false; `exist` loop must try *all* start cells

**Additional data structure:** a list of paths – `all_paths = []` (list of lists of `(r, c)`).
(For many words, e.g. a Boggle solver, use a **Trie** or a hash set of the dictionary so all words are searched in one pass and prefixes can be pruned.)

```
function findAll(r, c, i):
    if outside grid or visited[r][c] or grid[r][c] != word[i]: return
    visited[r][c] = true;  path.push((r,c))
    if i == L-1:
        all_paths.append(copy of path)       // record, do NOT stop
    else:
        for each of 8 directions: findAll(r+dr, c+dc, i+1)
    visited[r][c] = false; path.pop()        // backtrack
```

**Result for "DREAM": 3 ways** (the three paths in (b)). Complexity stays O(N·M·8^L); only the number of solutions found changes.
