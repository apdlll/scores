package apdlll.scores.impl.def

import apdlll.scores.test.support.fixtures.`2 games, 2 players, 32vs34 score`
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CalcDefaultImplTest {

    @Test
    fun `calculate scores using an empty games list should return an empty scores list`() {
        assertTrue { CalcDefaultImpl().calculateScores(emptyList()).isEmpty() }
    }

    @Test
    fun `calculate scores using valid games data should return the scores list`() {
        val scores = CalcDefaultImpl().calculateScores(`2 games, 2 players, 32vs34 score`)
        assertAll(
            { assertEquals(2, scores.size, "Wrong number of scores") },
            { assertEquals(32, scores.find { it.player == "player1" }?.points, "Wrong score for player1") },
            { assertEquals(34, scores.find { it.player == "player2" }?.points, "Wrong score for player2") },
        )
    }
}
