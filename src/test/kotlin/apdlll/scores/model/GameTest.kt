package apdlll.scores.model

import apdlll.scores.test.support.fixtures.`empty stats points`
import apdlll.scores.test.support.fixtures.`players stats for game1`
import apdlll.scores.test.support.fixtures.`players with empty stats`
import apdlll.scores.test.support.fixtures.`stats points for game1`
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

internal class GameTest {
    @Test
    fun `Game with duplicated players should throw exception`() {
        assertInvalidGame("Duplicated player \"player1\" in game \"game1\"") {
            Game("game1", `players stats for game1`.plus(`players stats for game1`[0]), `stats points for game1`)
        }
    }

    @Test
    fun `Game with missing positions should throw exception`() {
        assertInvalidGame("Missing position \"pos1\" in game \"game1\"") {
            Game("game1", `players stats for game1`, `stats points for game1`.drop(1))
        }
    }

    @Test
    fun `Game without points should throw exception`() {
        assertInvalidGame("Invalid points/stats data for player \"player1\" in game \"game1\"") {
            Game("game1", `players stats for game1`, `empty stats points`)
        }
    }

    @Test
    fun `Game without player's stats should throw exception`() {
        assertInvalidGame("Invalid points/stats data for player \"player1\" in game \"game1\"") {
            Game("game1", `players with empty stats`, `stats points for game1`)
        }
    }

    @Test
    fun `Game with invalid team score stat index should throw exception`() {
        assertInvalidGame("Invalid stat index for the team score \"10\" in game \"game1\"") {
            Game("game1", `players stats for game1`, `stats points for game1`, false, 10)
        }
    }

    private fun assertInvalidGame(expectedMessage: String, executable: () -> Unit) {
        val error = assertThrows<IllegalArgumentException>(executable)
        assertEquals(expectedMessage, error.message)
    }
}
