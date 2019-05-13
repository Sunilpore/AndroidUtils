package com.example.apinotification.utils.exception;

import android.content.Context;

import com.example.apinotification.utils.constants.Constants;
import com.example.apinotification.utils.file_utils.FileUtils;
import com.example.apinotification.utils.helper.HelperMethods;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ExceptionUtils {
//created


    private static String directory_name_crash = Constants.PRIVATE_FILE.CRASH_LOG_DIR_NAME;
    private static String file_name_crash = Constants.PRIVATE_FILE.CRASH_LOG_FILE_NAME;


    /**
     * Write crash log in internal file
     *
     * @param throwable -> Exception content
     */
    public static void writeCrashLogToFile(Context context, Throwable throwable) {

        //Write a printable representation of this Throwable
        //The StringWriter gives the lock used to synchronize access to this writer.
        final Writer stringBuffSync = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringBuffSync);
        throwable.printStackTrace(printWriter);
        String stacktrace = stringBuffSync.toString();
        printWriter.close();


        File file_to_check = new File(context.getFilesDir(), directory_name_crash + File.separator + file_name_crash);

        if (file_to_check.exists())
            FileUtils.updateExistingFile(file_to_check, stacktrace);
        else {
            String crash_log_view = "VTS Crash Logs Data\n\n";
            boolean status_of_create = createNewCrashLogFile(context, crash_log_view);
            if (status_of_create) {
                if (file_to_check.exists())
                    FileUtils.updateExistingFile(file_to_check, stacktrace);
            }
        }
    }

    /**
     * Create new crash log file in internal application 'file' folder
     *
     * @param cash_log_data -> initializing app log file with start text
     * @return -> status of file create
     */
    private static boolean createNewCrashLogFile(Context context, String cash_log_data) {
        try {

            //String fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + directory_name + File.separator;

            File file_to_check = new File(context.getFilesDir(), directory_name_crash + File.separator + file_name_crash);
            if (!file_to_check.exists()) {
                File file = FileUtils.createPrivateFile(context, directory_name_crash, file_name_crash);

                //File file = new File(fileDir);
                //if (FileUtils.checkOrCreateDirectory(mContext, file)) {
                if (file != null) {
                    FileOutputStream fout = new FileOutputStream(file);
                    FilterOutputStream filter = new FilterOutputStream(fout);

                    byte b[] = cash_log_data.getBytes();//converting string into byte array
                    filter.write(b);
                    filter.flush();
                    fout.close();
                    filter.close();
                    HelperMethods.showLogData("success crash log file crate...");
                    if (file_to_check.exists())
                        return true;
                }
            }/* else {
                String content = "new Updated";

                FileUtils.updateExistingFile(file_to_check, content);
            }*/
        } catch (Exception e) {
            HelperMethods.showLogError("Exception: " + e.getMessage());
        }
        return false;
    }


}
