package org.LogAggregatorTool;

import org.LogAggregatorTool.dto.LogAggregatorAuditData;
import org.LogAggregatorTool.jdbc.LogAggregatorAuditDataEntry;
import org.LogAggregatorTool.operations.LogFilesExecutor;
import org.LogAggregatorTool.constants.LogAggregatorConstants;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

public class LogAggregatorTool {

    public static void main(String[] args) {
        System.out.println(LogAggregatorConstants.WELCOME_MESSAGE);
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.ENTER_PATH_MESSAGE);
        // Taking the user input.
        LocalDateTime currentDateTime;
        String pathToLogFilesFolder = inputScanner.next();
        File logFilesFolder = new File(pathToLogFilesFolder);
        LogAggregatorAuditData logAggregatorAuditData=new LogAggregatorAuditData();
        LogAggregatorAuditDataEntry logAggregatorAuditDataEntry=new LogAggregatorAuditDataEntry();
        // if the path provided by the user is not valid, it iterates until they enter a valid path.
        while (!logFilesFolder.exists()) {
            System.out.println(LogAggregatorConstants.INVALID_PATH);
            pathToLogFilesFolder = inputScanner.next();
            if (pathToLogFilesFolder.equals(LogAggregatorConstants.TO_EXIT)) {
                logAggregatorAuditData.setResult(LogAggregatorConstants.FAILURE);
                logAggregatorAuditData.setErrorMessage(LogAggregatorConstants.USER_EXIT_MESSAGE);
                currentDateTime=LocalDateTime.now();
                logAggregatorAuditData.setDateTime(currentDateTime.toString());
                logAggregatorAuditData.setPathToTheFolder(LogAggregatorConstants.EMPTY_STRING);
                logAggregatorAuditData.setOutputFileName(LogAggregatorConstants.EMPTY_STRING);
                logAggregatorAuditData.setNamesOfLogFiles(LogAggregatorConstants.EMPTY_STRING);
                System.out.println(LogAggregatorConstants.EXIT_MESSAGE);
                logAggregatorAuditDataEntry.auditEntry(logAggregatorAuditData);
                return;
            }
            logFilesFolder = new File(pathToLogFilesFolder);
        }
        System.out.println(LogAggregatorConstants.ENTER_OUTPUT_FILE_PATH);
        String outputFilePath = inputScanner.next();
        System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.PROCESSING_MESSAGE);
        logAggregatorAuditData.setPathToTheFolder(pathToLogFilesFolder);
        logAggregatorAuditData.setNamesOfLogFiles(LogAggregatorConstants.EMPTY_STRING);
        logAggregatorAuditData.setOutputFileName(outputFilePath);
        LogFilesExecutor logFilesExecutor = new LogFilesExecutor();
        boolean logAggregatorToolResult = logFilesExecutor.executeLogFiles(pathToLogFilesFolder, outputFilePath,logAggregatorAuditData);
        if (logAggregatorToolResult) {
            logAggregatorAuditData.setResult(LogAggregatorConstants.SUCCESS);
            logAggregatorAuditData.setErrorMessage(LogAggregatorConstants.EMPTY_STRING);
            System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.SUCCESS_MESSAGE + outputFilePath);
        } else {
            logAggregatorAuditData.setOutputFileName(LogAggregatorConstants.EMPTY_STRING);
            logAggregatorAuditData.setResult(LogAggregatorConstants.FAILURE);
        }
        currentDateTime=LocalDateTime.now();
        logAggregatorAuditData.setDateTime(currentDateTime.toString());
        logAggregatorAuditDataEntry.auditEntry(logAggregatorAuditData);
        System.out.println(LogAggregatorConstants.NEW_LINE_CHAR + LogAggregatorConstants.THANK_YOU_MESSAGE);
    }
}
