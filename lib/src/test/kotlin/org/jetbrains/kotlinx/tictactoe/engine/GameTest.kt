package org.jetbrains.kotlinx.tictactoe.engine

import junit.framework.TestCase
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {

    @Test
    fun `game initializes with two players and X starts`() {
        val game = Game("Alice", "Bob")
        assertEquals("Alice", game.currentPlayerName)
        assertEquals(Mark.X, game.currentMark)
    }

    @Test
    fun `making a move switches turns`() {
        val game = Game("Alice", "Bob")
        game.makeMove(0)
        assertEquals(Mark.O, game.currentMark)
        assertEquals("Bob", game.currentPlayerName)
    }

    @Test
    fun `game detects a winner`() {
        val game = Game("Alice", "Bob")
        game.makeMove(0)
        game.makeMove(3)
        game.makeMove(1)
        game.makeMove(4)
        game.makeMove(2)
        TestCase.assertTrue(game.isOver)
        assertEquals(Mark.X, game.winner)
    }

    @Test
    fun `invalid move does not switch turn`() {
        val game = Game("Alice", "Bob")
        game.makeMove(0)
        val result = game.makeMove(0) // already occupied
        TestCase.assertFalse(result)
        assertEquals(Mark.O, game.currentMark) // turn not switched
    }

    @Test
    fun `game can reset`() {
        val game = Game("Alice", "Bob")
        game.makeMove(0)
        game.reset()
        assertEquals(Mark.X, game.currentMark)
        TestCase.assertTrue(game.board.getCells().all { it == Mark.EMPTY })
    }

}