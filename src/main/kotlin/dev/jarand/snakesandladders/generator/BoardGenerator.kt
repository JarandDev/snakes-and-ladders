package dev.jarand.snakesandladders.generator

import dev.jarand.snakesandladders.core.Board
import dev.jarand.snakesandladders.core.Ladder
import dev.jarand.snakesandladders.core.Square

class BoardGenerator(private val squareCount: Int) {

    fun generate(): Board {
        println("[BoardGenerator] Generating board")
        val ladders = (1..squareCount step 2)
            .filter { (1..10).random() == 1 }
            .map { squareId -> Ladder(start = squareId, end = squareId + (2..20).random()) }
        println("[BoardGenerator] Generated (${ladders.size}) ladders")
        val squares = (1..squareCount)
            .map { squareId ->
                Square(
                    id = squareId,
                    players = mutableListOf(),
                    ladder = ladders.find { ladder -> ladder.start == squareId }?.takeIf { ladder -> ladder.end < squareCount }
                )
            }
        println("[BoardGenerator] Generated (${squares.size}) squares with (${squares.count { it.hasLadder() }}) ladders")
        val board = Board(squares = squares)
        println("[BoardGenerator] Generated board")
        return board
    }
}
