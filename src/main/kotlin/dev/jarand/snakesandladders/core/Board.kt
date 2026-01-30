package dev.jarand.snakesandladders.core

class Board(
    val squares: List<Square>,
) {
    init {
        println("[Board] Initialized board with (${squares.size}) squares")
    }

    fun getStartSquare(): Square = squares.first()

    fun getEndSquare(): Square = squares.last()

    fun getSquare(id: Int): Square = squares.find { id == it.id }!!

    fun getSquare(player: Player): Square = squares.first { it.players.contains(player) }

    fun movePlayerToStart(player: Player) {
        getStartSquare().addPlayer(player = player)
        println("[Board] Player (${player.name}) moved to start square")
    }

    fun movePlayer(
        player: Player,
        moves: Int,
    ) {
        val currentSquare = squares.first { it.players.contains(player) }
        val targetSquareId = currentSquare.id + moves
        if (targetSquareId > getEndSquare().id) {
            println("[Board] Player (${player.name}) can not move ($moves) moves because it would be out of board")
            return
        }
        val targetSquare = squares[targetSquareId - 1]
        currentSquare.removePlayer(player)
        targetSquare.addPlayer(player)
        println("[Board] Player (${player.name}) moved ($moves) moves from square (${currentSquare.id}) to (${targetSquare.id})")
    }

    fun movePlayerByLadder(
        player: Player,
        ladder: Ladder,
    ) {
        val currentSquare = squares[ladder.start - 1]
        val targetSquare = squares[ladder.end - 1]
        currentSquare.removePlayer(player)
        targetSquare.addPlayer(player)
        println("[Board] Player (${player.name}) moved by ladder from square (${currentSquare.id}) to (${targetSquare.id})")
    }

    fun movePlayerBySnake(
        player: Player,
        snake: Snake,
    ) {
        val currentSquare = squares[snake.start - 1]
        val targetSquare = squares[snake.end - 1]
        currentSquare.removePlayer(player)
        targetSquare.addPlayer(player)
        println("[Board] Player (${player.name}) moved by snake from square (${currentSquare.id}) to (${targetSquare.id})")
    }
}
