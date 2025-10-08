package org.jetbrains.kotlinx.tictactoe.model

data class GameConfig(
    val zobristSeed: Long = System.currentTimeMillis(),
    val aiDepthStart: Int = 10,
    val aiDepthMid: Int = 15,
    val aiDepthFinish: Int = 20
)
