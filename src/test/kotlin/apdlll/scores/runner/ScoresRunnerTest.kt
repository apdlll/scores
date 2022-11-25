package apdlll.scores.runner

import apdlll.scores.test.support.mocks.BuggyCalcMockScoresImpl
import apdlll.scores.test.support.mocks.BuggyReadMockScoresImpl
import apdlll.scores.test.support.mocks.BuggyWriteErrorMockScoresImpl
import apdlll.scores.test.support.mocks.BuggyWriteMockScoresImpl
import apdlll.scores.test.support.mocks.WorkingMockScoresImpl
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ScoresRunnerTest {

    @Test
    fun `runner with a valid implementation should success`() {
        assertEquals(ScoresRunnerResult.SUCCESS, ScoresRunner(WorkingMockScoresImpl()).run(emptyList()))
    }

    @Test
    fun `runner with a buggy read implementation should return READ_ERROR`() {
        assertEquals(ScoresRunnerResult.READ_ERROR, ScoresRunner(BuggyReadMockScoresImpl()).run(emptyList()))
    }

    @Test
    fun `runner with a buggy read implementation should return CALC_ERROR`() {
        assertEquals(ScoresRunnerResult.CALC_ERROR, ScoresRunner(BuggyCalcMockScoresImpl()).run(emptyList()))
    }

    @Test
    fun `runner with a buggy read implementation should return WRITE_ERROR`() {
        assertEquals(ScoresRunnerResult.WRITE_ERROR, ScoresRunner(BuggyWriteMockScoresImpl()).run(emptyList()))
    }

    @Test
    fun `runner with a buggy write error implementation should throw exception`() {
        val e = assertThrows<ScoresWriteException> { ScoresRunner(BuggyWriteErrorMockScoresImpl()).run(emptyList()) }
        assertEquals(ScoresRunnerResult.READ_ERROR, e.runnerResult)
    }
}
