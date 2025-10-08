package org.jetbrains.kotlinx.tictactoe.model
import org.jetbrains.kotlinx.tictactoe.model.enums.GamePhase
import org.jetbrains.kotlinx.tictactoe.model.enums.switch

class GameState(private val stepsPerPhase: Int) {
    private var stage = GamePhase.START
    private var moveCounter = 0

    fun addMove() {
        moveCounter++
        if (moveCounter >= stepsPerPhase) {
            stage = stage.switch()
            moveCounter = 0
        }
    }

    fun resetState(){
        moveCounter = 0
        stage = GamePhase.START
    }

    fun getStage(): GamePhase = stage

    fun displayStats(): String =
        "Game stage: $stage ($moveCounter / $stepsPerPhase moves)\n"
}

