package org.LogAggregatorTool.operations;

import org.LogAggregatorTool.dto.LogAggregatorAuditData;
import java.util.concurrent.Callable;

/**
 * This is an interface which is implemented by ThreadCreation class to create a thread.
 */
interface ThreadCreationInterface extends Callable<String> {
    String call();
}

/**
 * This Class implements the ThreadCreationInterface.
 * It has the implementation for the call() method.
 */
public class ThreadCreation implements ThreadCreationInterface {
    private String pathToLogFilesFolder;
    private String outputFilePath;
    private LogAggregatorAuditData logAggregatorAuditData;

    public ThreadCreation(String pathToLogFilesFolder, String outputFilePath, LogAggregatorAuditData logAggregatorAuditData) {
        this.pathToLogFilesFolder = pathToLogFilesFolder;
        this.outputFilePath = outputFilePath;
        this.logAggregatorAuditData=logAggregatorAuditData;
    }

    @Override
    public String call() {
        LogFilesProcessor logFilesProcessor = new LogFilesProcessor();
        return logFilesProcessor.processLogFiles(pathToLogFilesFolder, outputFilePath, logAggregatorAuditData);
    }
}
