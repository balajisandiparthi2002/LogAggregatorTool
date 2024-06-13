package org.LogAggregatorTool.reader;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import org.LogAggregatorTool.validator.LogAggregatorValidator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LogFilesReader {
    /**
     * This method reads the logfiles and adds the data into the hashmap.
     * It checks for different validations and if they are not valid, the flow of the program returns from there.
     *
     * @param pathToLogFilesFolder    it is the path to the folder where logfiles are located.
     * @param timestampToLogStatementMap    it is a map which stores timestamps as keys and records as values.
     * @return    if there are no errors, it returns Success.Otherwise, it returns the error message.
     */
    public boolean readLogFiles(String pathToLogFilesFolder, HashMap<String, ArrayList<String>> timestampToLogStatementMap) {
        File logFilesFolder = new File(pathToLogFilesFolder);
        LogAggregatorValidator pathValidator = new LogAggregatorValidator();
        if (!pathValidator.isDirectory(logFilesFolder)) {
            System.out.println(logFilesFolder + LogAggregatorConstants.INVALID_DIRECTORY);
            return false;
        }
        for (String logFileName : logFilesFolder.list()) {
            if (!pathValidator.isValidFile(new File(logFilesFolder + LogAggregatorConstants.BACK_SLASH + logFileName))) {
                System.out.println((logFilesFolder + LogAggregatorConstants.BACK_SLASH + logFileName) + LogAggregatorConstants.INVALID_FILE);
                return false;
            }
            if (!pathValidator.checkEndsWith(logFileName)) {
                System.out.println(logFileName + LogAggregatorConstants.INVALID_FILE_FORMAT);
                return false;
            }
            String currentLogFilePath = pathToLogFilesFolder + LogAggregatorConstants.BACK_SLASH + logFileName;
            try {
                BufferedReader logFileBufferedReader = new BufferedReader(new FileReader(currentLogFilePath));
                String currentLineInLogFile = logFileBufferedReader.readLine();
                String currentTimestamp = LogAggregatorConstants.EMPTY_STRING;
                StringBuilder currentRecord = new StringBuilder();
                //for first iteration, we cannot store data, so took a boolean variable
                boolean isFirstRecord = true;
                while (currentLineInLogFile != null) {
                    if (currentLineInLogFile.length() >= LogAggregatorConstants.LENGTH_OF_DATE) {
                        //checking timestamp for separating the records based on timestamp.
                        String currentDate = currentLineInLogFile.substring(LogAggregatorConstants.ZERO, LogAggregatorConstants.LENGTH_OF_DATE);
                        //matching the regex expression for the date format
                        if (currentDate.matches(LogAggregatorConstants.REGEX_TO_MATCH_DATE_FORMAT)) {
                            if (!isFirstRecord) {
                                if (!timestampToLogStatementMap.containsKey(currentTimestamp)) {
                                    timestampToLogStatementMap.put(currentTimestamp, new ArrayList<>());
                                }
                                //Adding the data to the HashMap.
                                timestampToLogStatementMap.get(currentTimestamp).add(currentRecord.toString());
                            }
                            //taking the whole timestamp as the key.
                            currentTimestamp = currentLineInLogFile.substring(LogAggregatorConstants.ZERO, LogAggregatorConstants.LENGTH_OF_TIMESTAMP);
                            isFirstRecord = false;
                            currentRecord = new StringBuilder(currentLineInLogFile.substring(LogAggregatorConstants.LENGTH_OF_TIMESTAMP));
                        } else {
                            currentRecord.append(currentLineInLogFile);
                        }
                    } else {
                        currentRecord.append(currentLineInLogFile);
                    }
                    currentLineInLogFile = logFileBufferedReader.readLine();
                }
                if (!timestampToLogStatementMap.containsKey(currentTimestamp)) {
                    timestampToLogStatementMap.put(currentTimestamp, new ArrayList<>());
                }
                timestampToLogStatementMap.get(currentTimestamp).add(currentRecord.toString());
            } catch (IOException exception) {
                System.out.println(LogAggregatorConstants.IO_EXCEPTION_MESSAGE + exception);
            }
        }
        return true;
    }
}
