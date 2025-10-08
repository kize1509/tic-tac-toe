package org.jetbrains.kotlinx.tictactoe.utils

import org.jetbrains.kotlinx.tictactoe.engine.cache.TTEntry
import org.jetbrains.kotlinx.tictactoe.engine.cache.Zobrist
import org.jetbrains.kotlinx.tictactoe.model.Board
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import org.jetbrains.kotlinx.tictactoe.model.enums.opponent

fun minimax(
    board: Board,
    depth: Int,
    maximizing: Boolean,
    alpha: Int,
    beta: Int,
    aiMark: Mark,
    zobrist: Zobrist,
    tt: MutableMap<Long, TTEntry>,
    currentMark: Mark
): Int {
    val key = zobrist.hash(board, currentMark)
    val cached = tt[key]
    if (cached != null && cached.depth >= depth) {
        return cached.score
    }

    val winner = board.getWinner()
    if (winner != null || board.isFull() || depth == 0) {
        val score = evaluate(board, aiMark)
        tt[key] = TTEntry(score, depth)
        return score
    }

    var alphaVar = alpha
    var betaVar = beta
    var best = if (maximizing) Int.MIN_VALUE else Int.MAX_VALUE
    val moves = board.availableMoves()

    for (pos in moves) {
        val clone = board.clone()
        val markToPlace = if (maximizing) aiMark else aiMark.opponent()
        clone.placeMark(pos, markToPlace)

        val score = minimax(
            clone,
            depth - 1,
            !maximizing,
            alphaVar,
            betaVar,
            aiMark,
            zobrist,
            tt,
            markToPlace.opponent()
        )

        if (maximizing) {
            best = maxOf(best, score)
            alphaVar = maxOf(alphaVar, score)
        } else {
            best = minOf(best, score)
            betaVar = minOf(betaVar, score)
        }

        if (betaVar <= alphaVar) break
    }

    tt[key] = TTEntry(best, depth)
    return best
}
