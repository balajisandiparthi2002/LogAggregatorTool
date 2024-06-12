package org.LogAggregatorTool.operations;

import java.io.IOException;
import java.util.concurrent.Callable;

public class ThreadCreation implements Callable<String> {
    private String pathToLogFilesFolder;
    private String outputFilePath;

    public ThreadCreation(String pathToLogFilesFolder,String outputFilePath){
        this.pathToLogFilesFolder=pathToLogFilesFolder;
        this.outputFilePath=outputFilePath;
    }
    @Override
    public String call() throws IOException {
        LogFilesProcessor logFilesProcessor=new LogFilesProcessor();
        return logFilesProcessor.processLogFiles(pathToLogFilesFolder,outputFilePath);
    }
}
