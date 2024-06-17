package org.LogAggregatorTool.writer;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LogFilesWriter {
    /**
     * This method writes the final merged and sorted data into the output file.
     *
     * @param sortedTimestampsList    it is a list containing the timestamps in sorted order.
     * @param outputFilePath    it is the location of the output file where the data is to be written.
     * @param timestampToLogStatementMap    Map of log files data, Key : timestamp, Value : whole record.
     * @throws IOException    it throws IOException if there is any IOException while writing to the file.
     */
    public void writeToOutputFile(ArrayList<String> sortedTimestampsList, String outputFilePath, HashMap<String, ArrayList<String>> timestampToLogStatementMap) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFilePath));
        for (String timestamp : sortedTimestampsList) {
            for (String record : timestampToLogStatementMap.get(timestamp)) {
                StringBuilder currentData = new StringBuilder(LogAggregatorConstants.EMPTY_STRING);
                currentData.append(timestamp);
                currentData.append(LogAggregatorConstants.SPACE);
                currentData.append(record);
                currentData.append(LogAggregatorConstants.NEW_LINE_CHAR);
                //writing to the output file.
                bufferedWriter.write(currentData.toString());
            }
        }
        bufferedWriter.close();
    }
}
