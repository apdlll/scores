package apdlll.scores.writer

import apdlll.scores.model.Score
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class JsonWriteExtensionsTest {

    @Test
    fun `write an empty list of scores to JSON`() {
        assertEquals("[]", emptyList<Score>().toJson())
    }

    @Test
    fun `write a list of scores to JSON`() {
        assertEquals(
            """[{"player":"player1","points":1},{"player":"player\"2\"","points":2}]""",
            listOf(Score("player1", 1), Score("player\"2\"", 2)).toJson()
        )
    }

    @Test
    fun `write an exception with message to JSON`() {
        assertTrue { Exception("Message").toJson().startsWith("""{"error":"Message","stack":[""") }
    }

    @Test
    fun `write an exception without message to JSON`() {
        assertTrue { Exception().toJson().startsWith("""{"error":null,"stack":[""") }
    }
}
