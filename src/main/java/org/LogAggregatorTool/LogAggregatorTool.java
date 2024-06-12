package org.LogAggregatorTool;

import org.LogAggregatorTool.operations.LogFilesExecutor;
import org.LogAggregatorTool.utility.Constants;
import java.io.File;
import java.util.Scanner;

public class LogAggregatorTool {

    public static void main(String[] args){
        System.out.println(Constants.WELCOME_MESSAGE);
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(Constants.NEW_LINE + Constants.ENTER_PATH_MESSAGE);
        // Taking the user input.
        String pathToLogFilesFolder=inputScanner.next();
        File fileObjectOfPathToFolder=new File(pathToLogFilesFolder);
        // if the path provided by the user is not valid, it iterates until they enter a valid path.
        while(!fileObjectOfPathToFolder.exists()){
            System.out.println(Constants.PATH_DOES_NOT_EXIST_MESSAGE);
            pathToLogFilesFolder=inputScanner.next();
            if(pathToLogFilesFolder.equals("e")){
                System.out.println(Constants.EXIT_MESSAGE);
                return;
            }
            fileObjectOfPathToFolder=new File(pathToLogFilesFolder);
        }
        System.out.println(Constants.ENTER_OUTPUT_FILE_PATH);
        String outputFilePath=inputScanner.next();
        System.out.println(Constants.PROCESSING_MESSAGE);
        LogFilesExecutor logFilesExecutor=new LogFilesExecutor();
        String overallResult = logFilesExecutor.executeLogFiles(pathToLogFilesFolder,outputFilePath);
        if(overallResult.equals(Constants.SUCCESS)){
            System.out.println(Constants.NEW_LINE + "Success!!!All the files are merged");
            System.out.println("The location of the output file is : " + outputFilePath);
        }
        else{
            System.out.println("Failure : " + overallResult);
        }
        System.out.println(Constants.NEW_LINE + Constants.THANK_YOU_MESSAGE);
    }
}
