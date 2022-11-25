/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.reader.text

abstract class TextWithCommentsReader<T>(private val commentPrefix: String? = null) : TextReader<T>() {

    override fun readLine(line: String) =
        if (commentPrefix != null && line.startsWith(commentPrefix)) {
            readCommentLine(line.substring(commentPrefix.length))
        } else readPayloadLine(line)

    /**
     * Lines passed here are the real deal!
     */
    abstract fun readPayloadLine(line: String): T

    /**
     * Lines passed here are comments without the prefix.
     * By returning null we ignore comments, but you can return an object representing them
     */
    open fun readCommentLine(line: String): T? = null
}
