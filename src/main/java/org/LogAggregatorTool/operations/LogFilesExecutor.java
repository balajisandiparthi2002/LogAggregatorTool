package org.LogAggregatorTool.operations;

import org.LogAggregatorTool.utility.Constants;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LogFilesExecutor {
    /**
     * This method creates an executor to create and execute a new thread.
     * @param pathToLogFilesFolder it is the path to the folder where logfiles are located
     * @param outputFilePath it is the path, where all the data is to be written
     * @return this method returns Success/Failure message back to the main thread.
     */
    public String executeLogFiles(String pathToLogFilesFolder, String outputFilePath){
        ExecutorService logFilesExecutor = Executors.newSingleThreadExecutor();
        ThreadCreation newThread=new ThreadCreation(pathToLogFilesFolder,outputFilePath);
        //Spawning a new thread for processing the files
        Future<String> message = logFilesExecutor.submit(newThread);
        String result = Constants.EMPTY_STRING;
        try{
            //child thread returns either success/failure to the main thread
            result=message.get();
        }catch(Exception exception){
            System.out.println("Exception : " + exception);
        }finally {
            logFilesExecutor.shutdown();
        }
        return result;
    }
}
