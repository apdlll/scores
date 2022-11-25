package apdlll.scores.model

/**
 * Contains the final score
 */
data class Score(val player: String, val points: Int) {

    override fun equals(other: Any?) = this === other || (other is Score && player == other.player)
    override fun hashCode() = player.hashCode()
}
