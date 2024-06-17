package org.LogAggregatorTool;

import org.LogAggregatorTool.operations.LogFilesExecutor;
import org.LogAggregatorTool.constants.LogAggregatorConstants;
import java.io.File;
import java.util.Scanner;

public class LogAggregatorTool {

    public static void main(String[] args) {
        System.out.println(LogAggregatorConstants.WELCOME_MESSAGE);
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.ENTER_PATH_MESSAGE);
        // Taking the user input.
        String pathToLogFilesFolder = inputScanner.next();
        File logFilesFolder = new File(pathToLogFilesFolder);
        // if the path provided by the user is not valid, it iterates until they enter a valid path.
        while (!logFilesFolder.exists()) {
            System.out.println(LogAggregatorConstants.INVALID_PATH);
            pathToLogFilesFolder = inputScanner.next();
            if (pathToLogFilesFolder.equals(LogAggregatorConstants.TO_EXIT)) {
                System.out.println(LogAggregatorConstants.EXIT_MESSAGE);
                return;
            }
            logFilesFolder = new File(pathToLogFilesFolder);
        }
        System.out.println(LogAggregatorConstants.ENTER_OUTPUT_FILE_PATH);
        String outputLogFilePath = inputScanner.next();
        System.out.println(LogAggregatorConstants.PROCESSING_MESSAGE);
        LogFilesExecutor logFilesExecutor = new LogFilesExecutor();
        boolean LogAggregatorToolResult = logFilesExecutor.executeLogFiles(pathToLogFilesFolder, outputLogFilePath);
        if (LogAggregatorToolResult) {
            System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.SUCCESS_MESSAGE + outputLogFilePath);
        } else {
            System.out.println(LogAggregatorConstants.FAILURE);
        }
        System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.THANK_YOU_MESSAGE);
    }
}
