package org.jetbrains.kotlinx.tictactoe

import org.jetbrains.kotlinx.tictactoe.engine.AIPlayer
import org.jetbrains.kotlinx.tictactoe.engine.Game
import org.jetbrains.kotlinx.tictactoe.model.GameConfig
import org.jetbrains.kotlinx.tictactoe.model.enums.Mark
import java.util.*

fun main() {
    println("=== Tic-Tac-Toe ===")
    val scanner = Scanner(System.`in`)

    print("Enter name for Player X (or 'AI' for computer): ")
    val playerXName = scanner.nextLine().ifBlank { "Player X" }

    print("Enter name for Player O (or 'AI' for computer): ")
    val playerOName = scanner.nextLine().ifBlank { "Player O" }

    val aiPlayers = mutableMapOf<Mark, AIPlayer>()
    val config = GameConfig()

    if (playerXName.equals("AI", ignoreCase = true)) {
        aiPlayers[Mark.X] = AIPlayer(Mark.X)
    }
    if (playerOName.equals("AI", ignoreCase = true)) {
        aiPlayers[Mark.O] = AIPlayer(Mark.O)
    }

    val game = Game(
        playerX = playerXName,
        playerO = playerOName,
        aiPlayers = aiPlayers,
        cnfg = config
    )

    println("\nGame started!\n")
    println(game.displayBoard())

    while (!game.isOver) {
        val current = game.currentPlayerName
        println("\n$current's turn (${game.currentMark})")

        if (game.hasAI()) {
            val move = game.autoMove()
            println("AI ($current) chose position: $move")
        } else {
            print("Enter position [0-8]: ")
            val input = scanner.nextLine()
            val pos = input.toIntOrNull()

            if (pos == null || pos !in 0..8) {
                println("Invalid input! Please enter a number between 0 and 8.")
                continue
            }

            if (!game.makeMove(pos)) {
                println("Cell occupied! Try again.")
                continue
            }
        }

        println("\n" + game.displayBoard())
    }

    println("\n" + game.resultMessage())
    if (game.hasAI()) game.end()
}