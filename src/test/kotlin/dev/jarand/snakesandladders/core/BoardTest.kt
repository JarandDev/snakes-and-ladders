package dev.jarand.snakesandladders.core

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BoardTest {
    private lateinit var board: Board
    private lateinit var startSquareMock: Square
    private lateinit var endSquareMock: Square
    private lateinit var playerMock: Player

    @BeforeEach
    fun setup() {
        startSquareMock =
            mockk {
                every { id } returns 1
            }
        justRun { startSquareMock.addPlayer(any()) }
        justRun { startSquareMock.removePlayer(any()) }

        endSquareMock =
            mockk {
                every { id } returns 2
            }
        justRun { endSquareMock.addPlayer(any()) }
        justRun { endSquareMock.removePlayer(any()) }

        board = Board(squares = mutableListOf(startSquareMock, endSquareMock))
        playerMock =
            mockk {
                every { name } returns "Mock player 1"
            }
    }

    @Test
    fun `move to start square should invoke expected methods`() {
        board.movePlayerToStart(player = playerMock)

        verify(exactly = 1) { startSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 0) { startSquareMock.removePlayer(player = playerMock) }
        verify(exactly = 0) { endSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 0) { endSquareMock.removePlayer(player = playerMock) }
    }

    @Test
    fun `move to end square should invoke expected methods`() {
        every { startSquareMock.players } returns mutableListOf(playerMock)

        board.movePlayer(player = playerMock, moves = 1)

        verify(exactly = 0) { startSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 1) { startSquareMock.removePlayer(player = playerMock) }
        verify(exactly = 1) { endSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 0) { endSquareMock.removePlayer(player = playerMock) }
    }

    @Test
    fun `move by ladder to end square should invoke expected methods`() {
        every { startSquareMock.players } returns mutableListOf(playerMock)

        board.movePlayerByLadder(player = playerMock, ladder = Ladder(start = 1, end = 2))

        verify(exactly = 0) { startSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 1) { startSquareMock.removePlayer(player = playerMock) }
        verify(exactly = 1) { endSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 0) { endSquareMock.removePlayer(player = playerMock) }
    }

    @Test
    fun `move beyond end square should invoke expected methods`() {
        every { startSquareMock.players } returns mutableListOf(playerMock)

        board.movePlayer(player = playerMock, moves = 2)

        verify(exactly = 0) { startSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 0) { startSquareMock.removePlayer(player = playerMock) }
        verify(exactly = 0) { endSquareMock.addPlayer(player = playerMock) }
        verify(exactly = 0) { endSquareMock.removePlayer(player = playerMock) }
    }
}
