package org.jetbrains.kotlinx.tictactoe.utils

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.test.Test
import kotlin.test.assertEquals

class HeuristicsTest {

    @Test
    fun `evaluate returns 0 for draw`() {
        val board3 = Board()

        val moves = listOf(
            0 to Mark.X, 1 to Mark.O, 2 to Mark.X,
            3 to Mark.X, 4 to Mark.O, 5 to Mark.O,
            6 to Mark.O, 7 to Mark.X, 8 to Mark.X
        )
        moves.forEach { (pos, mark) -> board3.placeMark(pos, mark) }
        assertEquals(0, evaluate(board3, Mark.X))
    }

}