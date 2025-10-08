package org.jetbrains.kotlinx.tictactoe.engine.cache

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.random.Random

class Zobrist(private val seed: Long = System.currentTimeMillis()) {

    private val rand = Random(seed)

    private val table: Array<LongArray> = Array(9) { LongArray(2) { rand.nextLong() } }

    private val sideToMove: Long = rand.nextLong()

    private fun markIndex(mark: Mark): Int =
        when (mark) {
            Mark.X -> 0
            Mark.O -> 1
            else -> -1
        }

    fun hash(board: Board, currentMark: Mark): Long {
        var h = 0L
        val cells = board.getCells()
        for (pos in cells.indices) {
            val m = cells[pos]
            val idx = markIndex(m)
            if (idx >= 0) {
                h = h xor table[pos][idx]
            }
        }
        if (currentMark == Mark.O) {
            h = h xor sideToMove
        }
        return h
    }
}
