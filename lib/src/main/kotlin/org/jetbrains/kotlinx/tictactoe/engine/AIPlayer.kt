package org.jetbrains.kotlinx.tictactoe.engine

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.utils.Minimax

class AIPlayer(private val mark: Mark) {
    private val mx = Minimax()

    fun chooseMove(board: Board, depth: Int): Int {
        var bestScore = Int.MIN_VALUE
        var bestPos = board.availableMoves().first()

        for (pos in board.availableMoves()) {
            val clone = board.clone()
            clone.placeMark(pos, mark)
            val score = mx.minimax(
                board = clone,
                depth = depth - 1,
                maximizing = false,
                alpha = Int.MIN_VALUE,
                beta = Int.MAX_VALUE,
                aiMark = mark
            )
            if (score > bestScore) {
                bestScore = score
                bestPos = pos
            }
        }
        return bestPos
    }
}
