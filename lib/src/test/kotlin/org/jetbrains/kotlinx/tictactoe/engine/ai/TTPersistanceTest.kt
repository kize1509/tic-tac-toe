package org.jetbrains.kotlinx.tictactoe.engine.ai

import org.jetbrains.kotlinx.tictactoe.engine.AIPlayer
import org.jetbrains.kotlinx.tictactoe.engine.cache.TTPersistence
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import java.io.File
import kotlin.test.Test
import kotlin.test.assertTrue

class TTPersistenceTest {

    @Test
    fun `AI TT persists between runs`() {
        val filePath = "test_tt.json"
        val ai = AIPlayer(Mark.X, ttPath = filePath)

        val board = Board()
        ai.chooseMove(board, depth = 2)
        ai.saveCache()

        val newTT = TTPersistence.load(filePath)
        assertTrue(newTT.isNotEmpty(), "TT file should contain entries")

        File(filePath).delete()
    }
}
