# Potential Improvements

## 1. Transposition Table Memory Growth

**Problem**: The in-memory transposition table can grow unbounded during long gaming sessions, potentially consuming excessive memory.

**Current Mitigation**: The `Game` class provides a `resetCache(clearFile: Boolean)` method that clears the in-memory cache and optionally deletes the persistence file.

**Proposed Improvements**:
- Implement a maximum cache size limit with LRU (Least Recently Used) eviction policy
- Add automatic cache cleanup based on entry age or access frequency
- Provide configurable cache size limits in `GameConfig`
- Add cache statistics (hit rate, size, memory usage) for monitoring

## 2. Heuristic Evaluation Function

**Current Implementation**: The `evaluate()` function in `Heuristics.kt` uses random multiplication factors for win/loss scores to reduce positional bias:
```kotlin
fun evaluate(board: Board, aiMark: Mark): Int {
    return when (val winner = board.getWinner()) {
        aiMark -> 100 * Random.nextInt(1, 12)
        aiMark.opponent() -> -1000 * Random.nextInt(1, 5)
        else -> 0
    }
}
```

**Problems**:
- Random factors make the AI non-deterministic and harder to test
- No evaluation of non-terminal positions (potential forks, center control, corners)

**Proposed Improvements**:
- Add positional heuristics for non-terminal states (center = +3, corners = +2, edges = +1)
- Implement threat detection (two-in-a-row opportunities)
- Add fork detection (positions that create multiple winning threats)

## 3. Library Encapsulation

**Current Issues**:
- `AIPlayer.attachCache()` exposes internal cache management to external callers
- Direct manipulation of transposition table is possible through shared mutable map
- Internal fields like `tt` in `AIPlayer` can be accessed via reflection (as seen in tests)
