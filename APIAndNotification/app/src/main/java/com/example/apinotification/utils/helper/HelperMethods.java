package com.example.apinotification.utils.helper;

import android.content.Context;
import android.graphics.Color;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apinotification.R;


/**
 * Created on 21-02-2018.
 */

public class HelperMethods {

    private static final String TAGData = "myTag";
    private static final String TAGContextData = "myTag_context";
    private static final String TAGError = "ErrorTag";

    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //No use
    public static void showToastShortBelowView(Context context, View view, String message) {
        int x = view.getLeft();
        int y = view.getTop() + 2 * view.getHeight();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.START, x, y);
        toast.show();
    }

    /**
     * To check if String contains only digits
     *
     * @param str -> string to check in
     * @return -> returns the check status
     */
    public static boolean isDigitsOnly(String str) {
        return str != null && !str.isEmpty() && TextUtils.isDigitsOnly(str);
    }

    //No use
    public static void showToastLongBelowView(Context context, View view, String message) {
        int x = view.getLeft();
        int y = view.getTop() + 2 * view.getHeight();
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.START, x, y);
        toast.show();

    }

    public static void showLogData(String message) {
        Log.d(TAGData, message);
    }

    public static void showLogError(String message) {
        Log.d(TAGError, "Error: " + message);
    }

    public static void showContextLogData(Context context, String s) {

        Log.d(TAGContextData, String.format("%s\t%s", context.getClass().getCanonicalName(), s));
    }

    public static void showSnackBarShort(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    //---------------------- Snackbar ------------------------------

    public static void showSnackBarShortForError(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void showSnackBarLongForError(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void showSnackBarInfiniteForError(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(Color.RED);
        disableSwipeToDismiss(snackbar);
    }

    public static void showSnackBarLongForSuccess(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.snack_bar_color_success));
        snackbar.show();
    }

    public static void showSnackBarShortForSuccess(Context context, View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.snack_bar_color_success));
        snackbar.show();
    }

    public static void showSnackBarLong(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static void showSnackBarInfinite(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).show();
    }

    //disable Swipe to Dismiss feature in Snackbar
    private static void disableSwipeToDismiss(Snackbar snackbar) {
        final View view = snackbar.getView();
        snackbar.show();
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                ((CoordinatorLayout.LayoutParams) view.getLayoutParams()).setBehavior(null);
                return true;
            }
        });
    }

    //---------------------- Snackbar ------------------------------
    //is used to check if edit text is empty or not
    public static boolean isEditTextEmpty(EditText editText) {
        return editText != null && editText.getText().toString().trim().length() != 0 && !editText.getText().toString().trim().equals("");
    }


    //is used to check if edit text is empty or not
    public static boolean isTextInputEditTextEmpty(TextInputEditText editText) {
        return editText != null && editText.getText().toString().trim().length() != 0 && !editText.getText().toString().trim().equals("");
    }

    /*//is used to check if edit text is empty or not
    public static boolean isEditTextEmpty(EditText editText) {
        return editText == null || editText.getText().toString().trim().length() == 0 || editText.getText().toString().trim().equals("");
    }*/

    //is used to check if TextInputLayout is empty or not
    public static boolean isTextInputLayoutEmpty(TextInputEditText textInputEditText) {
        return textInputEditText == null || textInputEditText.getText().toString().trim().length() == 0 || textInputEditText.getText().toString().trim().equals("");
    }

    //is used to check if textView is empty or not
    public static boolean isTextViewEmpty(TextView textView) {
        return textView == null || textView.getText().toString().trim().length() == 0 || textView.getText().toString().trim().equals("");
    }

    //is used to check if String is empty or not
    //true not empty
    //false empty
    public static boolean isStringNotEmpty(String string) {
        return string != null && string.length() != 0;

    }


    public static boolean loadFragment(int containerViewId, FragmentManager fragmentManager, Fragment fragment) {
        //switching fragment
        if (fragment != null && fragmentManager != null) {
            fragmentManager.beginTransaction()
                    .replace(containerViewId, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    /*//is used to check if String is empty or not
    public static boolean isStringNotEmpty(String string) {
        return string == null || string.length() == 0 || string.equals("");

    }*/
}
