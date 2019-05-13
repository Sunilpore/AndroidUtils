package com.example.apinotification.utils.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

public class DialogueHelper {


    public static AlertDialog displayDialog(Context mContext, View view, String title){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setView(view);
        builder.setTitle(title);
        builder.setCancelable(false);

       // AlertDialog dialog = builder.create();

        return builder.show();
    }


}
