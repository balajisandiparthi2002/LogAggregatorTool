package org.LogAggregatorTool.operations;

import java.util.concurrent.Callable;

/**
 * This is an interface to create the instance of ThreadCreation to create a new thread.
 */
interface ThreadCreationInterface extends Callable<String> {
    String call();
}

public class ThreadCreation implements ThreadCreationInterface {
    private String pathToLogFilesFolder;
    private String outputFilePath;

    public ThreadCreation(String pathToLogFilesFolder, String outputFilePath) {
        this.pathToLogFilesFolder = pathToLogFilesFolder;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public String call() {
        LogFilesProcessor logFilesProcessor = new LogFilesProcessor();
        return logFilesProcessor.processLogFiles(pathToLogFilesFolder, outputFilePath);
    }
}
