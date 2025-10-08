package org.jetbrains.kotlinx.tictactoe.integration

import org.jetbrains.kotlinx.tictactoe.engine.AIPlayer
import org.jetbrains.kotlinx.tictactoe.engine.Game
import org.jetbrains.kotlinx.tictactoe.engine.cache.TTPersistence
import org.jetbrains.kotlinx.tictactoe.engine.cache.Zobrist
import org.jetbrains.kotlinx.tictactoe.model.enums.GamePhase
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class AIIntegrationWithTTTest {

    private val ttFile = "build/tmp/tt_test.json"

    private fun getDepthForPhase(phase: GamePhase): Int =
        when (phase) {
            GamePhase.START -> 1
            GamePhase.MID -> 3
            GamePhase.FINISH -> 6
        }

    @BeforeTest
    fun cleanUpBefore() {
        java.io.File(ttFile).delete()
    }

    @AfterTest
    fun cleanUpAfter() {
        java.io.File(ttFile).delete()
    }

    @Test
    fun `AI vs AI game completes successfully with persistent TT`() {
        val zobrist = Zobrist(42)
        val aiX = AIPlayer(Mark.X, zobrist, ttPath = ttFile)
        val aiO = AIPlayer(Mark.O, zobrist, ttPath = ttFile)
        val game = Game("AI-X", "AI-O")

        while (!game.isOver) {
            val currentAI = if (game.currentMark == Mark.X) aiX else aiO
            val depth = getDepthForPhase(game.state.getStage())

            val move = currentAI.chooseMove(game.board, depth)
            assertTrue(move in 0..8, "AI should return valid board index")

            val success = game.makeMove(move)
            assertTrue(success, "Move should be applied successfully")

            if (game.state.displayStats().contains("FINISH")) {
                currentAI.saveCache()
            }
        }

        println(game.displayBoard())
        println("Result: ${game.resultMessage()}")

        assertTrue(
            game.winner == Mark.X || game.winner == Mark.O || game.board.isFull(),
            "Game should end with a winner or a draw"
        )

        val file = java.io.File(ttFile)
        assertTrue(file.exists(), "TT persistence file should exist after save")
        assertTrue(file.readText().isNotBlank(), "TT persistence file should not be empty")

        val loadedTT = TTPersistence.load(ttFile)
        assertTrue(loadedTT.isNotEmpty(), "Loaded TT should contain entries from gameplay")
    }
}