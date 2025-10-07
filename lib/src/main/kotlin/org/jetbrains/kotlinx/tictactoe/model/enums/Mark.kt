package org.jetbrains.kotlinx.tictactoe.model.enums

enum class Mark { X, O, EMPTY }

fun Mark.opponent(): Mark = when (this) {
    Mark.X -> Mark.O
    Mark.O -> Mark.X
    else -> Mark.EMPTY
}
