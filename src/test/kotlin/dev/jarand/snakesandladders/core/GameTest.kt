package dev.jarand.snakesandladders.core

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameTest {

    private lateinit var game: Game

    @BeforeEach
    fun setup() {
        game = Game(squareCount = 100, 2)
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

    @Test
    fun `Starting before setup should throw exception`() {
        assertThrows<RuntimeException> {
            game.start()
        }
    }

    @Test
    fun `Setup twice should throw exception`() {
        game.setup()

        assertThrows<RuntimeException> {
            game.setup()
        }
    }

    @Test
    fun `Starting twice should throw exception`() {
        game.setup()
        game.start()

        assertThrows<RuntimeException> {
            game.start()
        }
    }

    @Test
    fun `Playing round without setting up should throw exception`() {
        assertThrows<RuntimeException> {
            game.playRound()
        }
    }

    @Test
    fun `Playing round without starting should throw exception`() {
        game.setup()

        assertThrows<RuntimeException> {
            game.playRound()
        }
    }
}
