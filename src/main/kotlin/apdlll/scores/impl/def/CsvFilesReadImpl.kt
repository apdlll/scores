/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.impl.def

import apdlll.scores.model.Game
import apdlll.scores.model.ReadInterface
import apdlll.scores.reader.csv.GamePointsCsvReader
import apdlll.scores.reader.csv.PlayerStatsCsvReader
import java.io.File
import java.io.Reader

private const val EXTENSION_DELIMITER = "."
private const val PLAYERS_FILE_EXTENSION = EXTENSION_DELIMITER + "players"
private const val POINTS_FILE_EXTENSION = EXTENSION_DELIMITER + "points"
private const val CSV_DELIMITER = ";"
private const val CSV_COMMENT = "#"

class CsvFilesReadImpl : ReadInterface {
    override fun readGames(args: List<Any>): List<Game> {
        val filesDir = readInputParams(args)
        val gamesFiles = readFiles(filesDir)
        val gamesNames = gamesFiles.keys.map { it.substringBeforeLast(EXTENSION_DELIMITER) }.distinct()
        return gamesNames.map { getGame(it, gamesFiles) }
    }

    private fun readInputParams(args: List<Any>) =
        require(args.isNotEmpty()) { "Missing CSV files dir path parameter" }.run { args[0] as String }

    private fun readFiles(dir: String): Map<String, Reader> {
        val dirFile = File(dir)
        return dirFile.listFiles {
            pathname ->
            !(pathname?.isDirectory ?: false)
        }?.associate {
            it.name to it.bufferedReader()
        } ?: throw IllegalArgumentException("Directory \"${dirFile.absolutePath}\" does not exist")
    }

    private fun getGame(name: String, files: Map<String, Reader>): Game {
        val pointsFile = name + POINTS_FILE_EXTENSION
        val playersFile = name + PLAYERS_FILE_EXTENSION
        val playerStats = PlayerStatsCsvReader(CSV_DELIMITER, CSV_COMMENT).read(files[playersFile], playersFile)
        val gamePoints = GamePointsCsvReader(CSV_DELIMITER, CSV_COMMENT).read(files[pointsFile], pointsFile)
        // To simplify the CSV, we use the following conventions:
        // - 1st stat value (at index 0) is the one that decides the team score in the game
        // - to know if the game is won by the lowest score, points given for that 1st stat should be <= 0
        val teamScoreStat = 0
        val lowestScoreWins = gamePoints.isNotEmpty() && gamePoints.first().statsPoints[teamScoreStat] <= 0
        return Game(name, playerStats, gamePoints, lowestScoreWins, teamScoreStat)
    }
}
