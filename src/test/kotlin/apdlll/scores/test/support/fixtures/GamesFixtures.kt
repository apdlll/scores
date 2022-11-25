package apdlll.scores.test.support.fixtures

import apdlll.scores.model.Game
import apdlll.scores.model.GamePoints
import apdlll.scores.model.PlayerStats

internal val `players stats for game1` = listOf(
    PlayerStats("player1", "team1", "pos1", listOf(1, 2, 3, 4)),
    PlayerStats("player2", "team2", "pos2", listOf(2, 2, 2, 2)),
)

private val `players stats for game2` = listOf(
    PlayerStats("player1", "team2", "pos2", listOf(1, 2, 3, 4)),
    PlayerStats("player2", "team1", "pos1", listOf(1, 2, 3, 4)),
)

internal val `stats points for game1` = listOf(
    GamePoints("pos1", 10, listOf(4, 3, 2, 1)),
    GamePoints("pos2", 10, listOf(2, 2, 2, 2)),
)

private val `stats points for game2` = listOf(
    GamePoints("pos2", 5, listOf(2, -2, 2, 2)),
    GamePoints("pos1", 5, listOf(4, -3, 2, 1)),
)

internal val `empty stats points` = listOf(
    GamePoints("pos1", 10, emptyList()),
    GamePoints("pos2", 10, emptyList()),
)

internal val `players with empty stats` = listOf(
    PlayerStats("player1", "team1", "pos1", emptyList()),
    PlayerStats("player2", "team2", "pos2", emptyList()),
)

internal val `2 games, 2 players, 32vs34 score` = listOf(
    Game("game1", `players stats for game1`, `stats points for game1`),
    Game("game2", `players stats for game2`, `stats points for game2`, true, 1),
)
