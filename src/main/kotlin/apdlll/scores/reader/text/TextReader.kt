package apdlll.scores.reader.text

import java.io.Reader

abstract class TextReader<T> {

    @Suppress("TooGenericExceptionCaught")
    fun read(reader: Reader?, fileName: String): List<T> = reader?.useLines { lines ->
        lines.mapIndexedNotNull { lineNumber, line ->
            try {
                readLine(line)
            } catch (e: Exception) {
                throw TextReaderException(e, lineNumber + 1, fileName)
            }
        }.toList()
    } ?: throw TextReaderException(fileName)

    /**
     * Transform given line into an object representing it
     * or return null to ignore it
     */
    abstract fun readLine(line: String): T?
}
