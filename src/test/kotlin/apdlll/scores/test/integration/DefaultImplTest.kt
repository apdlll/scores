package apdlll.scores.test.integration

import apdlll.scores.test.support.asserts.assertStandardOutputEquals
import org.junit.jupiter.api.TestInstance
import kotlin.test.Test
import kotlin.test.assertEquals

private const val SUCCESS = 0
private val LS = System.lineSeparator()

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DefaultImplTest {
    @Test
    fun `run with no args should use the default parameters`() {
        assertDefaultResults { apdlll.scores.run(emptyArray()) }
    }

    @Test
    fun `run without specifying the implementation class should use the default one`() {
        assertDefaultResults { apdlll.scores.run(arrayOf("src/test/resources/tournament1")) }
    }

    private fun assertDefaultResults(execution: () -> Int) {
        assertStandardOutputEquals(
            "[" +
                "{\"player\":\"Bronn\",\"points\":2}," +
                "{\"player\":\"Varys\",\"points\":3}," +
                "{\"player\":\"Tyrion\",\"points\":6}," +
                "{\"player\":\"Hodor\",\"points\":8}," +
                "{\"player\":\"Jaime\",\"points\":11}," +
                "{\"player\":\"Arya\",\"points\":18}]$LS"
        ) {
            assertEquals(SUCCESS, execution())
        }
    }
}
