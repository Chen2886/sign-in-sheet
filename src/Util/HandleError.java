package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HandleError {
    private String className;
    private String functionName;
    private String errorMessage;
    private StackTraceElement[] stackTrace;
    private boolean ignoredError;

    public static void clear() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("error.log", false));
            bufferedWriter.write("");
            bufferedWriter.close();
        } catch (IOException ignored) {

        }
    }

    public HandleError(String className, String functionName, String errorMessage, StackTraceElement[] stackTrace, boolean ignoredError) {
        this.className = className;
        this.functionName = functionName;
        this.errorMessage = errorMessage;
        this.stackTrace = stackTrace;
        this.ignoredError = ignoredError;
        WriteToLog();
    }

    private void WriteToLog() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("error.log", true));
            bufferedWriter.write(this.toString());
            bufferedWriter.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public String toString() {
        String ignoredError =                 "****************!THIS IS AN IGNORED ERROR IN PROGRAM!***************\n";
        String startHeader =                  "=====================Start=====================\n";
        String classHeader =    String.format("Class Name:    %s\n", className);
        String functionHeader = String.format("Function Name: %s\n", functionName);
        String startErrorHeader =             "\nMessage    :\n";
        String startStackTrace =              "\nStack Trace:\n";
        String endHeader =                    "\n======================End=====================\n\n";

        if (this.ignoredError)
            return ignoredError + classHeader + functionHeader + startErrorHeader + errorMessage + startStackTrace +
                    stackTraceToString(stackTrace) + endHeader;
        else
            return startHeader + classHeader + functionHeader + startErrorHeader + errorMessage + startStackTrace +
                    stackTraceToString(stackTrace) + endHeader;
    }

    private String stackTraceToString(StackTraceElement[] stackTrace) {
        String returnVal = "";
        for (int i = 0; i < stackTrace.length; i++) {
            returnVal += stackTrace[i];
            if (i == stackTrace.length - 1) continue;
            returnVal += "\n";
        }
        return returnVal;
    }
}