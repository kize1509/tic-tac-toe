# Tic-Tac-Toe Library

A highly optimized Tic-Tac-Toe game engine written in Kotlin, featuring an unbeatable AI powered by the minimax algorithm with advanced optimizations.

## Features

- **AI** using minimax algorithm with alpha-beta pruning
- **Optimizations** with Zobrist hashing and transposition table caching
- **UI-Agnostic Architecture** - integrate into any interface (CLI, GUI, Web, Mobile)
- **Adaptive Difficulty** - AI adjusts search depth based on game phase
- **Persistent Learning** - AI caches evaluated positions across games
- **Clean API** - Simple integration with comprehensive examples

## Project Structure

```
tic-tac-toe/
├── lib/                          # Core game engine library
│   └── src/
│       ├── main/kotlin/org/jetbrains/kotlinx/tictactoe/
│       │   ├── model/            # Data models
│       │   │   ├── Board.kt      # Game board management
│       │   │   ├── GameConfig.kt # Configuration settings
│       │   │   ├── GameState.kt  # Phase tracking (START/MID/FINISH)
│       │   │   └── enums/        # Mark (X/O/EMPTY), GamePhase
│       │   ├── engine/           # Game logic
│       │   │   ├── Game.kt       # Main game controller
│       │   │   ├── AIPlayer.kt   # AI decision engine
│       │   │   └── cache/        # Caching system
│       │   │       ├── Zobrist.kt       # Position hashing
│       │   │       ├── TTEntry.kt       # Cache entry
│       │   │       └── TTPersistence.kt # Cache persistence
│       │   └── utils/            # Algorithms
│       │       ├── Minimax.kt    # Core minimax implementation
│       │       └── Heuristics.kt # Position evaluation
│       └── test/                 # Comprehensive test suite
├── app/                          # CLI demo application
│   └── src/main/kotlin/
│       └── main.kt               # CLI integration example
├── TicTacToe.ipynb              # Interactive Kotlin notebook with examples
└── build.gradle.kts             # Build configuration
```

## Quick Start

### Prerequisites

- **JDK 17+** (project uses JDK 24)
- **Gradle 9.0+** (included via wrapper)

### Build the Project

```bash
# Clone the repository
git clone git@github.com:kize1509/tic-tac-toe.git
cd tic-tac-toe

# Build the library and application
./gradlew build

# Run tests
./gradlew :lib:test
```

### Run the CLI Application

- Make sure that lib build is done.

```bash
./gradlew :app:run
```

### Explore the Interactive Notebook

Open `TicTacToe.ipynb` in IntelliJ IDEA with the Kotlin Notebook plugin.


## Architecture & Data Flow

### Library Architecture

The library follows a clean separation of concerns:

```
┌─────────────────────────────────────────┐
│           Your Application              │
│       (CLI, GUI, Web, Mobile)           │
└─────────────────┬───────────────────────┘
                  │
                  ▼
┌─────────────────────────────────────────┐
│           Game Controller               │
│  - Turn management                      │
│  - Move validation                      │
│  - AI orchestration                     │
└─────────────────┬───────────────────────┘
                  │
         ┌────────┴────────┐
         ▼                 ▼
┌─────────────────┐  ┌──────────────┐
│     Board       │  │   AIPlayer   │
│  - State        │  │  - Minimax   │
│  - Win check    │  │  - Pruning   │
│  - Validation   │  │  - Caching   │
└─────────────────┘  └──────┬───────┘
                            │
                     ┌──────┴──────┐
                     ▼             ▼
              ┌───────────┐  ┌──────────┐
              │  Zobrist  │  │    TT    │
              │  Hashing  │  │  Cache   │
              └───────────┘  └──────────┘
```

### Data Flow

1. **User Input** → `Game.makeMove(position)`
2. **Validation** → `Board.placeMark()` validates and updates state
3. **Turn Switch** → `currentMark` toggles to opponent
4. **AI Turn** (if applicable):
   - `Game.autoMove()` → determines search depth based on phase
   - `AIPlayer.chooseMove()` → evaluates all possible moves
   - For each move: `minimax()` explores game tree recursively
   - **Zobrist hash** computed for each position
   - **Transposition table** checked for cached evaluations
   - **Alpha-beta pruning** eliminates inferior branches
   - Returns best move
5. **Game State Update** → Board updated, winner checked

## Algorithms

### Minimax Algorithm

The AI uses the **minimax algorithm** to guarantee optimal play:

- **Recursion**: Explores all possible future game states
- **Evaluation**: Leaf nodes (win/loss) are scored
- **Backpropagation**: Scores bubble up to current position
- **Decision**: AI chooses the move with maximum guaranteed outcome


### Alpha-Beta Pruning

Optimization that eliminates ~50% of nodes:

```
α (alpha): Best score maximizer can guarantee
β (beta):  Best score minimizer can guarantee
Prune when: β ≤ α (branch can't improve result)
```

### Zobrist Hashing

Fast position comparison using XOR operations:

```kotlin
// Each (position, mark) pair has a random 64-bit number
// Board hash = XOR of all occupied positions
hash = table[pos1][X] ⊕ table[pos4][O] ⊕ table[pos8][X] ⊕ ...
```

**Benefits**:
- O(1) position lookup in transposition table
- Incremental updates (XOR is reversible)
- Collision-resistant (64-bit space)

### Transposition Table

Cache of previously evaluated positions:

```kotlin
data class TTEntry(val score: Int, val depth: Int)
tt: Map<Long, TTEntry>  // Zobrist hash → evaluation
```

**Caching Strategy**:
- Store position score and depth
- Only reuse if cached depth ≥ current depth
- Persist to disk between games
- Share between AI instances

### Adaptive Depth

AI adjusts search depth by game phase:

| Phase  | Depth | Rationale                    |
|--------|-------|------------------------------|
| START  | configurable | Many options, less critical  |
| MID    | configurable | Balanced exploration         |
| FINISH | configurable | Few options, perfect play    |

## Integrating into Your UI

The library is **UI-agnostic** by design. You interact purely through the `Game` API:

### Integration Pattern

```kotlin
class YourGameUI {
    private val game = Game(
        playerX = "Player",
        playerO = "AI",
        aiPlayers = mapOf(Mark.O to AIPlayer(Mark.O)),
        cnfg = GameConfig()
    )
    
    fun onCellClick(position: Int) {
        // 1. Handle human move
        if (!game.makeMove(position)) {
            showError("Invalid move")
            return
        }
        updateUI()
        
        // 2. Check if game ended
        if (game.isOver) {
            showResult(game.resultMessage())
            return
        }
        
        // 3. Handle AI move (if applicable)
        if (game.hasAI()) {
            val aiMove = game.autoMove()
            updateUI()
            
            if (game.isOver) {
                showResult(game.resultMessage())
            }
        }
    }
    
    private fun updateUI() {
        // Get board state
        val cells = game.getRawBoard()  // List<Mark>
        
        // Update your UI cells
        cells.forEachIndexed { index, mark ->
            when (mark) {
                Mark.X -> drawX(index)
                Mark.O -> drawO(index)
                Mark.EMPTY -> drawEmpty(index)
            }
        }
    }
}
```

### Key Integration Points

| Method | Purpose |
|--------|---------|
| `game.makeMove(pos)` | Make a move, returns success |
| `game.autoMove()` | Get AI move (if current player is AI) |
| `game.getRawBoard()` | Get board state as `List<Mark>` |
| `game.displayBoard()` | Get formatted ASCII board (for debug) |
| `game.isOver` | Check if game ended |
| `game.winner` | Get winner (`Mark.X`, `Mark.O`, or `null`) |
| `game.currentMark` | Get current player's mark |
| `game.hasAI()` | Check if current player is AI |
| `game.reset()` | Start new game |


## CLI Application Example (script)

The `app/` module demonstrates a complete integration:

**Key Features**:
- Player name input
- AI opponent selection
- Move validation
- Board display
- Win/draw detection
- Persistent transposition table

**Run it**:
- Make sure that lib build is done.
```bash 
./gradlew :app:run
```

**Code**: See [`app/src/main/kotlin/org/jetbrains/kotlinx/tictactoe/main.kt`](app/src/main/kotlin/org/jetbrains/kotlinx/tictactoe/main.kt)

## Testing

Comprehensive test suite covering:

- **Board mechanics** (placement, validation, win detection)
- **Game flow** (turns, winner detection, reset)
- **AI behavior** (winning moves, blocking, valid moves)
- **Zobrist hashing** (consistency, collision resistance)
- **Transposition table** (caching, persistence, loading)
- **Integration tests** (AI vs AI with full caching)

```bash
# Run all tests
./gradlew :lib:test

```


### Algorithm Deep Dive

Minimax implementation:

1. Read [`lib/src/main/kotlin/org/jetbrains/kotlinx/tictactoe/utils/Minimax.kt`](lib/src/main/kotlin/org/jetbrains/kotlinx/tictactoe/utils/Minimax.kt)
2. Explore [`lib/src/main/kotlin/org/jetbrains/kotlinx/tictactoe/engine/AIPlayer.kt`](lib/src/main/kotlin/org/jetbrains/kotlinx/tictactoe/engine/AIPlayer.kt)
3. Study the test cases in [`lib/src/test/`](lib/src/test/)

## Configuration Options

```kotlin
data class GameConfig(
    val zobristSeed: Long = System.currentTimeMillis(),
    val aiDepthStart: Int = 10,   // Opening depth
    val aiDepthMid: Int = 15,      // Midgame depth
    val aiDepthFinish: Int = 20    // Endgame depth
)
```

**Depth Guidelines**:
- **Fast mode**: 6, 8, 10 (near instant, still strong)
- **Balanced**: 10, 15, 20 (default, optimal play)
- **Perfect**: 15, 20, 25 (guaranteed optimal, slower)

## Performance

With transposition table caching:

- **First move** (empty board): ~1500 positions evaluated, ~1ms
- **Endgame**: Near-instant (all positions cached)

Cache persistence enables instant decisions across game sessions.



# Github actions
- Feturing [ci.yml](./.github/workflows/ci.yml) : build and test on each `main` or `develop` push. 

