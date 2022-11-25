package apdlll.scores.impl.def

import apdlll.scores.model.Score
import apdlll.scores.test.support.asserts.assertStandardErrorContains
import apdlll.scores.test.support.asserts.assertStandardOutputEquals
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test

private val LS = System.lineSeparator()

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class StdOutJsonWriteImplTest {

    @Test
    fun `writing scores should write a JSON array of players ordered by increasing points`() {
        val expectedOutput = "[{\"player\":\"p2\",\"points\":0},{\"player\":\"p1\",\"points\":1}]$LS"
        assertStandardOutputEquals(expectedOutput) {
            StdOutJsonWriteImpl().writeScores(setOf(Score("p1", 1), Score("p2", 0)), emptyList())
        }
    }

    @Test
    fun `writing scores with an empty list should not fail`() {
        val expectedOutput = "[]$LS"
        assertStandardOutputEquals(expectedOutput) {
            StdOutJsonWriteImpl().writeScores(emptySet(), emptyList())
        }
    }

    @Test
    fun `writing errors should write a JSON message with the exception`() {
        val expectedOutput = "{\"error\":\"testing errors\",\"stack\":[\""
        assertStandardErrorContains(expectedOutput) {
            StdOutJsonWriteImpl().writeError(Exception("testing errors"), emptyList())
        }
    }
}
