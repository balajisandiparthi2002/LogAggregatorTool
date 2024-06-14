package org.LogAggregatorTool.operations;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import org.LogAggregatorTool.reader.LogFilesReader;
import org.LogAggregatorTool.writer.LogFilesWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class LogFilesProcessor {
    /**
     * This method processes the logfiles.
     *
     * @param pathToLogFilesFolder    it is the path to the folder where logfiles are located.
     * @param outputFilePath    it is the path, where all the data is to be written.
     * @return this method returns Success, if the reading of the files is success.Otherwise, it returns the errors that encountered.
     */
    public String processLogFiles(String pathToLogFilesFolder, String outputFilePath) {
        HashMap<String, ArrayList<String>> timestampToLogStatementMap = new HashMap<>();
        LogFilesReader logFilesReader = new LogFilesReader();
        boolean readingResult = logFilesReader.readLogFiles(pathToLogFilesFolder, timestampToLogStatementMap);

        //creating a separate list of the timestamps to sort them.
        ArrayList<String> sortedTimestampsList = new ArrayList<>(timestampToLogStatementMap.keySet());
        Collections.sort(sortedTimestampsList);

        LogFilesWriter logFilesWriter = new LogFilesWriter();
        try {
            logFilesWriter.writeToOutputFile(sortedTimestampsList, outputFilePath, timestampToLogStatementMap);
        } catch (IOException ioException) {
            System.out.println(LogAggregatorConstants.IO_EXCEPTION_MESSAGE + ioException);
        }
        if (readingResult) return LogAggregatorConstants.SUCCESS;
        return LogAggregatorConstants.FAILURE;
    }
}
