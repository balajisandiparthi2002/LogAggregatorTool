package org.LogAggregatorTool.dto;

public class LogAggregatorAuditData {
    private String pathToTheFolder;
    private int numberOfLogFiles;
    private String namesOfLogFiles;
    private String dateTime;
    private String result;
    private String outputFileName;
    private String errorMessage;

    public String getResult() {
        return result;
    }

    public String getPathToTheFolder() {
        return pathToTheFolder;
    }

    public int getNumberOfLogFiles() {
        return numberOfLogFiles;
    }

    public String getNamesOfLogFiles() {
        return namesOfLogFiles;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setPathToTheFolder(String pathToTheFolder) {
        this.pathToTheFolder = pathToTheFolder;
    }

    public void setNumberOfLogFiles(int numberOfLogFiles) {
        this.numberOfLogFiles = numberOfLogFiles;
    }

    public void setNamesOfLogFiles(String namesOfLogFiles) {
        this.namesOfLogFiles = namesOfLogFiles;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "AuditData{" +
                "pathToTheFolder='" + pathToTheFolder + '\'' +
                ", numberOfFiles=" + numberOfLogFiles +
                ", namesOfFiles='" + namesOfLogFiles + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", result='" + result + '\'' +
                ", outputFileName='" + outputFileName + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
