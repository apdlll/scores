/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.model

/**
 * A game data including [players] stats and points given for each one of them.
 * The [lowestTeamScoreWins] allows creating games whose winner is the team with the lowest score.
 * The [teamScoreStatIndex] specifies the stat index that decides the team score.
 */
data class Game(
    val name: String,
    val players: Set<Player>,
    val lowestTeamScoreWins: Boolean,
    val teamScoreStatIndex: Int
) {
    companion object {
        operator fun invoke(
            name: String,
            playersStats: List<PlayerStats>,
            statsPoints: List<GamePoints>,
            lowestTeamScoreWins: Boolean = false,
            teamScoreStatIndex: Int = 0
        ): Game {
            val pointsPerPosition = statsPoints.associateBy { it.position }
            val players = HashSet<Player>()
            playersStats.forEach { player ->
                val playerPosPoints = pointsPerPosition[player.position]
                require(playerPosPoints != null) { "Missing position \"${player.position}\" in game \"$name\"" }
                require(player.stats.isNotEmpty() && player.stats.size == playerPosPoints.statsPoints.size) {
                    "Invalid points/stats data for player \"${player.name}\" in game \"$name\""
                }
                require(teamScoreStatIndex < player.stats.size) {
                    "Invalid stat index for the team score \"$teamScoreStatIndex\" in game \"$name\""
                }
                val p = Player(
                    player.name, player.team, player.stats,
                    playerPosPoints.statsPoints, playerPosPoints.winPoints
                )
                require(players.add(p)) { "Duplicated player \"${player.name}\" in game \"$name\"" }
            }
            return Game(name, players, lowestTeamScoreWins, teamScoreStatIndex)
        }
    }
}
