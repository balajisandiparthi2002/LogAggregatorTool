package org.example;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Callable;

public class ProcessLogFiles implements Callable<String> {
    private String folderPathOfLogFiles;
    private String mergedAndSortedLogFile;

    public ProcessLogFiles(String folderPath, String mergedAndSortedLogFile) {
        this.folderPathOfLogFiles = folderPath;
        this.mergedAndSortedLogFile = mergedAndSortedLogFile;
    }

    public String processTheFile() throws IOException, NullPointerException {
        HashMap<String, ArrayList<String>> logData = new HashMap<>();
        File fileObjectOfFolder = new File(folderPathOfLogFiles);
        if (!fileObjectOfFolder.isDirectory()) {
            System.out.println("The provided path is not a directory.");
            return "Failure";
        }
        for (String logFileName : fileObjectOfFolder.list()) {
            if (new File(logFileName).isDirectory()) {
                System.out.println("There are some sub-directories in the provided path. Please ensure that only log files will be there.");
                return "Failure";
            } else if (!logFileName.endsWith(".log")) {
                System.out.println("Some or All of the files are in wrong format...");
                return "Failure";
            }
            BufferedReader file = new BufferedReader(new FileReader(folderPathOfLogFiles + "\\" + logFileName));
            String currentLine = file.readLine();
            String key = "";
            StringBuilder value = new StringBuilder();
            //for first iteration, we cannot store data, so taking a boolean variable
            boolean isFirstRecord = true;
            while (currentLine != null) {
                if (currentLine.length() >= 10) {
                    //checking timestamp for separating the records based on timestamp.
                    String temp = currentLine.substring(0, 10);
                    //matching the regex expression for the date format
                    if (temp.matches("[1-9][0-9][0-9]{2}/(0[1-9]|1[0-2])/([1-2][0-9]|0[1-9]|3[0-1])")) {
                        if (!isFirstRecord) {
                            if (!logData.containsKey(key)) {
                                logData.put(key, new ArrayList<>());
                            }
                            //Adding the data to the HashMap.
                            logData.get(key).add(value.toString());
                        }
                        //taking the whole timestamp as the key.
                        key = currentLine.substring(0, 23);
                        isFirstRecord = false;
                        value = new StringBuilder(currentLine.substring(23));
                    } else {
                        value.append(currentLine);
                    }
                } else {
                    value.append(currentLine);
                }
                currentLine = file.readLine();
            }
            if (!logData.containsKey(key)) {
                logData.put(key, new ArrayList<>());
            }
            logData.get(key).add(value.toString());
        }
        //creating a separate list of the timestamps to sort them.
        ArrayList<String> sortByTimestamp = new ArrayList<>(logData.keySet());
        Collections.sort(sortByTimestamp);
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(mergedAndSortedLogFile));
        for (String timestamp : sortByTimestamp) {
            for (String record : logData.get(timestamp)) {
                String currentData = "";
                currentData += timestamp;
                currentData += " ";
                currentData += record;
                currentData += "\n";
                //writing to the output file.
                outputFile.write(currentData);
            }
        }
        outputFile.close();
        return "Success";
    }

    @Override
    public String call() throws IOException {
        return processTheFile();
    }
}