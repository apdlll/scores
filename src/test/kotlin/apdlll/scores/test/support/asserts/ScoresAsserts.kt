package apdlll.scores.test.support.asserts

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

fun assertStandardOutputEquals(expected: String, executable: () -> Unit) {
    val buffer = ByteArrayOutputStream()
    System.setOut(PrintStream(buffer, true))
    executable()
    System.setOut(System.out)
    assertEquals(expected, buffer.toString())
}

fun assertStandardErrorContains(expected: String, executable: () -> Unit) {
    val buffer = ByteArrayOutputStream()
    System.setErr(PrintStream(buffer, true))
    executable()
    System.setErr(System.err)
    assertTrue { buffer.toString().contains(expected) }
}
