package org.jetbrains.kotlinx.tictactoe.engine.cache

import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import kotlin.random.Random

class Zobrist(private val seed: Long = System.currentTimeMillis()) {

    private val rand = Random(seed)

    // [position][markIndex], where markIndex: 0 = X, 1 = O
    private val table: Array<LongArray> = Array(9) { LongArray(2) { rand.nextLong() } }

    // optional: a value to xor when it's O's turn (or use sideToMove)
    private val sideToMove: Long = rand.nextLong()

    // helper to map Mark -> index in table or -1 for EMPTY
    private fun markIndex(mark: Mark): Int =
        when (mark) {
            Mark.X -> 0
            Mark.O -> 1
            else -> -1
        }

    fun hash(board: Board, currentMark: Mark): Long {
        var h = 0L
        val cells = board.getCells() // expects List<Mark>
        for (pos in cells.indices) {
            val m = cells[pos]
            val idx = markIndex(m)
            if (idx >= 0) {
                h = h xor table[pos][idx]
            }
        }
        // include side to move (so X-to-move vs O-to-move differ)
        if (currentMark == Mark.O) {
            h = h xor sideToMove
        }
        return h
    }
}
