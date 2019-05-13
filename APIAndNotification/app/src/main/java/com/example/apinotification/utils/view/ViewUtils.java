package com.example.apinotification.utils.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;


import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.example.apinotification.R;


public final class ViewUtils {

    private ViewUtils() {
        // This utility class is not publicly instantiable
    }

    public static float pxToDp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dpToPx(float dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    public static void changeIconDrawableToGray(Context context, Drawable drawable) {
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(ContextCompat
                    .getColor(context, R.color.DarkGray), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public static int getColorResources(Context context, int color) {
        return context.getResources().getColor(color);
    }

    public static Drawable getDrawableIcon(Context context, int icon_resource_id) {
        return context.getResources().getDrawable(icon_resource_id);
    }

    //created
    public static boolean isColorCorrect(String color) {
        return !TextUtils.isEmpty(color) && color.length() > 5;
    }

    //created

    /**
     * Get Drawable icon from resources, find by its name
     *
     * @param icon_name -> name of icon to find in resources
     * @return -> Drawable icon
     */
    public static Drawable getIconFromString(Context context, String icon_name) {
        Resources res = context.getResources();
        int resID = res.getIdentifier(icon_name, "drawable", context.getPackageName());
        return ContextCompat.getDrawable(context, resID);
    }

/*@NonNull
    private static ShapeDrawable getShapeOfVehicleName(@ColorInt int color) {
        ShapeDrawable sd = new ShapeDrawable();
        // Specify the shape of ShapeDrawable
        sd.setShape(new OvalShape());
        // Specify the border color of shape
        sd.getPaint().setColor(color);
        sd.getPaint().setColor(Color.WHITE);
        // Set the border width
        sd.getPaint().setStrokeWidth(1f);
        // Specify the style is a Stroke
        sd.getPaint().setStyle(Paint.Style.STROKE);


        return sd;
    }*/



    //created
    /**
     * get rectangle shape with Corner Radius
     *
     * @param stroke_color   -> border color
     * @param stroke_width   -> border width
     * @param corners_radius -> Corner Radius of rectangle
     * @param color          -> background color of shape
     * @return -> rectangle shape with Corner Radius
     */
    @NonNull
    public static GradientDrawable getRectangleOvalShape(@ColorInt int stroke_color,
                                                         int stroke_width,
                                                         float corners_radius,
                                                         @ColorInt int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setColor(color);
        // gd.setCornerRadii(new float[]{25f, 25f, 25f, 25f});
        gd.setCornerRadius(corners_radius);
        gd.setStroke(stroke_width, stroke_color);
        return gd;
    }

}
