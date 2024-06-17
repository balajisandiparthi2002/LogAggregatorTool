package org.LogAggregatorTool.validator;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import java.io.File;

/**
 * This class is used for validating files and folders based on the requirements.
 */
public class LogAggregatorValidator {
    /**
     * This method is used to check whether a file object is a directory or not.
     *
     * @param logFilesFolder    File object of the provided path.
     * @return true if it is a directory, otherwise, it returns false.
     */
    public boolean isDirectory(File logFilesFolder) {
        return logFilesFolder.isDirectory();
    }

    /**
     * This method is used to check whether a file object is a file or not.
     *
     * @param logFile    File object of the provided path.
     * @return true or false.
     */
    public boolean isValidFile(File logFile) {
        return logFile.isFile();
    }

    /**
     * This method checks whether the file ends with .log or .txt.
     *
     * @param logFileName    name of the file.
     * @return true or false.
     */
    public boolean endsWith(String logFileName) {
        return logFileName.endsWith(LogAggregatorConstants.LOG_FILE_EXTENSION) || logFileName.endsWith(LogAggregatorConstants.TEXT_FILE_EXTENSION);
    }
}
