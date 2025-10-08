package org.jetbrains.kotlinx.tictactoe.engine

import org.jetbrains.kotlinx.tictactoe.engine.cache.TTPersistence
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.GameConfig
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.model.enums.opponent
import org.jetbrains.kotlinx.tictactoe.model.GameState
import org.jetbrains.kotlinx.tictactoe.model.enums.GamePhase

class Game(
    val playerX: String,
    val playerO: String,
    private val aiPlayers: Map<Mark, AIPlayer> = emptyMap(),
    private val ttPath: String? = "lib/tt_cache.json",
    private val cnfg: GameConfig = GameConfig()
) {
    val board = Board()
    val state = GameState(1)
    var currentMark = Mark.X

    val currentPlayerName: String
        get() = if (currentMark == Mark.X) playerX else playerO

    val winner: Mark?
        get() = board.getWinner()

    val isOver: Boolean
        get() = winner != null || board.isFull()

    private val sharedTT: MutableMap<Long, org.jetbrains.kotlinx.tictactoe.engine.cache.TTEntry> =
        if (ttPath != null && java.io.File(ttPath).exists())
            TTPersistence.load(ttPath).toMutableMap()
        else mutableMapOf()

    init {
        aiPlayers.values.forEach { it.attachCache(sharedTT) }
    }

    fun makeMove(pos: Int): Boolean {
        val success = board.placeMark(pos, currentMark)
        if (success) {
            state.addMove()
            currentMark = currentMark.opponent()
        }
        return success
    }

    fun autoMove(): Int {
        val ai = aiPlayers[currentMark] ?: return -1
        val depth = getDepthForCurrentPhase()
        val move = ai.chooseMove(board, depth)
        makeMove(move)
        return move
    }

    fun getDepthForCurrentPhase(): Int =
        when (state.getStage()) {
            GamePhase.START -> cnfg?.aiDepthStart ?: 10
            GamePhase.MID -> cnfg?.aiDepthMid ?: 12
            GamePhase.FINISH -> cnfg?.aiDepthFinish ?: 20
        }

    fun reset() {
        board.clear()
        currentMark = Mark.X
        state.resetState()
    }

    fun displayBoard(): String {
        val cells = board.getCells()
        val cellStrings = cells.map {
            when (it) {
                Mark.EMPTY -> "   "
                Mark.X -> " X "
                Mark.O -> " O "
            }
        }

        val divider = "---+---+---"
        return buildString {
            appendLine()
            for (i in cells.indices step 3) {
                append(cellStrings[i])
                append("|")
                append(cellStrings[i + 1])
                append("|")
                append(cellStrings[i + 2])
                appendLine()
                if (i < 6) appendLine(divider)
            }
        }
    }


    fun resultMessage(): String =
        when {
            winner != null -> "$winner wins!"
            board.isFull() -> "Draw!"
            else -> "Game in progress"
        }

    fun hasAI(): Boolean = aiPlayers.containsKey(currentMark)

    fun saveCache() {
        ttPath?.let { path ->
            TTPersistence.save(sharedTT, path)
        }
    }

    fun resetCache(clearFile: Boolean = false) {
        sharedTT.clear()
        if (clearFile && ttPath != null) java.io.File(ttPath).delete()
    }

    fun end(){
        saveCache()
    }
}
