package apdlll.scores.test.support.mocks

import apdlll.scores.model.Game
import apdlll.scores.model.Score
import apdlll.scores.model.ScoresInterface

internal enum class FailingStage { READ, CALC, WRITE, WRITE_ERROR, NONE }

internal open class WorkingMockScoresImpl : MockScoresImpl(FailingStage.NONE)
internal class BuggyReadMockScoresImpl : MockScoresImpl(FailingStage.READ)
internal class BuggyCalcMockScoresImpl : MockScoresImpl(FailingStage.CALC)
internal class BuggyWriteMockScoresImpl : MockScoresImpl(FailingStage.WRITE)
internal class BuggyWriteErrorMockScoresImpl : MockScoresImpl(FailingStage.WRITE_ERROR, FailingStage.READ)

internal abstract class MockScoresImpl(private vararg val failingStage: FailingStage) : ScoresInterface {
    override fun readGames(args: List<Any>) = require(FailingStage.READ !in failingStage).run { emptyList<Game>() }
    override fun calculateScores(gamesData: List<Game>) = require(FailingStage.CALC !in failingStage).run { emptySet<Score>() }
    override fun writeScores(scores: Set<Score>, args: List<Any>) = require(FailingStage.WRITE !in failingStage)
    override fun writeError(error: Throwable, args: List<Any>) = require(FailingStage.WRITE_ERROR !in failingStage)
}
