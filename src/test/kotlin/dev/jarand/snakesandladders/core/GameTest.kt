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
    private lateinit var diceMock: Dice
    private lateinit var startSquareMock: Square
    private lateinit var secondSquareMock: Square
    private lateinit var thirdSquareMock: Square
    private lateinit var fourthSquareMock: Square
    private lateinit var endSquareMock: Square
    private lateinit var ladderMock: Ladder
    private lateinit var snakeMock: Snake
    private lateinit var playerMock: Player

    @BeforeEach
    fun setup() {
        ladderMock =
            mockk {
                every { start } returns 2
                every { end } returns 3
            }

        snakeMock =
            mockk {
                every { start } returns 4
                every { end } returns 3
            }

        startSquareMock =
            mockk {
                every { id } returns 1
                every { ladder } returns null
                every { snake } returns null
            }
        justRun { startSquareMock.addPlayer(any()) }
        justRun { startSquareMock.removePlayer(any()) }

        secondSquareMock =
            mockk {
                every { id } returns 2
                every { ladder } returns ladderMock
                every { snake } returns null
            }
        justRun { secondSquareMock.addPlayer(any()) }
        justRun { secondSquareMock.removePlayer(any()) }

        thirdSquareMock =
            mockk {
                every { id } returns 3
                every { ladder } returns null
                every { snake } returns null
            }
        justRun { thirdSquareMock.addPlayer(any()) }
        justRun { thirdSquareMock.removePlayer(any()) }

        fourthSquareMock =
            mockk {
                every { id } returns 4
                every { ladder } returns null
                every { snake } returns snakeMock
            }
        justRun { fourthSquareMock.addPlayer(any()) }
        justRun { fourthSquareMock.removePlayer(any()) }

        endSquareMock =
            mockk {
                every { id } returns 5
                every { ladder } returns null
                every { snake } returns null
                every { hasPlayers() } returns false
            }
        justRun { endSquareMock.addPlayer(any()) }
        justRun { endSquareMock.removePlayer(any()) }

        boardMock =
            mockk {
                every { squares } returns listOf(startSquareMock, secondSquareMock, thirdSquareMock, endSquareMock)
                every { getEndSquare() } returns endSquareMock
            }
        justRun { boardMock.movePlayerToStart(any()) }
        justRun { boardMock.movePlayer(any(), any()) }
        justRun { boardMock.movePlayerByLadder(any(), any()) }
        justRun { boardMock.movePlayerBySnake(any(), any()) }

        diceMock = mockk()

        playerMock =
            mockk {
                every { name } returns "Mock player 1"
            }

        game = Game(board = boardMock, dice = diceMock, players = listOf(playerMock))
    }

    @Test
    fun `Playing round should move player as expected`() {
        every { boardMock.getSquare(player = playerMock) } returns thirdSquareMock
        every { diceMock.roll() } returns 2

        game.setup()
        game.start()

        game.playRound()

        verify(exactly = 1) { boardMock.movePlayer(player = playerMock, moves = 2) }
        verify(exactly = 0) { boardMock.movePlayerByLadder(player = playerMock, ladder = ladderMock) }
        verify(exactly = 0) { boardMock.movePlayerBySnake(player = playerMock, snake = snakeMock) }
    }

    @Test
    fun `Playing round with ladder should move player as expected`() {
        every { boardMock.getSquare(player = playerMock) } returns secondSquareMock
        every { diceMock.roll() } returns 1

        game.setup()
        game.start()

        game.playRound()

        verify(exactly = 1) { boardMock.movePlayer(player = playerMock, moves = 1) }
        verify(exactly = 1) { boardMock.movePlayerByLadder(player = playerMock, ladder = ladderMock) }
        verify(exactly = 0) { boardMock.movePlayerBySnake(player = playerMock, snake = snakeMock) }
    }

    @Test
    fun `Playing round with snake should move player as expected`() {
        every { boardMock.getSquare(player = playerMock) } returns fourthSquareMock
        every { diceMock.roll() } returns 3

        game.setup()
        game.start()

        game.playRound()

        verify(exactly = 1) { boardMock.movePlayer(player = playerMock, moves = 3) }
        verify(exactly = 1) { boardMock.movePlayerBySnake(player = playerMock, snake = snakeMock) }
        verify(exactly = 0) { boardMock.movePlayerByLadder(player = playerMock, ladder = ladderMock) }
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
