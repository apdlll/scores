/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.runner

import apdlll.scores.model.Game
import apdlll.scores.model.Score
import apdlll.scores.model.ScoresInterface

@Suppress("TooGenericExceptionCaught", "ReturnCount")
class ScoresRunner(val impl: ScoresInterface) {
    fun run(args: List<String>): ScoresRunnerResult {
        val gamesData: List<Game>
        try {
            gamesData = impl.readGames(args)
        } catch (e: Throwable) {
            return handleError(e, args, ScoresRunnerResult.READ_ERROR)
        }

        val scores: Set<Score>
        try {
            scores = impl.calculateScores(gamesData)
        } catch (e: Throwable) {
            return handleError(e, args, ScoresRunnerResult.CALC_ERROR)
        }

        try {
            impl.writeScores(scores, args)
        } catch (e: Throwable) {
            return handleError(e, args, ScoresRunnerResult.WRITE_ERROR)
        }

        return ScoresRunnerResult.SUCCESS
    }

    private fun handleError(
        runnerException: Throwable,
        args: List<String>,
        result: ScoresRunnerResult
    ): ScoresRunnerResult {
        try {
            impl.writeError(runnerException, args)
        } catch (writeException: Throwable) {
            throw ScoresWriteException(writeException, runnerException, result)
        }
        return result
    }
}
