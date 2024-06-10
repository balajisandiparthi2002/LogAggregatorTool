package org.example;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LogAggregatorTool {

    public static void main(String[] args){
        System.out.println("Welcome to Log Aggregator Tool");
        System.out.println("Note : Ensure that all the paths you provide are valid.");
        Scanner input = new Scanner(System.in);
        System.out.println("\nPlease enter the path to a folder containing log files : ");
        //Taking the user input.
        String folderPathOfLogFiles=input.next();
        File fileObjectOfFolder=new File(folderPathOfLogFiles);
        if(!fileObjectOfFolder.exists()){
            //if the path provided by user does not exist, showing the message and returning from there.
            System.out.println("\nPlease provide a valid path to the folder.");
            return;
        }
        System.out.println("\nPlease enter the location where the output file should be stored, including the filename : ");
        String mergedAndSortedOutputFile=input.next();
        System.out.println("Processing...");
        ExecutorService logExecutor = Executors.newSingleThreadExecutor();
        ProcessLogFiles logFileProcess = new ProcessLogFiles(folderPathOfLogFiles, mergedAndSortedOutputFile);
        //Spawning a new thread for processing the files
        Future<String> message = logExecutor.submit(logFileProcess);
        String result="";
        try{
            //child thread returns either success/failure to the main thread
            result=message.get();
        }catch(InterruptedException | ExecutionException e){
            System.out.println("Exception : " + e);
        } finally{
            logExecutor.shutdown();
        }
        if(result.equals("Success")){
            System.out.println("\nSuccess!!!All the files are merged");
            System.out.println("The location of the output file is : " + mergedAndSortedOutputFile);
        }
    }
}