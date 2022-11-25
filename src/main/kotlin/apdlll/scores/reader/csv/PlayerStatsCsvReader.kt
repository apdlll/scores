package apdlll.scores.reader.csv

import apdlll.scores.model.PlayerStats
import apdlll.scores.reader.text.TextWithCommentsReader

private enum class StatsColumns { PLAYER, TEAM, POSITION, STATS }

/**
 * Reader of files containing the players' stats for a given game:
 * player;team;position;stat1;stat2;...
 */
class PlayerStatsCsvReader(private val csvDelimiter: String, csvComment: String? = null) :
    TextWithCommentsReader<PlayerStats>(csvComment) {

    override fun readPayloadLine(line: String): PlayerStats {
        val data = line.split(csvDelimiter)
        return PlayerStats(
            data[StatsColumns.PLAYER.ordinal],
            data[StatsColumns.TEAM.ordinal],
            data[StatsColumns.POSITION.ordinal],
            data.subList(StatsColumns.STATS.ordinal, data.size).map { it.toInt() }
        )
    }
}
