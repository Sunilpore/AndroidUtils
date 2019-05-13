package com.example.apinotification.utils.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;


public final class PermissionUtils {

    public static String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static String READ_SMS = Manifest.permission.READ_SMS;
    public static String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    public static final String[] STORAGE_PERMISSIONS = {
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE
    };

    public static final String[] LOCATION_PERMISSIONS = {
            WRITE_EXTERNAL_STORAGE
    };


    public static boolean checkPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (!checkPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isPermissionsGranted(Context context, String... permissions) {
        return checkPermissions(context, permissions);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isStoragePermissionsGranted(Context context) {
        return checkPermissions(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    public static void requestPermissions(Object o, int requestCode, String... permissions) {
        if (o instanceof Activity) {
            ActivityCompat.requestPermissions((AppCompatActivity) o, permissions, requestCode);
        }
    }


    public static boolean isAllPermissionsGranted(int[] grantResults, int length) {
        for (int i = 0; i < length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}