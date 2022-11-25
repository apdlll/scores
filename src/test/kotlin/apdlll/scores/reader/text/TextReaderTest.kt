package apdlll.scores.reader.text

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import java.io.StringReader

private val LS = System.lineSeparator()

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TextReaderTest {

    private val textReader = object : TextReader<String>() {
        override fun readLine(line: String) = line
    }

    @Test
    fun `read text with no line separator ending`() {
        assertReadText("line 1${LS}line 2")
    }

    @Test
    fun `read text with line separator ending`() {
        assertReadText("line 1${LS}line 2$LS")
    }

    @Test
    fun `read invalid reader`() {
        val exception = assertThrows<TextReaderException> { textReader.read(null, "text") }
        Assertions.assertEquals("text", exception.fileName)
    }

    @Test
    fun `read invalid line`() {
        val textReader = object : TextReader<String>() {
            override fun readLine(line: String) = if (line == "exception") throw Exception() else line
        }
        val text = "line 1${LS}line 2${LS}exception"
        val exception = assertThrows<TextReaderException> { textReader.read(StringReader(text), "text") }
        assertAll(
            { Assertions.assertEquals("text", exception.fileName) },
            { Assertions.assertEquals(3, exception.line) },
        )
    }

    private fun assertReadText(text: String) {
        val lines = textReader.read(StringReader(text), "text")
        assertAll(
            { Assertions.assertEquals(2, lines.size, "Wrong number of lines") },
            { Assertions.assertEquals("line 1", lines[0], "Incorrect first line") },
            { Assertions.assertEquals("line 2", lines[1], "Incorrect second line") },
        )
    }
}
