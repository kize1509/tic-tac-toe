package org.jetbrains.kotlinx.tictactoe.model

enum class Mark { X, O, EMPTY }

fun Mark.opponent(): Mark = when (this) {
    Mark.X -> Mark.O
    Mark.O -> Mark.X
    else -> Mark.EMPTY
}
