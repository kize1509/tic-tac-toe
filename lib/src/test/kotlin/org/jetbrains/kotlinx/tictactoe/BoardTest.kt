package org.jetbrains.kotlinx.tictactoe

import junit.framework.TestCase.assertTrue
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.Mark
import kotlin.test.Test
import kotlin.test.assertEquals


class BoardTest{
    @Test
    fun `Mark should have X O and EMPTY`() {
        val values = Mark.values().toList()
        assertTrue(values.containsAll(listOf(Mark.X, Mark.O, Mark.EMPTY)))
    }


    @Test
    fun `board starts empty`() {
        val board = Board()
        assertEquals(List(9) { Mark.EMPTY }, board.getCells())
        assertEquals((0..8).toList(), board.availableMoves())
    }

}
