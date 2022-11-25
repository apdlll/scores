/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.model

/**
 * A player's stats for a given team and position in a game
 */
data class PlayerStats(val name: String, val team: String, val position: String, val stats: List<Int>)
