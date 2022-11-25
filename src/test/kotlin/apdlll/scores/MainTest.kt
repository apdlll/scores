package apdlll.scores

import apdlll.scores.model.Game
import apdlll.scores.test.support.mocks.BuggyWriteErrorMockScoresImpl
import apdlll.scores.test.support.mocks.WorkingMockScoresImpl
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private const val INVALID_ARGS_ERROR = 4
private const val SUCCESS = 0

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MainKtTest {

    @Test
    fun `run with a valid implementation should exit with status SUCCESS`() {
        assertEquals(SUCCESS, run(arrayOf("--impl", WorkingMockScoresImpl::class.java.canonicalName)))
    }

    @Test
    fun `run with invalid params should exit with status INPUT_ARGUMENTS_ERROR`() {
        assertEquals(INVALID_ARGS_ERROR, run(arrayOf("--impl")))
    }

    @Test
    fun `run with an invalid implementation class should exit with status INPUT_ARGUMENTS_ERROR`() {
        assertEquals(INVALID_ARGS_ERROR, run(arrayOf("--impl", "invalid.Implementation")))
    }

    @Test
    fun `run with a buggy implementation should exit with the implementation error status`() {
        val result = run(arrayOf("--impl", BuggyWriteErrorMockScoresImpl::class.java.canonicalName))
        assertAll(
            { assertNotEquals(SUCCESS, result, "Not success result expected") },
            { assertNotEquals(INVALID_ARGS_ERROR, result, "A value other than INVALID_ARGS_ERROR expected") },
        )
    }

    @Test
    fun `run with several input params should them be provided to the implementation class`() {
        assertEquals(SUCCESS, run(arrayOf("1", "--impl", AssertInputParamsMockScoresImpl::class.java.canonicalName, "2", "3")))
    }
}

internal class AssertInputParamsMockScoresImpl : WorkingMockScoresImpl() {
    override fun readGames(args: List<Any>): List<Game> {
        assertAll(
            { assertEquals(3, args.size, "Incorrect number of input params received") },
            { assertEquals("1", args[0], "Incorrect first input param received") },
            { assertEquals("2", args[1], "Incorrect second input param received") },
            { assertEquals("3", args[2], "Incorrect third input param received") },
        )
        return super.readGames(args)
    }
}
