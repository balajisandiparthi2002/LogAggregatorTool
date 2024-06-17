package org.LogAggregatorTool.operations;

import org.LogAggregatorTool.constants.LogAggregatorConstants;
import org.LogAggregatorTool.dto.LogAggregatorAuditData;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class executes the log files using ExecutorService.
 * It uses Future to fetch the result from the child thread.
 */
public class LogFilesExecutor {
    /**
     * This method creates an executor to create and execute a new thread.
     *
     * @param pathToLogFilesFolder    it is the path to the folder where logfiles are located
     * @param outputFilePath    it is the path, where all the data is to be written
     * @return this method returns Success/Failure message back to the main thread.
     */
    public boolean executeLogFiles(String pathToLogFilesFolder, String outputFilePath, LogAggregatorAuditData logAggregatorAuditData) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //Spawning a new thread for processing the files.
        ThreadCreationInterface newThread = new ThreadCreation(pathToLogFilesFolder, outputFilePath, logAggregatorAuditData);
        Future<String> futureResult = executorService.submit(newThread);
        String threadOutputResult = LogAggregatorConstants.EMPTY_STRING;
        try {
            //child thread returns either success/failure to the main thread.
            threadOutputResult = futureResult.get();
        } catch (InterruptedException | ExecutionException exception) {
            logAggregatorAuditData.setErrorMessage(exception.getMessage());
            System.out.println(LogAggregatorConstants.EXCEPTION_MESSAGE + LogAggregatorConstants.NEW_LINE_CHAR + exception.getMessage());
        } finally {
            executorService.shutdown();
        }
        return threadOutputResult.equals(LogAggregatorConstants.SUCCESS);
    }
}
