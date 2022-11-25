package apdlll.scores.reader.text

import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TextWithCommentsReaderTest {

    private val reader = object : TextWithCommentsReader<String>("#") {
        override fun readPayloadLine(line: String) = line
        override fun readCommentLine(line: String) = line
    }

    @Test
    fun `read text with comments`() {
        assertEquals("comment", reader.readLine("#comment"))
    }

    @Test
    fun `read text without comments`() {
        val noComment = "//this is not a comment"
        assertEquals(noComment, reader.readLine(noComment))
    }
}
