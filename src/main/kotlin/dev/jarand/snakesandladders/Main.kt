package dev.jarand.snakesandladders

import dev.jarand.snakesandladders.core.Dice
import dev.jarand.snakesandladders.core.Game
import dev.jarand.snakesandladders.generator.BoardGenerator
import dev.jarand.snakesandladders.generator.PlayerGenerator

fun main() {
    val squareCount = 100
    val playerCount = 2

    val boardGenerator = BoardGenerator(squareCount = squareCount)
    val playerGenerator = PlayerGenerator(playerCount = playerCount)

    val board = boardGenerator.generate()
    val dice = Dice()
    val players = playerGenerator.generate()

    val game = Game(board = board, dice = dice, players = players)

    game.setup()

    game.start()

    while (game.hasRemainingRounds()) {
        game.playRound()
    }
}
