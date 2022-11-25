/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.runner

class ScoresWriteException(
    writeException: Throwable,
    val runnerException: Throwable,
    val runnerResult: ScoresRunnerResult
) : Exception(writeException)
