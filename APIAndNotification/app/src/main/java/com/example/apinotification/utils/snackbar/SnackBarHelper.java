package com.example.apinotification.utils.snackbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.apinotification.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnackBarHelper {

    //Color.parseColor("#0099CC")

    // Duration for SnackBar to show

    public static final int SHORT = Snackbar.LENGTH_SHORT;
    public static final int LONG = Snackbar.LENGTH_LONG;
    public static final int INDEFINITE = Snackbar.LENGTH_INDEFINITE;

    // SnackBar

    public static Snackbar showSnackBar(final View view, String message, int duration, String actionText, View.OnClickListener listener, int textColor, int actionTextColor, int backgroundColor) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setAction(actionText, listener);
        snackbar.setActionTextColor(actionTextColor);
        snackbar.getView().setBackgroundColor(backgroundColor);
        // TextView textView = snackbar.getView().findViewById(android.support.id.snackbar_text);
        // textView.setTextColor(textColor);
        snackbar.show();
        return snackbar;
    }



    public static Snackbar showSnackBarIndefiniteForErrorWithAction(View view, String message, View.OnClickListener listener, String actionText) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(actionText, listener);
        snackbar.setActionTextColor(Color.CYAN);
        snackbar.getView().setBackgroundColor(Color.BLACK);
        snackbar.show();
        return snackbar;
    }


    // Map an Object to JSON String
    public static String MapObjectToJSONString(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }


    public static ProgressDialog showLoadingDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.show();
        if (progressDialog.getWindow() != null) {
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    @SuppressLint("all")
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName)
            throws IOException {

        AssetManager manager = context.getAssets();
        InputStream is = manager.open(jsonFileName);

        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        return new String(buffer, "UTF-8");
    }


    //Converts Content uri path to absolute file path for image
    public static String getImageRealPathFromURI(Context context, Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


}
