/**
 * Copyright (c) 2022, apdlll
 * All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package apdlll.scores.model

/**
 * Points given for a position in a game
 */
data class GamePoints(val position: String, val winPoints: Int, val statsPoints: List<Int>)
