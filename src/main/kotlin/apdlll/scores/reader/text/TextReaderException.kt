package apdlll.scores.reader.text

class TextReaderException(private val e: Exception?, val line: Int?, val fileName: String) : Exception(e) {
    constructor(fileName: String) : this(null, null, fileName)

    override fun toString() = if (line != null && e != null) {
        "Error reading line $line of file file $fileName: ${e.message}"
    } else {
        "Can not read file $fileName"
    }
}
