package org.jetbrains.kotlinx.tictactoe.engine
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.test.Test
import kotlin.test.assertTrue


class AIPlayerTest {

    @Test
    fun `AI chooses winning move if available`() {
        val board = Board()
        val aiMark = Mark.X

        board.placeMark(0, aiMark)
        board.placeMark(1, aiMark)
        val ai = AIPlayer(aiMark)
        val move = ai.chooseMove(board, depth = 2)

        assertTrue(move == 2)
    }

    @Test
    fun `AI blocks opponent winning move`() {
        val board = Board()
        val aiMark = Mark.X

        board.placeMark(0, Mark.O)
        board.placeMark(1, Mark.O)

        val ai = AIPlayer(aiMark)
        val move = ai.chooseMove(board, depth = 2)
        assertTrue(move == 2)
    }

    @Test
    fun `AI returns valid available move`() {
        val board = Board()
        val aiMark = Mark.X
        val ai = AIPlayer(aiMark)
        val move = ai.chooseMove(board, depth = 2)
        assertTrue(move in board.availableMoves())
    }
}
