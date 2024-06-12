package org.LogAggregatorTool.writer;

import org.LogAggregatorTool.utility.Constants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LogFilesWriter {
    public void writeToOutputFile(ArrayList<String> sortedTimestampsList, String outputFilePath, HashMap<String, ArrayList<String>> timestampToLogStatementMap) throws IOException {
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(outputFilePath));
        for (String timestamp : sortedTimestampsList) {
            for (String record : timestampToLogStatementMap.get(timestamp)) {
                StringBuilder currentData = new StringBuilder(Constants.EMPTY_STRING);
                currentData.append(timestamp);
                currentData.append(Constants.SPACE);
                currentData.append(record);
                currentData.append(Constants.NEW_LINE);
                //writing to the output file.
                outputFile.write(currentData.toString());
            }
        }
        outputFile.close();
    }
}
