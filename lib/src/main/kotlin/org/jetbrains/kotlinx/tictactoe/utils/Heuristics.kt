package org.jetbrains.kotlinx.tictactoe.utils

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.model.enums.opponent

class Heuristics {

    fun evaluate(board: Board, aiMark: Mark): Int {
        return when (val winner = board.getWinner()) {
            aiMark -> 10
            aiMark.opponent() -> -10
            else -> 0
        }
    }

}