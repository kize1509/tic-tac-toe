package org.jetbrains.kotlinx.tictactoe.integration

import org.jetbrains.kotlinx.tictactoe.engine.AIPlayer
import org.jetbrains.kotlinx.tictactoe.engine.Game
import org.jetbrains.kotlinx.tictactoe.engine.cache.TTPersistence
import org.jetbrains.kotlinx.tictactoe.engine.cache.Zobrist
import org.jetbrains.kotlinx.tictactoe.model.GameConfig
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class AIIntegrationWithTTTest {

    private val ttFile = "build/tmp/tt_test.json"

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
        val aiX = AIPlayer(Mark.X, zobrist)
        val aiO = AIPlayer(Mark.O, zobrist)
        val config = GameConfig(aiDepthStart = 4, aiDepthMid = 6, aiDepthFinish = 8)

        val game = Game(
            playerX = "AI-X",
            playerO = "AI-O",
            aiPlayers = mapOf(Mark.X to aiX, Mark.O to aiO),
            ttPath = ttFile,
            cnfg = config
        )

        var moveCount = 0
        while (!game.isOver) {
            val move = game.autoMove()
            assertTrue(move in 0..8, "AI should return valid board index")
            moveCount++
        }

        game.end()

        println(game.displayBoard())
        println("Result: ${game.resultMessage()}")
        println("Moves played: $moveCount")

        assertTrue(
            game.winner == Mark.X || game.winner == Mark.O || game.board.isFull(),
            "Game should end with a winner or a draw"
        )

        val file = java.io.File(ttFile)
        assertTrue(file.exists(), "TT persistence file should exist after save")
        assertTrue(file.readText().isNotBlank(), "TT persistence file should not be empty")

        val loadedTT = TTPersistence.load(ttFile)
        assertTrue(loadedTT.isNotEmpty(), "Loaded TT should contain entries from gameplay")

        val aiX2 = AIPlayer(Mark.X, zobrist)
        val aiO2 = AIPlayer(Mark.O, zobrist)
        val game2 = Game("AI-X2", "AI-O2", mapOf(Mark.X to aiX2, Mark.O to aiO2), ttFile, config)

        var moves2 = 0
        while (!game2.isOver) {
            game2.autoMove()
            moves2++
        }

        assertTrue(moves2 > 0, "Second game should also complete successfully with loaded TT")
        game2.end()
    }
}