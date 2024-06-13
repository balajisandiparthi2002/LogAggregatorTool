package org.LogAggregatorTool.validator;

import org.LogAggregatorTool.constants.LogAggregatorConstants;

import java.io.File;

public class LogAggregatorValidator {
    public boolean isDirectory(File logFilesFolder) {
        return logFilesFolder.isDirectory();
    }

    public boolean isValidFile(File logFile) {
        return logFile.isFile();
    }

    public boolean checkEndsWith(String logFileName) {
        return logFileName.endsWith(LogAggregatorConstants.LOG_FILE_EXTENSION) || logFileName.endsWith(LogAggregatorConstants.TEXT_FILE_EXTENSION);
    }
}
