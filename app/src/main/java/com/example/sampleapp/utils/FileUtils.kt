package com.example.sampleapp.utils

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.sampleapp.BuildConfig
import com.example.sampleapp.activity.CUSTOM_IMAGE
import com.example.sampleapp.activity.DEPOSIT_EXT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

private const val tag = "FileUtils"

suspend fun Activity.compressImageFile(
    path: String,
    shouldOverride: Boolean = true,
    uri: Uri
): String {
    return withContext(Dispatchers.IO) {
        var scaledBitmap: Bitmap? = null

        try {
            val (hgt, wdt) = getImageHgtWdt(uri)
            try {
                val bm = getBitmapFromUri(uri)
                Log.d(tag, "original bitmap height${bm?.height} width${bm?.width}")
                Log.d(tag, "Dynamic height$hgt width$wdt")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            // Part 1: Decode image
            val unscaledBitmap = decodeFile(this@compressImageFile, uri, wdt, hgt, ScalingLogic.FIT)
            if (unscaledBitmap != null) {
                if (!(unscaledBitmap.width <= 800 && unscaledBitmap.height <= 800)) {
                    // Part 2: Scale image
                    scaledBitmap = createScaledBitmap(unscaledBitmap, wdt, hgt, ScalingLogic.FIT)
                } else {
                    scaledBitmap = unscaledBitmap
                }
            }

            // Store to tmp file
            val mFolder = File("$filesDir/Images")
            if (!mFolder.exists()) {
                mFolder.mkdir()
            }

            val tmpFile = File(mFolder.absolutePath, "IMG_${getTimestampString()}.png")

            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(tmpFile)
                scaledBitmap?.compress(
                    Bitmap.CompressFormat.PNG,
                    getImageQualityPercent(tmpFile),
                    fos
                )
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()

            } catch (e: Exception) {
                e.printStackTrace()
            }

            var compressedPath = ""
            if (tmpFile.exists() && tmpFile.length() > 0) {
                compressedPath = tmpFile.absolutePath
                if (shouldOverride) {
                    val srcFile = File(path)
                    val result = tmpFile.copyTo(srcFile, true)
                    Log.d(tag, "copied file ${result.absolutePath}")
                    Log.d(tag, "Delete temp file ${tmpFile.delete()}")
                }
            }

            scaledBitmap?.recycle()

            return@withContext if (shouldOverride) path else compressedPath
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        return@withContext ""
    }

}

@Throws(IOException::class)
fun Context.getBitmapFromUri(uri: Uri, options: BitmapFactory.Options? = null): Bitmap? {
    val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r")
    val fileDescriptor = parcelFileDescriptor?.fileDescriptor
    val image: Bitmap? = if (options != null)
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options)
    else
        BitmapFactory.decodeFileDescriptor(fileDescriptor)
    parcelFileDescriptor?.close()
    return image
}

fun getTimestampString(): String {
    val date = Calendar.getInstance()
    return SimpleDateFormat("yyyy MM dd hh mm ss", Locale.US).format(date.time).replace(" ", "")
}

class FileSupportUtils {

    companion object {

        private const val maxHeight = 2339
        private const val maxWidth = 1654


        fun getFileObj(context:Context, fileName: String, ext: String): File {
            // Create an image file name
            //Environment.getExtgetExternalStorageDirectory()
            val storageDir = File(
                context.getExternalFilesDir(null)
                    .toString() + File.separator + "Custom"
            )
            if (!storageDir.isDirectory) {
                storageDir.mkdirs()
            }
            return File(storageDir, fileName + ext)
        }

        fun getAppID(): String? {
            return BuildConfig.APPLICATION_ID
        }

        fun createFileRAbove(context:Context,
                             fileName:String=CUSTOM_IMAGE + DEPOSIT_EXT,
                             mimeType:String="image/jpg",
                             filePath:String="Custom Collection"
        ): Uri? {
            val fos:OutputStream
            val contentResolver = context.contentResolver
            val contentValues = ContentValues().apply {
                this.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                this.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                this.put(MediaStore.MediaColumns.RELATIVE_PATH, filePath)
            }
            //val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues)
            //val imageUri = contentResolver.insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues)
            val imageUri = contentResolver.insert(MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL), contentValues)
            imageUri?.let { fos = contentResolver.openOutputStream(it)!! }
            return imageUri
        }

        @Throws(IOException::class)
        fun handleSamplingAndRotationBitmap(context: Context, selectedImage: Uri): Bitmap? {

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val imageStream = context.contentResolver.openInputStream(selectedImage)
            BitmapFactory.decodeStream(imageStream, null, options)
            imageStream!!.close()

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, maxWidth, maxHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            val imageStream1 = context.contentResolver.openInputStream(selectedImage)
            var img = BitmapFactory.decodeStream(imageStream1, null, options)
            img = rotateImageIfRequired(img!!, selectedImage)
            img = resize(img!!, maxWidth, maxHeight)
            saveBitmapToJpg(img!!, File(selectedImage.path))
            return img
        }

    }



}

@Throws(IOException::class)
private fun rotateImageIfRequired(img: Bitmap, selectedImage: Uri): Bitmap? {
    val ei = ExifInterface(selectedImage.path!!)
    val orientation =
        ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    return when (orientation) {
        ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(img, 90)
        ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(img, 180)
        ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(img, 270)
        else -> img
    }
}

private fun rotateImage(img: Bitmap, degree: Int): Bitmap? {
    val matrix = Matrix()
    matrix.postRotate(degree.toFloat())
    val rotatedImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
    img.recycle()
    return rotatedImg
}

private fun calculateInSampleSize(
    options: BitmapFactory.Options,
    reqWidth: Int, reqHeight: Int
): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {

        // Calculate ratios of height and width to requested height and width
        val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
        val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

        // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
        // with both dimensions larger than or equal to the requested height and width.
        inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio

        // This offers some additional logic in case the image has a strange
        // aspect ratio. For example, a panorama may have a much larger
        // width than height. In these cases the total pixels might still
        // end up being too large to fit comfortably in memory, so we should
        // be more aggressive with sample down the image (=larger inSampleSize).
        val totalPixels = width.toFloat() * height

        // Anything more than 2x the requested pixels we'll sample down further
        val totalReqPixelsCap = reqWidth * reqHeight * 2f
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
    }
    return inSampleSize
}

fun resize(image: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap? {
    var image = image
    var finalWidth: Int
    var finalHeight: Int
    return if (maxHeight > 0 && maxWidth > 0) {
        val width = image.width
        val height = image.height
        if (width >= maxWidth || height >= maxHeight) {
            val ratioBitmap = width.toFloat() / height.toFloat()
            val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()
            finalWidth = maxWidth
            finalHeight = maxHeight
            if (ratioMax > 1) {
                finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
            } else {
                finalHeight = (maxWidth.toFloat() / ratioBitmap).toInt()
            }
        } else {
            finalWidth = image.width
            finalHeight = image.height
        }
        image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true)
        image
    } else {
        image
    }
}

@Throws(IOException::class)
fun saveBitmapToJpg(bitmap: Bitmap, file: File?) {
    val imageByteArray = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, imageByteArray)
    val imageData = imageByteArray.toByteArray()
    var fileOutputStream: FileOutputStream? = null
    try {
        fileOutputStream = FileOutputStream(file)
        fileOutputStream.write(imageData)
    } catch (fnf: FileNotFoundException) {
        Log.e("exception", fnf.message!!)
    } finally {
        fileOutputStream?.close()
    }
}

