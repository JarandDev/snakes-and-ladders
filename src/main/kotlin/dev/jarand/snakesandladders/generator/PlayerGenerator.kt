package dev.jarand.snakesandladders.generator

import dev.jarand.snakesandladders.core.Player

class PlayerGenerator(
    private val playerCount: Int,
) {
    fun generate(): List<Player> {
        println("[PlayerGenerator] Generating players")
        val players = (1..playerCount).map { Player(name = "Player $it") }
        println("[PlayerGenerator] Generated (${players.size}) players")
        return players
    }
}
