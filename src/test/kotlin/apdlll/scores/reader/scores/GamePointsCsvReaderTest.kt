package apdlll.scores.reader.scores

import apdlll.scores.reader.csv.GamePointsCsvReader
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class GamePointsCsvReaderTest {
    @Test
    fun `read game points data`() {
        val game = GamePointsCsvReader(";").readLine("P;+10;-1;2;3;4;5")
        assertAll(
            { assertEquals("P", game?.position, "Wrong position") },
            { assertEquals(10, game?.winPoints, "Wrong win points") },
            { assertEquals(5, game?.statsPoints?.size, "Wrong stats points size") },
            { assertEquals(-1, game?.statsPoints?.get(0), "Wrong first stat value") },
            { assertEquals(5, game?.statsPoints?.get(4), "Wrong last stat value") },
        )
    }

    @Test
    fun `read game points data with wrong number of columns`() {
        assertInvalidData("P")
    }

    @Test
    fun `read game points data with invalid column values`() {
        assertInvalidData("P;a;b;c;3;4;5")
    }

    @Test
    fun `read game points data with invalid columns separator`() {
        assertInvalidData("P,+10,-1,2,3,4,5")
    }

    private fun assertInvalidData(data: String) =
        assertThrows<Exception> { GamePointsCsvReader(";").readLine(data) }
}
