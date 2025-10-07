package org.jetbrains.kotlinx.tictactoe.engine

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.model.enums.opponent
import org.jetbrains.kotlinx.tictactoe.model.GameState
import org.jetbrains.kotlinx.tictactoe.model.enums.switch

class Game(val playerX: String, val playerO: String) {
    val board = Board()
    val state = GameState()
    var stage = state.getStage()
    var currentMark = Mark.X
    val currentPlayerName: String
        get() = if (currentMark == Mark.X) playerX else playerO

    val winner: Mark?
        get() = board.getWinner()

    val isOver: Boolean
        get() = winner != null || board.isFull()

    fun makeMove(pos: Int): Boolean {
        val success = board.placeMark(pos, currentMark)
        if (success) {
            currentMark = currentMark.opponent()
            state.addMove()
            stage = state.getStage()
        }
        return success
    }

    fun reset() {
        board.clear()
        currentMark = Mark.X
        stage = state.getStage().switch()
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
