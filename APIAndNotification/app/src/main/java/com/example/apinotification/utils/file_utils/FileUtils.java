package com.example.apinotification.utils.file_utils;

import android.content.Context;


import com.example.apinotification.utils.exception.ExceptionUtils;
import com.example.apinotification.utils.helper.HelperMethods;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created on 11-11-2018.
 */
public class FileUtils {

    //created

    /**
     * Create a Directory if not exists
     *
     * @param file -> file of directory along with directory name
     * @return -> status of existing directory
     */
    public static boolean checkOrCreateDirectory(Context context, File file/*, String pathOfDirectory*/) {

        if (file != null) {
            try {
                //file = new File(pathOfDirectory);

                if (file.mkdir()) {
                    HelperMethods.showToastShort(context, "New File Created!");
                    return true;
                } else {
                    if (!file.exists()) {
                        boolean status = file.mkdirs();
                        if (status) {
                            HelperMethods.showLogData("New File Created!");
                            return true;
                        } else {
                            HelperMethods.showLogData("Error creating new File!");
                            return false;
                        }
                    } else {
                        HelperMethods.showLogData("New File is Already Created!");
                        return true;
                    }

                }

            } catch (Exception e) {
                HelperMethods.showLogData("Error: " + e.getMessage());
                return false;
            }
        } else {
            return false;
        }

    }

    //Generating Unique File Name
    public static String getUniqueFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "PDF_" + timeStamp + "_.pdf";
    }

    /**
     * Create a private Directory in app
     *
     * @return -> will return File object of app new Private directory
     */
    public static File createPrivateFile(Context context, String PRIVATE_DIR_NAME, String FILE_NAME) {
        try {
            String directory_path = PRIVATE_DIR_NAME + File.separator;
            File file = new File(context.getFilesDir(), directory_path);
            if (checkOrCreateDirectory(context, file)) {
                File private_file = new File(context.getFilesDir(), directory_path + FILE_NAME);
                if (!private_file.exists()) {
                    if (private_file.createNewFile()) {
                        return private_file;
                    } else {
                        return null;
                    }
                } else {
                    return private_file;
                }
            } else
                return null;
        } catch (IOException e) {
            HelperMethods.showLogError("IOException: " + e.getMessage());
            return null;
        }
    }

    /**
     * Update existing file content i.e. append new content in file
     *
     * @param file_to_check     -> file of which content will be updated
     * @param content_to_update -> content to append in file
     * @return -> status of updating the file
     */
    public static boolean updateExistingFile(File file_to_check, String content_to_update) {
        if (file_to_check != null && file_to_check.exists()) {
            try {
                Writer output = null;
                output = new BufferedWriter(new FileWriter(file_to_check, true));
                output.write("\n\n---------------------------  Date Tme: (" + new Date().toString() + ")  ----------------------------\n"
                        + content_to_update +
                        "\n----------------------------------------------------------------------------------------------------------\n");
                output.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else return false;
    }

    public static boolean writeToFile(Context context, String content_to_write, File file_to_write) {
        if (file_to_write != null) {
            //File file = new File(fileDir);
            //if (FileUtils.checkOrCreateDirectory(context, file)) {
            try {
                FileOutputStream fout = null;

                fout = new FileOutputStream(file_to_write);

                FilterOutputStream filter = new FilterOutputStream(fout);

                byte b[] = content_to_write.getBytes();//converting string into byte array
                filter.write(b);
                filter.flush();
                fout.close();
                filter.close();
                HelperMethods.showLogData("success...");
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ExceptionUtils.writeCrashLogToFile(context, e);
            } catch (IOException e) {
                e.printStackTrace();
                ExceptionUtils.writeCrashLogToFile(context, e);
            }
        }

        return false;
    }

    /**
     * Get content of file in String format
     *
     * @param data_of_file -> file in which data_of_file is present
     * @return file content
     */
    public static String getJsonViewData(Context context, File data_of_file) {
        FileInputStream file = null;
        FilterInputStream filter = null;
        if (data_of_file.exists()) {
            try {
                file = new FileInputStream(data_of_file);

                filter = new BufferedInputStream(file);
                int k = 0;
                StringBuilder json_data = new StringBuilder();
                while ((k = filter.read()) != -1) {
                    json_data.append(String.valueOf((char) k));
                }
                file.close();
                filter.close();
                return json_data.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                HelperMethods.showLogError("FileNotFoundException:c " + e.getMessage());
                ExceptionUtils.writeCrashLogToFile(context, e);
            } catch (IOException e) {
                e.printStackTrace();
                HelperMethods.showLogError("IOException: " + e.getMessage());
                ExceptionUtils.writeCrashLogToFile(context, e);
            }
        }

        return "";
    }

}
