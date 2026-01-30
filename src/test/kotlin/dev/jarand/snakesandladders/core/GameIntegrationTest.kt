package dev.jarand.snakesandladders.core

import dev.jarand.snakesandladders.generator.BoardGenerator
import dev.jarand.snakesandladders.generator.PlayerGenerator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GameIntegrationTest {
    private lateinit var game: Game

    @BeforeEach
    fun setup() {
        game = Game(board = BoardGenerator(squareCount = 100).generate(), dice = Dice(), players = PlayerGenerator(playerCount = 2).generate())
    }

    @Test
    fun `Playing game should move state to ENDED`() {
        game.setup()
        game.start()

        while (game.hasRemainingRounds()) {
            game.playRound()
        }

        assertThat(game.hasEnded()).isTrue()
    }
}
