package org.jetbrains.kotlinx.tictactoe.engine.cache

import kotlinx.serialization.Serializable

@Serializable
data class TTEntry(val score: Int, val depth: Int)
