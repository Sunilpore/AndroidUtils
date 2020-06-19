package com.myserviceeg;

import android.content.Context;
import android.util.Log;

public class LogHelper {

    private static final String TAG_DEBUG ="log_debug";
    private static final String TAG_ERROR ="log_error";
    private static final String TAG_CONTEXT ="log_context";

    static public void i(String tag, String Value) {
        System.out.println(tag + Value);
    }

    public static void d(String value){
        Log.d(TAG_DEBUG,value);
    }

    public static void e(String message) {
        Log.d(TAG_ERROR, "Error: " + message);
    }

    public static void c(Context context, String s) {
        Log.d(TAG_CONTEXT, String.format("%s\t%s", context.getClass().getCanonicalName(), s));
    }

}
