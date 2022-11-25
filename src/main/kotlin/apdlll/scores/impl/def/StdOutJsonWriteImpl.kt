package apdlll.scores.impl.def

import apdlll.scores.model.Score
import apdlll.scores.model.WriteInterface
import apdlll.scores.writer.toJson

class StdOutJsonWriteImpl : WriteInterface {
    override fun writeScores(scores: Set<Score>, args: List<Any>) {
        println(scores.sortedBy { it.points }.toJson())
    }

    override fun writeError(error: Throwable, args: List<Any>) {
        System.err.println(error.toJson())
    }
}
