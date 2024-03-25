package dev.jarand.snakesandladders.core

class Board(private val squares: List<Square>) {

    init {
        println("[Board] Initialized board with (${squares.size}) squares")
    }

    fun getStartSquare(): Square {
        return squares.first()
    }

    fun getEndSquare(): Square {
        return squares.last()
    }

    fun getSquare(id: Int): Square {
        return squares.find { id == it.id }!!
    }

    fun getSquare(player: Player): Square {
        return squares.first { it.players.contains(player) }
    }

    fun movePlayerToStart(player: Player) {
        getStartSquare().addPlayer(player = player)
        println("[Board] Player (${player.name}) moved to start square")
    }

    fun movePlayerToSquare(player: Player, square: Square) {
        val currentSquare = squares.first { it.players.contains(player) }
        currentSquare.removePlayer(player)
        square.addPlayer(player)
        println("[Board] Player (${player.name}) moved from square (${currentSquare.id}) to (${square.id})")
    }
}
