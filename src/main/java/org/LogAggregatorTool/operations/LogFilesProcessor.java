package org.LogAggregatorTool.operations;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import org.LogAggregatorTool.dto.LogAggregatorAuditData;
import org.LogAggregatorTool.reader.LogFilesReader;
import org.LogAggregatorTool.writer.LogFilesWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LogFilesProcessor {
    /**
     * This method processes the logfiles.
     * It reads, sorts and then writes the logfiles in a sorted order into another log file.
     *
     * @param pathToLogFilesFolder    it is the path to the folder where logfiles are located.
     * @param outputFilePath    it is the path, where all the data is to be written.
     * @return this method returns Success, if the reading of the files is success.Otherwise, it returns the errors that encountered.
     */

    public String processLogFiles(String pathToLogFilesFolder, String outputFilePath, LogAggregatorAuditData logAggregatorAuditData) {
        HashMap<String, ArrayList<String>> timestampToLogStatementMap = new HashMap<>();
        LogFilesReader logFilesReader = new LogFilesReader();
        boolean readingResult = logFilesReader.readLogFiles(pathToLogFilesFolder, timestampToLogStatementMap, logAggregatorAuditData);

        //creating a separate list of the timestamps to sort them.
        ArrayList<String> sortedTimestampsList = new ArrayList<>(timestampToLogStatementMap.keySet());
        TimestampSorter timestampSorter = new TimestampSorter();
        timestampSorter.sortTimeStamps(sortedTimestampsList);

        //writing the merged and sorted timestamps and logs to an output file.
        LogFilesWriter logFilesWriter = new LogFilesWriter();
        try {
            logFilesWriter.writeToOutputFile(sortedTimestampsList, outputFilePath, timestampToLogStatementMap);
        } catch (IOException ioException) {
            logAggregatorAuditData.setErrorMessage(ioException.getMessage());
            System.out.println(LogAggregatorConstants.IO_EXCEPTION_MESSAGE + ioException.getMessage());
        }
        if (readingResult) return LogAggregatorConstants.SUCCESS;
        return LogAggregatorConstants.FAILURE;
    }
}
