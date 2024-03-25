package dev.jarand.snakesandladders

import dev.jarand.snakesandladders.core.Game

fun main() {
    val game = Game(squareCount = 100, playerCount = 2)

    game.setup()

    game.start()

    while (game.hasRemainingRounds()) {
        game.playRound()
    }
}
