package org.jetbrains.kotlinx.tictactoe.engine
import org.jetbrains.kotlinx.tictactoe.model.GameState
import kotlin.test.Test
import kotlin.test.assertEquals
import org.jetbrains.kotlinx.tictactoe.model.enums.GamePhase

class GameStateTest {

    @Test
    fun `initial phase is START`() {
        val state = GameState(2)
        assertEquals(GamePhase.START, state.getStage())
    }

    @Test
    fun `phase switches after enough moves`() {
        val state = GameState(2)
        state.addMove()
        state.addMove()
        assertEquals(GamePhase.MID, state.getStage())
    }

    @Test
    fun `phase cycles back to START after FINISH`() {
        val state = GameState(1)
        state.addMove()
        state.addMove()
        state.addMove()
        assertEquals(GamePhase.START, state.getStage())
    }
}

