package dev.jarand.snakesandladders.core

class Square(
    val id: Int,
    val players: MutableList<Player>,
    val ladder: Ladder?,
    val snake: Snake?,
) {
    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun hasPlayers(): Boolean = players.isNotEmpty()

    fun hasLadder(): Boolean = ladder != null

    fun hasSnake(): Boolean = snake != null
}
