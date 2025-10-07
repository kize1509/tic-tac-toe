package org.jetbrains.kotlinx.tictactoe.model

class Board {
    private val cells = MutableList(9) { Mark.EMPTY }

    fun getCells(): List<Mark> = cells.toList()

    fun availableMoves(): List<Int> =
        cells.indices.filter { cells[it] == Mark.EMPTY }


    fun placeMark(position: Int, mark: Mark): Boolean {
        if (position !in 0..8 || cells[position] != Mark.EMPTY) return false
        cells[position] = mark
        return true
    }


    fun clear() {
        for (i in 0..8) {
            cells[i] = Mark.EMPTY
        }
    }

    fun getWinner(): Mark? {
        val lines = listOf(
            listOf(0,1,2), listOf(3,4,5), listOf(6,7,8),
            listOf(0,3,6), listOf(1,4,7), listOf(2,5,8),
            listOf(0,4,8), listOf(2,4,6)
        )
        for (line in lines) {
            val (a,b,c) = line
            if (cells[a] != Mark.EMPTY && cells[a] == cells[b] && cells[a] == cells[c])
                return cells[a]
        }
        return null
    }

    fun isFull(): Boolean = cells.all { it != Mark.EMPTY }

    fun clone(): Board {
        val newBoard = Board()
        for (i in cells.indices)
            if (cells[i] != Mark.EMPTY)
                newBoard.placeMark(i, cells[i])
        return newBoard
    }

}
