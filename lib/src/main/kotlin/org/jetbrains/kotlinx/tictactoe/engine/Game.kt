package org.jetbrains.kotlinx.tictactoe.engine

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.Mark

class Game(val playerX: String, val playerO: String) {
    val board = Board()
    var currentMark = Mark.X
    val currentPlayerName: String
        get() = if (currentMark == Mark.X) playerX else playerO
}
