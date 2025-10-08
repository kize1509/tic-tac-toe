package org.jetbrains.kotlinx.tictactoe.engine

import org.jetbrains.kotlinx.tictactoe.engine.cache.TTEntry
import org.jetbrains.kotlinx.tictactoe.engine.cache.TTPersistence
import org.jetbrains.kotlinx.tictactoe.engine.cache.Zobrist
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.model.enums.opponent
import org.jetbrains.kotlinx.tictactoe.utils.minimax
import java.io.File


class AIPlayer(
    private val mark: Mark,
    private val zobrist: Zobrist = Zobrist(System.currentTimeMillis()),
    private val ttPath: String = "transposition_table.json"
) {

    private val tt: MutableMap<Long, TTEntry> = mutableMapOf()

    fun chooseMove(board: Board, depth: Int): Int {
        var bestScore = Int.MIN_VALUE
        var bestPos = board.availableMoves().first()

        for (pos in board.availableMoves()) {
            val clone = board.clone()
            clone.placeMark(pos, mark)

            val score = minimax(
                board = clone,
                depth = depth - 1,
                maximizing = false,
                alpha = Int.MIN_VALUE,
                beta = Int.MAX_VALUE,
                aiMark = mark,
                zobrist = zobrist,
                tt = tt,
                currentMark = mark.opponent()
            )

            if (score > bestScore) {
                bestScore = score
                bestPos = pos
            }
        }

        return bestPos
    }

    fun saveCache() {
        TTPersistence.save(tt, ttPath)
    }

    fun resetCache(clearFile: Boolean = false) {
        tt.clear()
        if (clearFile) File(ttPath).delete()
    }
}
