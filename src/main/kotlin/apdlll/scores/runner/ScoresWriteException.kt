package apdlll.scores.runner

class ScoresWriteException(
    writeException: Throwable,
    val runnerException: Throwable,
    val runnerResult: ScoresRunnerResult
) : Exception(writeException)
