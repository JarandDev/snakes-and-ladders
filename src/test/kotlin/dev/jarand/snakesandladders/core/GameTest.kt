package dev.jarand.snakesandladders.core

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameTest {

    private lateinit var game: Game
    private lateinit var boardMock: Board
    private lateinit var startSquareMock: Square
    private lateinit var secondSquareMock: Square
    private lateinit var thirdSquareMock: Square
    private lateinit var endSquareMock: Square
    private lateinit var playerMock: Player

    @BeforeEach
    fun setup() {
        startSquareMock = mockk {
            every { id } returns 1
        }
        justRun { startSquareMock.addPlayer(any()) }
        justRun { startSquareMock.removePlayer(any()) }

        secondSquareMock = mockk {
            every { id } returns 2
        }
        justRun { secondSquareMock.addPlayer(any()) }
        justRun { secondSquareMock.removePlayer(any()) }

        thirdSquareMock = mockk {
            every { id } returns 3
        }
        justRun { thirdSquareMock.addPlayer(any()) }
        justRun { thirdSquareMock.removePlayer(any()) }

        endSquareMock = mockk {
            every { id } returns 4
        }
        justRun { endSquareMock.addPlayer(any()) }
        justRun { endSquareMock.removePlayer(any()) }

        playerMock = mockk {
            every { name } returns "Mock player 1"
        }

        boardMock = mockk {
            every { squares } returns listOf(startSquareMock, secondSquareMock, thirdSquareMock, endSquareMock)
        }
        justRun { boardMock.movePlayerToStart(any()) }

        game = Game(board = boardMock, players = listOf(playerMock))
    }

    @Test
    fun `Setup should move players to start`() {
        game.setup()

        verify(exactly = 1) { boardMock.movePlayerToStart(player = playerMock) }
    }

    @Test
    fun `Setup should not set remaining rounds to true`() {
        game.setup()

        assertThat(game.hasRemainingRounds()).isFalse()
    }

    @Test
    fun `Setup should not set has ended to true`() {
        game.setup()

        assertThat(game.hasEnded()).isFalse()
    }

    @Test
    fun `Start should set remaining rounds to true`() {
        game.setup()
        game.start()

        assertThat(game.hasRemainingRounds()).isTrue()
    }

    @Test
    fun `Start should not set has ended to true`() {
        game.setup()
        game.start()

        assertThat(game.hasEnded()).isFalse()
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
