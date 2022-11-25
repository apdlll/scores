/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.impl.def

import apdlll.scores.model.CalcInterface
import apdlll.scores.model.Game
import apdlll.scores.model.Player
import apdlll.scores.model.Score

class CalcDefaultImpl : CalcInterface {

    /**
     * Return the player's score adding the extra points if the player belongs to the winner team
     */
    private fun getPlayerPersonalScore(player: Player, winnerTeam: String?) =
        player.stats.mapIndexed {
            index, statValue ->
            statValue * player.statsPoints[index]
        }.reduce {
            acc, score ->
            acc + score
        }.plus(if (player.team == winnerTeam) player.winPoints else 0)

    /**
     * Return the player's stat on the list that contributes to the team score in the game
     * and, if the game is won by the lowest score, return its negative value
     */
    private fun getPlayerTeamScore(player: Player, game: Game) =
        player.stats[game.teamScoreStatIndex].run { if (game.lowestTeamScoreWins) unaryMinus() else this }

    /**
     * Return a map of team name to its score in the game
     */
    private fun getTeamsScores(game: Game) = game.players.fold(mutableMapOf<String, Int>()) { acc, player ->
        acc.compute(player.team) { _, points -> (points ?: 0) + getPlayerTeamScore(player, game) }.run { acc }
    }

    /**
     * Return the team with the highest score. In case of a tie return null
     */
    private fun getWinnerTeam(game: Game) = getTeamsScores(game).entries.fold(null as String? to Int.MIN_VALUE) {
        acc, teamScore ->
        if (acc.second < teamScore.value) teamScore.toPair()
        else if (acc.second == teamScore.value) null to teamScore.value
        else acc
    }.first

    /**
     * Sum of two scores:
     * Score(player, score1) + Score(otherPlayer, score2) = Score(player, score1 + score2>
     */
    private operator fun Score.plus(other: Score?) = copy(points = this.points + (other?.points ?: 0))

    /**
     * Map all players' scores from all games and aggregate them by player's name
     */
    override fun calculateScores(gamesData: List<Game>) = gamesData.flatMap { game ->
        val winnerTeam = getWinnerTeam(game)
        game.players.map { player -> Score(player.name, getPlayerPersonalScore(player, winnerTeam)) }
    }.fold(mutableMapOf<String, Score>()) {
        acc, score ->
        acc.compute(score.player) { _, totalScore -> score + totalScore }.run { acc }
    }.values.toSet()
}
