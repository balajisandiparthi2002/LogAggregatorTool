package org.LogAggregatorTool.utility;

import java.io.File;

public class Validator {
    public boolean checkIsDirectory(File logFilesFolder){
        return logFilesFolder.isDirectory();
    }
    public boolean checkIsFile(File logFile){
        return logFile.isFile();
    }
    public boolean checkEndsWith(String logFileName){
        return logFileName.endsWith(".log");
    }
}
