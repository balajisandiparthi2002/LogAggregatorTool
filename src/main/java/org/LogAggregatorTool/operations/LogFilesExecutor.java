package org.LogAggregatorTool.operations;

import org.LogAggregatorTool.constants.LogAggregatorConstants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LogFilesExecutor {
    /**
     * This method creates an executor to create and execute a new thread.
     *
     * @param pathToLogFilesFolder it is the path to the folder where logfiles are located
     * @param outputFilePath       it is the path, where all the data is to be written
     * @return this method returns Success/Failure message back to the main thread.
     */
    public boolean executeLogFiles(String pathToLogFilesFolder, String outputFilePath) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ThreadCreationInterface newThread = new ThreadCreation(pathToLogFilesFolder, outputFilePath);
        //Spawning a new thread for processing the files
        Future<String> futureResult = executorService.submit(newThread);
        String threadOutputResult = LogAggregatorConstants.EMPTY_STRING;
        try {
            //child thread returns either success/failure to the main thread
            threadOutputResult = futureResult.get();
        } catch (Exception exception) {
            System.out.println(LogAggregatorConstants.EXCEPTION_MESSAGE + LogAggregatorConstants.SPACE + exception);
        } finally {
            executorService.shutdown();
        }
        return threadOutputResult.equals(LogAggregatorConstants.SUCCESS);
    }
}
