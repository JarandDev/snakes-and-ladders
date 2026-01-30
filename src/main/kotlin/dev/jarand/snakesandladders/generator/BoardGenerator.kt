package dev.jarand.snakesandladders.generator

import dev.jarand.snakesandladders.core.Board
import dev.jarand.snakesandladders.core.Ladder
import dev.jarand.snakesandladders.core.Snake
import dev.jarand.snakesandladders.core.Square

class BoardGenerator(
    private val squareCount: Int,
) {
    fun generate(): Board {
        println("[BoardGenerator] Generating board")
        val ladders =
            (1..squareCount step 2)
                .filter { (1..10).random() == 1 }
                .map { squareId -> Ladder(start = squareId, end = squareId + (2..20).random()) }
        println("[BoardGenerator] Generated (${ladders.size}) ladders")
        val snakes =
            (1..squareCount step 2)
                .filter { (1..10).random() == 1 }
                .map { squareId -> Snake(start = squareId, end = squareId - (2..20).random()) }
        println("[BoardGenerator] Generated (${snakes.size}) snakes")
        val squares =
            (1..squareCount)
                .map { squareId ->
                    val ladder = ladders.find { ladder -> ladder.start == squareId }?.takeIf { ladder -> ladder.end < squareCount }
                    val snake = snakes.find { snake -> snake.start == squareId }?.takeIf { snake -> snake.end > 0 && (ladder?.start != squareId) }
                    Square(
                        id = squareId,
                        players = mutableListOf(),
                        ladder = ladder,
                        snake = snake,
                    )
                }
        println("[BoardGenerator] Generated (${squares.size}) squares with (${squares.count { it.hasLadder() }}) ladders and (${squares.count { it.hasSnake() }}) snakes")
        val board = Board(squares = squares)
        println("[BoardGenerator] Generated board")
        return board
    }
}
