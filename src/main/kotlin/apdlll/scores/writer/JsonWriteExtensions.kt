/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.writer

import apdlll.scores.model.Score

fun List<Score>.toJson() = "[" + joinToString(",") { it.toJson() } + "]"
fun Throwable.toJson() = "{\"error\":${message?.let { "\"${it.toJson()}\"" }}," +
    "\"stack\":${stackTraceToString().split(LS).map { it.trim() }.toJsonArray()}}"

private val LS = System.lineSeparator()
private fun String.toJson() = escape(
    '\\' to '\\', '"' to '"', '\b' to 'b', '\n' to 'n', '\r' to 'r', '\t' to 't', '\u000C' to 'f'
)
private fun List<String>.toJsonArray() =
    "[\"" + filter { it.isNotBlank() }.map { it.toJson() }.reduce { json, elem -> "$json\",\"$elem" } + "\"]"
private fun Score.toJson() = """{"player":"${player.toJson()}","points":$points}"""
private fun String.escape(vararg chars: Pair<Char, Char>, escapeSeq: String = "\\") =
    chars.fold(this) { acc, (old, new) -> acc.replace(old.toString(), escapeSeq + new) }
