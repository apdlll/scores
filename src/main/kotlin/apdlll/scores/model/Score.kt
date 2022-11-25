/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.model

/**
 * Contains the final score
 */
data class Score(val player: String, val points: Int) {

    override fun equals(other: Any?) = this === other || (other is Score && player == other.player)
    override fun hashCode() = player.hashCode()
}
