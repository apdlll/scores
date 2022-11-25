package apdlll.scores.reader.scores

import apdlll.scores.reader.csv.PlayerStatsCsvReader
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.lang.Exception
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PlayerStatsCsvReaderTest {

    @Test
    fun `read player stats data`() {
        val playerStats = PlayerStatsCsvReader(";").readLine("Player name;Team name;P;+1;2;3;4;-5")
        assertAll(
            { assertEquals("Player name", playerStats?.name, "Wrong player name") },
            { assertEquals("Team name", playerStats?.team, "Wrong team name") },
            { assertEquals("P", playerStats?.position, "Wrong position") },
            { assertEquals(5, playerStats?.stats?.size, "Wrong stats size") },
            { assertEquals(1, playerStats?.stats?.get(0), "Wrong first stat value") },
            { assertEquals(-5, playerStats?.stats?.get(4), "Wrong last stat value") },
        )
    }

    @Test
    fun `read player data with wrong number of columns`() {
        assertInvalidData("Player name;Team name")
    }

    @Test
    fun `read player data data with invalid column values`() {
        assertInvalidData("Player name;Team name;P;a;b")
    }

    @Test
    fun `read player data data with invalid columns separator`() {
        assertInvalidData("Player name,Team name,P,1,2,3,4,5")
    }

    private fun assertInvalidData(data: String) =
        assertThrows<Exception> { PlayerStatsCsvReader(";").readLine(data) }
}
