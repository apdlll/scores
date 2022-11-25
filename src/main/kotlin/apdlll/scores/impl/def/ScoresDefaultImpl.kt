package apdlll.scores.impl.def

import apdlll.scores.model.CalcInterface
import apdlll.scores.model.ReadInterface
import apdlll.scores.model.ScoresInterface
import apdlll.scores.model.WriteInterface

/**
 * This is the default implementation using CSV files to read the games' data and
 * writing the scores to the standard output in JSON format.
 */
class ScoresDefaultImpl :
    ScoresInterface,
    ReadInterface by CsvFilesReadImpl(),
    CalcInterface by CalcDefaultImpl(),
    WriteInterface by StdOutJsonWriteImpl()
