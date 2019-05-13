package com.example.apinotification.utils.exception;

import android.content.Context;

public class CustomizedExceptionHandler implements Thread.UncaughtExceptionHandler {
//created

    private Thread.UncaughtExceptionHandler defaultUEH;
    //private String localPath;
    private Context mContext;

    public CustomizedExceptionHandler(/*String localPath,*/ Context context) {
        //this.localPath = localPath;
        //Getting the the default exception handler
        //that's executed when uncaught exception terminates a thread
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        /*final Writer stringBuffSync = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringBuffSync);
        throwable.printStackTrace(printWriter);
        String stacktrace = stringBuffSync.toString();
        printWriter.close();*/

        //if (localPath != null) {


        ExceptionUtils.writeCrashLogToFile(mContext, throwable);

        //}

//Used only to prevent from any code getting executed.
        // Not needed in this example
        defaultUEH.uncaughtException(thread, throwable);
    }


}
