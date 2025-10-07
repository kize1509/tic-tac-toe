package org.jetbrains.kotlinx.tictactoe.engine

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.Mark
import org.jetbrains.kotlinx.tictactoe.model.opponent

class Game(val playerX: String, val playerO: String) {
    val board = Board()
    var currentMark = Mark.X
    val currentPlayerName: String
        get() = if (currentMark == Mark.X) playerX else playerO

    val winner: Mark?
        get() = board.getWinner()

    val isOver: Boolean
        get() = winner != null || board.isFull()

    fun makeMove(pos: Int): Boolean {
        val success = board.placeMark(pos, currentMark)
        if (success) currentMark = currentMark.opponent()
        return success
    }

    fun reset() {
        board.clear()
        currentMark = Mark.X
    }


    fun displayBoard(): String {
        val cells = board.getCells()
        return cells.chunked(3).joinToString("\n") { row ->
            row.joinToString(" | ") { it.name }
        }
    }

    fun resultMessage(): String =
        when {
            winner != null -> "$winner wins!"
            board.isFull() -> "Draw!"
            else -> "Game in progress"
        }
}
