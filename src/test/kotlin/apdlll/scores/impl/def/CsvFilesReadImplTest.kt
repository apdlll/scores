package apdlll.scores.impl.def

import apdlll.scores.model.Game
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CsvFilesReadImplTest {

    @Test
    fun `reading games with valid contents should success`(@TempDir tmpDir: Path) {
        val dir = createFiles(tmpDir, "name;team;position;1;1;1", "position;1;-1;1;1")
        val games = CsvFilesReadImpl().readGames(listOf(dir))
        assertAll(
            { assertEquals(1, games.size, "Incorrect number of games") },
            { assertGame(games[0], 1, true) },
        )
    }

    @Test
    fun `reading games from an empty directory should return an empty list of games`(@TempDir tmpDir: Path) {
        val dir = createDir(tmpDir).absolutePathString()
        assertTrue(CsvFilesReadImpl().readGames(listOf(dir)).isEmpty())
    }

    @Test
    fun `reading games from a directory with subdirectories should return an empty list of games`(@TempDir tmpDir: Path) {
        val dir = createDir(tmpDir)
        val subDir = createDir(dir)
        createFiles(subDir, "name;team;position;1;1;1", "position;1;1")
        assertTrue(CsvFilesReadImpl().readGames(listOf(dir.absolutePathString())).isEmpty())
    }

    @Test
    fun `reading games with empty files should return empty players and points`(@TempDir tmpDir: Path) {
        val dir = createFiles(tmpDir, "", "")
        val games = CsvFilesReadImpl().readGames(listOf(dir))
        assertAll(
            { assertEquals(1, games.size, "Incorrect number of games") },
            { assertGame(games[0], 0, false) },
        )
    }

    @Test
    fun `reading games with invalid format should throw an error`(@TempDir tmpDir: Path) {
        val dir = createFiles(tmpDir, "wrong format", "wrong format")
        assertThrows<Throwable> { CsvFilesReadImpl().readGames(listOf(dir)) }
    }

    @Test
    fun `reading games from a nonexistent directory should throw an error`() {
        assertThrows<Throwable> { CsvFilesReadImpl().readGames(listOf("nonexistent")) }
    }

    @Test
    fun `reading games without providing the CSV files dir should throw an error`() {
        assertThrows<Throwable> { CsvFilesReadImpl().readGames(emptyList()) }
    }

    private fun createDir(tmpDir: Path): Path = Files.createDirectory(tmpDir.resolve("dir"))

    private fun createFiles(tmpDir: Path, playersContents: String, pointsContents: String): String {
        val dir = createDir(tmpDir)
        Files.writeString(dir.resolve("game.players"), playersContents)
        Files.writeString(dir.resolve("game.points"), pointsContents)
        return dir.absolutePathString()
    }

    private fun assertGame(game: Game, playersNumber: Int, lowestScoreWins: Boolean) {
        assertAll(
            { assertEquals("game", game.name, "Incorrect game name") },
            { assertEquals(playersNumber, game.players.size, "Incorrect players number") },
            { assertEquals(lowestScoreWins, game.lowestTeamScoreWins, "Incorrect type of winning score") },
            { assertEquals(0, game.teamScoreStatIndex, "Incorrect team score stat index") },
        )
    }
}
