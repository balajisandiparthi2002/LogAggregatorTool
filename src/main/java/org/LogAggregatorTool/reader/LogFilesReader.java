package org.LogAggregatorTool.reader;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import org.LogAggregatorTool.dto.LogAggregatorAuditData;
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
     * @return if there are no errors, it returns Success.Otherwise, it returns the error message.
     */
    public boolean readLogFiles(String pathToLogFilesFolder, HashMap<String, ArrayList<String>> timestampToLogStatementMap, LogAggregatorAuditData logAggregatorAuditData) {
        File logFilesFolder = new File(pathToLogFilesFolder);
        LogAggregatorValidator pathValidator = new LogAggregatorValidator();
        if (!pathValidator.isDirectory(logFilesFolder)) {
            logAggregatorAuditData.setErrorMessage(LogAggregatorConstants.INVALID_DIRECTORY);
            System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.FAILURE_MESSAGE + logFilesFolder + LogAggregatorConstants.INVALID_DIRECTORY_MESSAGE);
            return false;
        }
        int numberOfLogFiles = LogAggregatorConstants.DEFAULT_INT_VALUE;
        StringBuilder namesOfLogFiles = new StringBuilder(LogAggregatorConstants.EMPTY_STRING);
        for (String logFileName : logFilesFolder.list()) {
            if (!pathValidator.isValidFile(new File(logFilesFolder + LogAggregatorConstants.BACK_SLASH + logFileName))) {
                logAggregatorAuditData.setErrorMessage(LogAggregatorConstants.INVALID_FILE);
                System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.FAILURE_MESSAGE + (logFilesFolder + LogAggregatorConstants.BACK_SLASH + logFileName) + LogAggregatorConstants.INVALID_FILE_MESSAGE);
                return false;
            }
            if (!pathValidator.endsWith(logFileName)) {
                logAggregatorAuditData.setErrorMessage(LogAggregatorConstants.INVALID_FILE_FORMAT);
                System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.FAILURE_MESSAGE + logFileName + LogAggregatorConstants.INVALID_FILE_FORMAT_MESSAGE);
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
                        String currentDate = currentLineInLogFile.substring(LogAggregatorConstants.DEFAULT_INT_VALUE, LogAggregatorConstants.LENGTH_OF_DATE);
                        //matching the regex expression for the date format
                        if (currentDate.matches(LogAggregatorConstants.REGEX_TO_MATCH_DATE_FORMAT) || currentDate.matches(LogAggregatorConstants.REGEX_TO_MATCH_NEW_DATE_FORMAT)) {
                            if (!isFirstRecord) {
                                if (!timestampToLogStatementMap.containsKey(currentTimestamp)) {
                                    timestampToLogStatementMap.put(currentTimestamp, new ArrayList<>());
                                }
                                //Adding the data to the HashMap.
                                timestampToLogStatementMap.get(currentTimestamp).add(currentRecord.toString());
                            }
                            //taking the whole timestamp as the key.
                            currentTimestamp = currentLineInLogFile.substring(LogAggregatorConstants.DEFAULT_INT_VALUE, LogAggregatorConstants.LENGTH_OF_TIMESTAMP);
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
                System.out.println(LogAggregatorConstants.IO_EXCEPTION_MESSAGE + exception.getMessage());
            }
            if (numberOfLogFiles == LogAggregatorConstants.DEFAULT_INT_VALUE)
                namesOfLogFiles = new StringBuilder(logFileName);
            else
                namesOfLogFiles.append(LogAggregatorConstants.COMA).append(logFileName);
            numberOfLogFiles++;
        }
        logAggregatorAuditData.setNumberOfLogFiles(numberOfLogFiles);
        logAggregatorAuditData.setNamesOfLogFiles(namesOfLogFiles.toString());
        return true;
    }
}
