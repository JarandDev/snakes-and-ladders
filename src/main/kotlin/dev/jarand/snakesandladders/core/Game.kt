package dev.jarand.snakesandladders.core

class Game(
    private val board: Board,
    private val dice: Dice,
    private val players: List<Player>,
) {
    private var state = GameState.INITIALIZED
    private var rounds = 0

    init {
        println("[Game] Initialized game with (${players.size}) players")
    }

    fun setup() {
        if (state != GameState.INITIALIZED) {
            throw RuntimeException("Invalid state to setup the game: $state, expecting ${GameState.INITIALIZED}")
        }
        players.forEach {
            board.movePlayerToStart(player = it)
        }
        state = GameState.SETUP_COMPLETE
    }

    fun start() {
        if (state != GameState.SETUP_COMPLETE) {
            throw RuntimeException("Invalid state to start the game: $state, expecting ${GameState.SETUP_COMPLETE}")
        }
        state = GameState.RUNNING
    }

    fun playRound() {
        if (state != GameState.RUNNING) {
            throw RuntimeException("Invalid state to play round: $state, expecting ${GameState.RUNNING}")
        }
        if (board.getEndSquare().hasPlayers()) {
            state = GameState.ENDED
            println("[Game] Game ended in ($rounds) rounds! Winner(s): ${board.getEndSquare().players.map { it.name }}")
            return
        }
        rounds++
        println("[Game] Playing round ($rounds)")
        players.forEach { player ->
            val moves = dice.roll()
            board.movePlayer(player = player, moves = moves)
            val square = board.getSquare(player = player)
            square.ladder?.let { board.movePlayerByLadder(player = player, ladder = it) }
            square.snake?.let { board.movePlayerBySnake(player = player, snake = it) }
        }
    }

    fun hasRemainingRounds(): Boolean = state == GameState.RUNNING

    fun hasEnded(): Boolean = state == GameState.ENDED
}
