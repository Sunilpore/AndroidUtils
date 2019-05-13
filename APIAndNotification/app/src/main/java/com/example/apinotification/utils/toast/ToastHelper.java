package com.example.apinotification.utils.toast;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apinotification.R;


public class ToastHelper {

    //Color.parseColor("#0099CC")

    // position,horizontal offset,vertical offset for Toast to show

    public static final int GRAVITY_CENTER = Gravity.CENTER;
    public static final int X_OFFSET = 0;
    public static final int Y_OFFSET = 0;

    // Duration for Toast to show

    public static final int LONG = Toast.LENGTH_LONG;
    public static final int SHORT = Toast.LENGTH_SHORT;


    // Toast

    public static void showDefaultToast(Context mContext, String message, int duration, int textColor) {
        Toast toast = Toast.makeText(mContext, message, duration);
        TextView view = (TextView) toast.getView().findViewById(android.R.id.message);
        view.setTextColor(textColor);
        toast.show();
    }

    public static void showToastInLocation(Context mContext, String message, int duration, int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin) {
        Toast toast = Toast.makeText(mContext, message, duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setMargin(horizontalMargin, verticalMargin);
        toast.show();
    }

    public static void showDefaultImageInToast(Context mContext, String message, int duration, float horizontalMargin, float verticalMargin) {
        Toast toast = Toast.makeText(mContext, message, duration);
        toast.setMargin(horizontalMargin, verticalMargin);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    public static void showImageInToastInLocation(Context mContext, String message, int duration, int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin) {
        Toast toast = Toast.makeText(mContext, message, duration);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setMargin(horizontalMargin, verticalMargin);
        LinearLayout toastContentView = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.drawable.ic_launcher_background);
        toastContentView.addView(imageView, 0);
        toast.show();
    }

    public static void showToastCustomView(Context mContext, View view, String message, int duration, int gravity, int xOffset, int yOffset, float horizontalMargin, float verticalMargin) {
        Toast toast = Toast.makeText(mContext, message, duration);
        toast.setView(view);
        toast.setGravity(gravity, xOffset, yOffset);
        toast.setMargin(horizontalMargin, verticalMargin);
        toast.show();
    }
}
