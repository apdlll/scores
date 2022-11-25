package apdlll.scores.reader.csv

import apdlll.scores.model.GamePoints
import apdlll.scores.reader.text.TextWithCommentsReader

private enum class PointsColumns { POSITION, WIN_POINTS, STATS_POINTS }

/**
 * Reader of files containing the points given for each position stat:
 * position;win points;stat1;stat2;...
 */
class GamePointsCsvReader(private val csvDelimiter: String, csvComment: String? = null) :
    TextWithCommentsReader<GamePoints>(csvComment) {

    override fun readPayloadLine(line: String): GamePoints {
        val data = line.split(csvDelimiter)
        return GamePoints(
            data[PointsColumns.POSITION.ordinal],
            data[PointsColumns.WIN_POINTS.ordinal].toInt(),
            data.subList(PointsColumns.STATS_POINTS.ordinal, data.size).map { it.toInt() }
        )
    }
}
