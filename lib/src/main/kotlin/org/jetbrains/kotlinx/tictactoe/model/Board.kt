package org.jetbrains.kotlinx.tictactoe.model

class Board {
    private val cells = MutableList(9) { Mark.EMPTY }

    fun getCells(): List<Mark> = cells.toList()

    fun availableMoves(): List<Int> =
        cells.indices.filter { cells[it] == Mark.EMPTY }
}
