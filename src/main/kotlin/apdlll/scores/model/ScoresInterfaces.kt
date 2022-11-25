package apdlll.scores.model

/**
 * This interface allows creating different solutions by combining different implementations for each stage
 */
interface ScoresInterface : ReadInterface, CalcInterface, WriteInterface

interface ReadInterface {
    /**
     * Reads the games data including players stats for each game and the points given for each stat
     * @param args config needed to read the data.
     *             It might be a URL, a file, a directory, a DB connection, etc. or nothing,
     *             whatever is needed to configure the underlying implementation
     * @return the list of games played containing players stats and points given for each stat
     */
    fun readGames(args: List<Any>): List<Game>
}

interface CalcInterface {
    /**
     * Computes the scores from the given games data
     * @param gamesData here will be provided the return value of `ReadInterface.readGames`
     * @return the list of players with their final scores
     */
    fun calculateScores(gamesData: List<Game>): Set<Score>
}

interface WriteInterface {
    /**
     * Writes the given scores in the desired format (i.e. JSON, PDF, HTML, text, etc.)
     * and to the desired channel (standard output, local disk, FTP, DB, etc.)
     * @param scores here will be provided the return value of `CalcInterface.calculateScores`
     * @param args config needed to write the scores
     */
    fun writeScores(scores: Set<Score>, args: List<Any>)
    /**
     * Same as in `writeScores` but for errors
     * @param error exception that was thrown during the process
     * @param args same as in `writeScores`
     */
    fun writeError(error: Throwable, args: List<Any>)
}
