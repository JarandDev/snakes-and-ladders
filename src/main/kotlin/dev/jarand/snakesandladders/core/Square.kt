package dev.jarand.snakesandladders.core

class Square(val id: Int, val players: MutableList<Player>, val ladder: Ladder?, val snake: Snake?) {

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun hasPlayers(): Boolean {
        return players.isNotEmpty()
    }

    fun hasLadder(): Boolean {
        return ladder != null
    }

    fun hasSnake(): Boolean {
        return snake != null
    }
}
