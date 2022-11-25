/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores

import apdlll.scores.impl.def.ScoresDefaultImpl
import apdlll.scores.model.ScoresInterface
import apdlll.scores.runner.ScoresRunner
import apdlll.scores.runner.ScoresRunnerResult
import apdlll.scores.runner.ScoresWriteException
import kotlin.system.exitProcess

/**
 * CMD usage: `$ java -jar scores.jar [--impl package.ImplClass] [impl params]`
 * @param args --impl Full name of the implementation class
 *             (defaults to a CSV files reader printing the results to the standard output as JSON),
 *             [impl params] any params needed by the implementation class
 */
fun main(args: Array<String>) {
    val exitCode = run(args)
    exitProcess(exitCode)
}

@Suppress("TooGenericExceptionCaught")
fun run(args: Array<String>): Int {
    val params: InputParams
    val impl: ScoresInterface

    try {
        params = readInputParams(args)
        impl = getInstance(params.implClassName) as ScoresInterface
    } catch (e: Throwable) {
        System.err.println(
            "Invalid arguments: ${e.message}\n\r" +
                "Usage: java -jar scores.jar [--impl package.ImplClass] [impl params]"
        )
        return getInputArgumentsErrorExitCode()
    }

    val runnerResult = try {
        ScoresRunner(impl).run(params.implClassArgs)
    } catch (e: ScoresWriteException) {
        // Writing stage imploded! let's take care of the debris...
        System.err.println(
            "Implementation error: ${e.runnerException.message}\r\n" +
                "\"Write error\" implementation error: ${e.message}"
        )
        e.runnerResult
    }
    return getExitCode(runnerResult)
}

private fun getInputArgumentsErrorExitCode() = ScoresRunnerResult.values().size
private fun getExitCode(result: ScoresRunnerResult) = result.ordinal

private data class InputParams(val implClassName: String, val implClassArgs: List<String>)
private fun readInputParams(args: Array<String>): InputParams {
    // Provide nice defaults to the user
    val defaultArgs = listOf("src/test/resources/tournament1")
    val defaultImpl = ScoresDefaultImpl::class.java.canonicalName
    var implClassName = defaultImpl
    val implArgs = mutableListOf<String>()
    for (arg in args) {
        when (arg) {
            "--impl", "-i" -> implClassName = null
            else -> if (implClassName == null) implClassName = arg else implArgs.add(arg)
        }
    }
    return InputParams(implClassName, if (implArgs.isEmpty() && implClassName == defaultImpl) defaultArgs else implArgs)
}

private fun getInstance(name: String) = Class.forName(name).getDeclaredConstructor().newInstance()
