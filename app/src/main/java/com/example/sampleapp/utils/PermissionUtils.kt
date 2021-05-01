package com.example.sampleapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionUtils {

    companion object {

        val CAMERA_REQ_CODE = 31
        val CAMERA_AND_STORAGE_REQ_CODE = 32
        val MANAGE_FILE_REQ_CODE = 33

//------------------------------------------------------------------------------------------------------//

        val READ_EXTERNAL_STORAGE: String = Manifest.permission.READ_EXTERNAL_STORAGE
        val WRITE_EXTERNAL_STORAGE: String = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val ACCESS_COARSE_LOCATION: String = Manifest.permission.ACCESS_COARSE_LOCATION
        val ACCESS_FINE_LOCATION: String = Manifest.permission.ACCESS_FINE_LOCATION
        val CAMERA: String = Manifest.permission.CAMERA

//------------------------------------------------------------------------------------------------------//
        val STORAGE_PERMISSIONS = arrayOf(
                READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE
        )

        val CAMERA_AND_STORAGE_PERMISSIONS = arrayOf(
                READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA
        )

        private val LOCATION_PERMISSTIONS = arrayOf(
                ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION
        )

//------------------------------------------------------------------------------------------------------//
        fun checkPermissions(ctx: Context, vararg permissions: String): Boolean {
            for (per in permissions) {
                if (!checkPermision(ctx, per))
                    return false
            }
            return true
        }

        fun checkPermision(ctx: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                    ctx,
                    permission
            ) == PackageManager.PERMISSION_GRANTED
        }


        fun isPermissionsGranted(ctx: Context, vararg permissions: String): Boolean {
            return checkPermissions(ctx, *permissions)
        }

        fun isAllPermissionsGranted(grantResults: IntArray, lenght: Int): Boolean {
            for (res in grantResults) {
                if (res != PackageManager.PERMISSION_GRANTED)
                    return false
            }
            return true
        }


        fun requestPermissions(obj: Any, requestCode: Int, vararg permissions: String) {
            if (obj is Activity) {
                //For Activity
                ActivityCompat.requestPermissions(obj, permissions, requestCode)
            } else {
                //For fragment
                requestPermissions(permissions, requestCode)
            }

        }

//------------------------------------------------------------------------------------------------------//
        //Write functions for particular permission call

        fun isStoragePermissionsGranted(ctx: Context): Boolean {
            return checkPermissions(ctx, *STORAGE_PERMISSIONS)
        }

        fun isCameraPermisssionGranted(ctx: Context): Boolean {
            return checkPermissions(ctx, CAMERA)
        }

        fun isCameraAndStoragePermissionGranted(ctx: Context): Boolean {
            return checkPermissions(ctx, *CAMERA_AND_STORAGE_PERMISSIONS)
        }

        fun isLocationPermissionGranted(ctx: Context): Boolean {
            return checkPermissions(ctx, *LOCATION_PERMISSTIONS)
        }
//------------------------------------------------------------------------------------------------------//

        fun requestCameraPermission(ctx: Context,requestCode: Int=CAMERA_REQ_CODE){
            requestPermissions(ctx,requestCode,CAMERA)
        }

        fun requestCameraAndStoragePermission(ctx: Context,requestCode: Int=CAMERA_AND_STORAGE_REQ_CODE){
            requestPermissions(ctx,requestCode,*CAMERA_AND_STORAGE_PERMISSIONS)
        }

//------------------------------------------------------------------------------------------------------//
        fun isExternalStorageManager():Boolean {
            return Environment.isExternalStorageManager()
        }

        fun requestFileManagePermission(activity: Activity,requestCode: Int=MANAGE_FILE_REQ_CODE){
            try {
                val intent: Intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(java.lang.String.format("package:%s", activity.getApplicationContext()?.getPackageName()))
                activity.startActivityForResult(intent, requestCode)
            } catch (e:Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                activity.startActivityForResult(intent, requestCode)
            }
        }

    }
}