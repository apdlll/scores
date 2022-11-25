package apdlll.scores.model

/**
 * A player's stats for a given team and position in a game
 */
data class PlayerStats(val name: String, val team: String, val position: String, val stats: List<Int>)
