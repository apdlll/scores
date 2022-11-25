package apdlll.scores.model

data class Player(
    val name: String,
    val team: String,
    val stats: List<Int>,
    val statsPoints: List<Int>,
    val winPoints: Int
) {

    override fun equals(other: Any?) = this === other || (other is Player && name == other.name)
    override fun hashCode() = name.hashCode()
}
