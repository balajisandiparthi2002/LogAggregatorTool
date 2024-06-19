package org.LogAggregatorTool.validator;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import java.io.File;

/**
 * This class validates the files and directory based on the requirements.
 */
public class LogAggregatorValidator {
    public boolean isDirectory(File logFilesFolder) {
        return logFilesFolder.isDirectory();
    }

    public boolean isValidFile(File logFile) {
        return logFile.isFile();
    }

    public boolean endsWith(String logFileName) {
        return logFileName.endsWith(LogAggregatorConstants.LOG_FILE_EXTENSION) || logFileName.endsWith(LogAggregatorConstants.TEXT_FILE_EXTENSION);
    }
}
