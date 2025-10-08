package org.jetbrains.kotlinx.tictactoe.utils

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.model.enums.opponent

class Minimax {
    private val heur = Heuristics()


    fun minimax(
        board: Board,
        depth: Int,
        maximizing: Boolean,
        alpha: Int,
        beta: Int,
        aiMark: Mark
    ): Int {
        val winner = board.getWinner()
        if (winner != null || board.isFull() || depth == 0) {
            return heur.evaluate(board, aiMark)
        }

        var alphaVar = alpha
        var betaVar = beta

        return if (maximizing) {
            var maxEval = Int.MIN_VALUE
            for (pos in board.availableMoves()) {
                val clone = board.clone().apply { placeMark(pos, aiMark) }
                val eval = minimax(clone, depth - 1, false, alphaVar, betaVar, aiMark)
                maxEval = maxOf(maxEval, eval)
                alphaVar = maxOf(alphaVar, eval)
                if (betaVar <= alphaVar) break
            }
            maxEval
        } else {
            var minEval = Int.MAX_VALUE
            for (pos in board.availableMoves()) {
                val clone = board.clone().apply { placeMark(pos, aiMark.opponent()) }
                val eval = minimax(clone, depth - 1, true, alphaVar, betaVar, aiMark)
                minEval = minOf(minEval, eval)
                betaVar = minOf(betaVar, eval)
                if (betaVar <= alphaVar) break
            }
            minEval
        }
    }

}