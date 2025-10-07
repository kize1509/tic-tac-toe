package org.jetbrains.kotlinx.tictactoe
import org.jetbrains.kotlinx.tictactoe.engine.Game
import org.jetbrains.kotlinx.tictactoe.model.Mark
import kotlin.test.Test
import kotlin.test.assertEquals

class GameTest {

    @Test
    fun `game initializes with two players and X starts`() {
        val game = Game("Alice", "Bob")
        assertEquals("Alice", game.currentPlayerName)
        assertEquals(Mark.X, game.currentMark)
    }

}