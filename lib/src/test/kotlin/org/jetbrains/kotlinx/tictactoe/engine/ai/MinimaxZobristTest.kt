package org.jetbrains.kotlinx.tictactoe.engine.ai

import org.jetbrains.kotlinx.tictactoe.engine.AIPlayer
import org.jetbrains.kotlinx.tictactoe.engine.cache.Zobrist
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.test.Test
import kotlin.test.assertTrue

class MinimaxZobristTest {

    @Test
    fun `AIPlayer reuses TT between moves`() {
        val zobrist = Zobrist(42L)
        val ai = AIPlayer(Mark.X, zobrist)
        val board = Board()

        val move1 = ai.chooseMove(board, 3)
        assertTrue(move1 in 0..8)

        val ttSizeAfterFirst = ai.javaClass
            .getDeclaredField("tt")
            .apply { isAccessible = true }
            .get(ai) as MutableMap<*, *>
        assertTrue(ttSizeAfterFirst.isNotEmpty())

        board.placeMark(move1, Mark.X)
        val move2 = ai.chooseMove(board, 3)
        assertTrue(move2 in 0..8)

        val ttSizeAfterSecond = (ai.javaClass
            .getDeclaredField("tt")
            .apply { isAccessible = true }
            .get(ai) as MutableMap<*, *>).size

        assertTrue(ttSizeAfterSecond >= ttSizeAfterFirst.size)
    }
}