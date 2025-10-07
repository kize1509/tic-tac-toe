package org.jetbrains.kotlinx.tictactoe.model
import org.jetbrains.kotlinx.tictactoe.model.enums.GamePhase
import org.jetbrains.kotlinx.tictactoe.model.enums.switch

class GameState {
    private var stage = GamePhase.START
    private var moveCounter = 0
    fun addMove(){
        moveCounter+=1
    }
    fun getStage() : GamePhase{
       if( moveCounter > 4){
           stage = stage.switch()
           moveCounter = 0
       }
        return stage
    }

    fun displayStats() : String{
        return "Game stage: ${stage} with ${moveCounter} moves.\n"
    }
}

