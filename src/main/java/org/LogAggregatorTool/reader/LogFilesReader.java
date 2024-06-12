package org.LogAggregatorTool.reader;

import org.LogAggregatorTool.utility.Constants;
import org.LogAggregatorTool.utility.Validator;
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
     * @param pathToLogFilesFolder it is the path to the folder where logfiles are located.
     * @param timestampToLogStatementMap it is the path, where all the data is to be written.
     * @return if there are no errors, it returns Success.Otherwise, it returns the error message.
     * @throws IOException throws IOException, if there is any IOException while reading the files.
     */
    public String readLogFiles(String pathToLogFilesFolder, HashMap<String,ArrayList<String>> timestampToLogStatementMap) throws IOException {
        File logFilesFolder = new File(pathToLogFilesFolder);
        Validator pathValidator=new Validator();
        if (!pathValidator.checkIsDirectory(logFilesFolder)) {
            return Constants.NOT_A_DIRECTORY;
        }
        for (String logFileName : logFilesFolder.list()) {
            if (!pathValidator.checkIsFile(new File(logFilesFolder + Constants.BACK_SLASH + logFileName))) {
                return logFileName + Constants.NOT_A_FILE;
            } else if (!pathValidator.checkEndsWith(logFileName)) {
                return logFileName + Constants.WRONG_FILE_FORMAT;
            }
            String currentLogFilePath=pathToLogFilesFolder + Constants.BACK_SLASH + logFileName;
            BufferedReader logFileBufferedReader = new BufferedReader(new FileReader(currentLogFilePath));
            String currentLineInLogFile = logFileBufferedReader.readLine();
            String currentTimestamp = Constants.EMPTY_STRING;
            StringBuilder currentRecord= new StringBuilder();
            //for first iteration, we cannot store data, so took a boolean variable
            boolean isFirstRecord = true;
            while (currentLineInLogFile != null) {
                if (currentLineInLogFile.length() >= Constants.LENGTH_OF_DATE) {
                    //checking timestamp for separating the records based on timestamp.
                    String currentDate = currentLineInLogFile.substring(Constants.ZERO, Constants.LENGTH_OF_DATE);
                    //matching the regex expression for the date format
                    if (currentDate.matches(Constants.REGEX_TO_MATCH_DATE_FORMAT)) {
                        if (!isFirstRecord) {
                            if (!timestampToLogStatementMap.containsKey(currentTimestamp)) {
                                timestampToLogStatementMap.put(currentTimestamp, new ArrayList<>());
                            }
                            //Adding the data to the HashMap.
                            timestampToLogStatementMap.get(currentTimestamp).add(currentRecord.toString());
                        }
                        //taking the whole timestamp as the key.
                        currentTimestamp = currentLineInLogFile.substring(Constants.ZERO, Constants.LENGTH_OF_TIMESTAMP);
                        isFirstRecord = false;
                        currentRecord = new StringBuilder(currentLineInLogFile.substring(Constants.LENGTH_OF_TIMESTAMP));
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
        }
        return Constants.SUCCESS;
    }
}
