package org.jetbrains.kotlinx.tictactoe

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.Mark
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class BoardTest{
    @Test
    fun `Mark should have X O and EMPTY`() {
        val values = Mark.values().toList()
        assertTrue(values.containsAll(listOf(Mark.X, Mark.O, Mark.EMPTY)))
    }
    @Test
    fun `can place mark on empty cell`() {
        val board = Board()
        assertTrue(board.placeMark(0, Mark.X))
        assertEquals(Mark.X, board.getCells()[0])
    }

    @Test
    fun `cannot place mark on occupied cell`() {
        val board = Board()
        board.placeMark(0, Mark.X)
        assertFalse(board.placeMark(0, Mark.O))
    }


    @Test
    fun `board starts empty`() {
        val board = Board()
        assertEquals(List(9) { Mark.EMPTY }, board.getCells())
        assertEquals((0..8).toList(), board.availableMoves())
    }

    @Test
    fun `detects horizontal win`() {
        val board = Board()
        board.placeMark(0, Mark.X)
        board.placeMark(1, Mark.X)
        board.placeMark(2, Mark.X)
        assertEquals(Mark.X, board.getWinner())
    }

    @Test
    fun `detects diagonal win`() {
        val board = Board()
        board.placeMark(0, Mark.O)
        board.placeMark(4, Mark.O)
        board.placeMark(8, Mark.O)
        assertEquals(Mark.O, board.getWinner())
    }

    @Test
    fun `no winner returns null`() {
        val board = Board()
        board.placeMark(0, Mark.X)
        board.placeMark(1, Mark.O)
        board.placeMark(2, Mark.X)
        assertNull(board.getWinner())
    }

    @Test
    fun `detects draw when board full and no winner`() {
        val b = Board()
        val moves = listOf(
            0 to Mark.X, 1 to Mark.O, 2 to Mark.X,
            3 to Mark.X, 4 to Mark.O, 5 to Mark.O,
            6 to Mark.O, 7 to Mark.X, 8 to Mark.X
        )
        moves.forEach { (pos, mark) -> b.placeMark(pos, mark) }
        assertTrue(b.isFull())
        assertNull(b.getWinner())
    }

    @Test
    fun `cloned board is independent copy`() {
        val b1 = Board()
        b1.placeMark(0, Mark.X)
        val b2 = b1.clone()
        b2.placeMark(1, Mark.O)

        assertEquals(Mark.X, b1.getCells()[0])
        assertEquals(Mark.EMPTY, b1.getCells()[1])
        assertEquals(Mark.O, b2.getCells()[1])
    }


}
