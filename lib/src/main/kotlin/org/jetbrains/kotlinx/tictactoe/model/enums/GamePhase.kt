package org.jetbrains.kotlinx.tictactoe.model.enums

enum class GamePhase { START, MID, FINISH}

fun GamePhase.switch(): GamePhase = when (this) {
    GamePhase.START -> GamePhase.MID
    GamePhase.MID -> GamePhase.FINISH
    else -> GamePhase.START
}
