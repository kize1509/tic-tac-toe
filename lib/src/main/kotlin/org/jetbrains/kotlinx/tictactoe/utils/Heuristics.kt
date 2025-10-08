package org.jetbrains.kotlinx.tictactoe.utils

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.model.enums.opponent
import kotlin.random.Random

    fun evaluate(board: Board, aiMark: Mark): Int {
        return when (val winner = board.getWinner()) {
            aiMark -> 100 * Random.nextInt(1, 12)
            aiMark.opponent() -> -1000 * Random.nextInt(1, 5)
            else -> 0
        }
    }

