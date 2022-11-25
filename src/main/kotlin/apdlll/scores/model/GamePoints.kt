package apdlll.scores.model

/**
 * Points given for a position in a game
 */
data class GamePoints(val position: String, val winPoints: Int, val statsPoints: List<Int>)
