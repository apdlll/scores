/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.reader.text

class TextReaderException(private val e: Exception?, val line: Int?, val fileName: String) : Exception(e) {
    constructor(fileName: String) : this(null, null, fileName)

    override fun toString() = if (line != null && e != null) {
        "Error reading line $line of file file $fileName: ${e.message}"
    } else {
        "Can not read file $fileName"
    }
}
