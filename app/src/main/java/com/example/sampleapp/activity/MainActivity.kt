package com.example.sampleapp.activity

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapp.BuildConfig
import com.example.sampleapp.R
import com.example.sampleapp.utils.FileSupportUtils
import com.example.sampleapp.utils.PermissionUtils
import com.example.sampleapp.utils.compressImageFile
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.OutputStream
import java.util.*

private const val REQ_CAPTURE = 100

private const val REQUEST_CODE_CAPTURE_IMAGE = 200
val CUSTOM_IMAGE = "CUSTOM_IMAGE"
val DEPOSIT_EXT = ".jpg"

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private var queryImageUrl: String = ""
    private val tag = javaClass.simpleName
    private var imgPath: String = ""
    private var imageUri: Uri? = null

    private var file: File? = null
    private var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_capture.setOnClickListener {
            onImgeCapture()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQ_CAPTURE -> {
                if (isAllPermissionsGranted(grantResults)) {
                    //chooseImage()
                } else {
                    Toast.makeText(
                            this,
                            getString(R.string.permission_not_granted),
                            Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            REQUEST_CODE_CAPTURE_IMAGE -> if (resultCode == RESULT_OK) {
                val bmap: Bitmap
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(this.contentResolver, fileUri!!)
                    bmap = ImageDecoder.decodeBitmap(source)
                } else {
                    bmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri)
                }
                //val bitMap = FileSupportUtils.handleSamplingAndRotationBitmap(this,fileUri!!)
                imageUri = fileUri
                queryImageUrl = imageUri?.path!!

                iv_img.setImageBitmap(bmap)


                Toast.makeText(this, "Image call back recieved...!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun onImgeCapture() {

        //Check File Permissin for Android11+
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q ){

            if(!PermissionUtils.isExternalStorageManager()){
                PermissionUtils.requestFileManagePermission(this)
            }
            else if(!PermissionUtils.isCameraPermisssionGranted(this)){
                PermissionUtils.requestCameraPermission(this)
            }
            else {
                launchCamera()
            }

        }
        else if (!PermissionUtils.isCameraAndStoragePermissionGranted(this)) {

                requestPermission(REQUEST_CODE_CAPTURE_IMAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)

        } else {
            launchCamera()
        }
    }

    private fun requestPermission(reqCode: Int, vararg permissionName: String) {
        ActivityCompat.requestPermissions(this, permissionName, reqCode)
    }

    private fun launchCamera() {
        try {
            //final Uri contentUri =
            // ContentUris.withAppendedId( MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
            // Long.valueOf(id));

            //val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.getContentUri(MediaStore.Images.Media.IS_PENDING),0)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val filename = CUSTOM_IMAGE + "_" + System.currentTimeMillis().toString()
            file = FileSupportUtils.getFileObj(this, filename, DEPOSIT_EXT)

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                //createImageFile()
               fileUri = FileSupportUtils.createFileRAbove(this)
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fileUri = FileProvider.getUriForFile(this, FileSupportUtils.getAppID() + ".contentprovider", file!!)
                intent.flags = Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            } else {
                fileUri = Uri.fromFile(file)
            }

            //val editPendingIntent: PendingIntent = MediaStore.createWriteRequest(contentResolver, listOf(fileUri))
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            val pm = packageManager!!

            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE)
                //startIntentSenderForResult(editPendingIntent.intentSender, REQUEST_CODE_CAPTURE_IMAGE, null, 0, 0, 0, null)

            } else {
                Toast.makeText(this, R.string.camera_unavailable, Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Log.d("custom_error","msg: ${e.message}")
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }


    fun isAllPermissionsGranted(grantResults: IntArray): Boolean {
        var isGranted = true
        for (grantResult in grantResults) {
            isGranted = grantResult.equals(PackageManager.PERMISSION_GRANTED)
            if (!isGranted)
                break
        }
        return isGranted
    }

    private fun createImageFile(){
        val fos:OutputStream
        val contentResolver = contentResolver
        var contentValues = ContentValues().apply {
            this.put(MediaStore.MediaColumns.DISPLAY_NAME, CUSTOM_IMAGE + DEPOSIT_EXT)
            this.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            this.put(MediaStore.MediaColumns.RELATIVE_PATH, "Custom Collection")
            //this.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES)
        }

       //val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
       val imageUri = contentResolver.insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues)
       fileUri = imageUri
       imageUri?.let { fos = contentResolver.openOutputStream(it)!! }
    }

    private fun getPickImageIntent(): Intent? {
        var chooserIntent: Intent? = null

        var intentList: MutableList<Intent> = ArrayList()

        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri())

        intentList = addIntentsToList(this, intentList, pickIntent)
        intentList = addIntentsToList(this, intentList, takePhotoIntent)

        if (intentList.size > 0) {
            chooserIntent = Intent.createChooser(
                    intentList.removeAt(intentList.size - 1),
                    getString(R.string.select_capture_image)
            )
            chooserIntent!!.putExtra(
                    Intent.EXTRA_INITIAL_INTENTS,
                    intentList.toTypedArray<Parcelable>()
            )
        }

        return chooserIntent
    }

    private fun setImageUri(): Uri {
        val folder = File("${getExternalFilesDir(Environment.DIRECTORY_DCIM)}")
        folder.mkdirs()

        val file = File(folder, "Image_Tmp.jpg")
        if (file.exists())
            file.delete()
        file.createNewFile()
        imageUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + getString(R.string.file_provider_name),
                file
        )
        imgPath = file.absolutePath
        return imageUri!!
    }

    private fun addIntentsToList(
            context: Context,
            list: MutableList<Intent>,
            intent: Intent
    ): MutableList<Intent> {
        val resInfo = context.packageManager.queryIntentActivities(intent, 0)
        for (resolveInfo in resInfo) {
            val packageName = resolveInfo.activityInfo.packageName
            val targetedIntent = Intent(intent)
            targetedIntent.setPackage(packageName)
            list.add(targetedIntent)
        }
        return list
    }

    private fun handleImageRequest(data: Intent?) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            t.printStackTrace()
            progressBar.visibility = View.GONE
            Toast.makeText(
                    this,
                    t.localizedMessage ?: getString(R.string.some_err),
                    Toast.LENGTH_SHORT
            ).show()
        }

        GlobalScope.launch(Dispatchers.Main + exceptionHandler) {
            progressBar.visibility = View.VISIBLE

            if (data?.data != null) {     //Photo from gallery
                imageUri = data.data
                queryImageUrl = imageUri?.path!!
                queryImageUrl = compressImageFile(queryImageUrl, false, imageUri!!)
            } else {
                queryImageUrl = imgPath ?: ""
                compressImageFile(queryImageUrl, uri = imageUri!!)
            }
            imageUri = Uri.fromFile(File(queryImageUrl))

            if (queryImageUrl.isNotEmpty()) {

                Glide.with(this@MainActivity)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .load(queryImageUrl)
                    .into(iv_img)
            }
            progressBar.visibility = View.GONE
        }

    }

}
