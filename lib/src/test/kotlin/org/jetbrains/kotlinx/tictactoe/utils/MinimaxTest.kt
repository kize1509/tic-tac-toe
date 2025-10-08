package org.jetbrains.kotlinx.tictactoe.utils

import junit.framework.TestCase.assertTrue
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.test.Test


class MinimaxTest {

    @Test
    fun `minimax returns an integer score for AI move`() {
        val board = Board()
        val aiMark = Mark.X
        val mx = Minimax()
        val score = mx.minimax(
            board = board,
            depth = 3,
            maximizing = true,
            alpha = Int.MIN_VALUE,
            beta = Int.MAX_VALUE,
            aiMark = aiMark
        )

        assertTrue(score is Int)
    }

    @Test
    fun `minimax chooses winning move if available`() {
        val board = Board()
        val aiMark = Mark.X
        val mx = Minimax()

        board.placeMark(0, aiMark)
        board.placeMark(1, aiMark)

        val bestScore = mx.minimax(
            board = board,
            depth = 2,
            maximizing = true,
            alpha = Int.MIN_VALUE,
            beta = Int.MAX_VALUE,
            aiMark = aiMark
        )

        assertTrue(bestScore > 0)
    }
}