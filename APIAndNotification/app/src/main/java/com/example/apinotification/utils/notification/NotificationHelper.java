package com.example.apinotification.utils.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.example.apinotification.R;


public class NotificationHelper {
    private Context mContext;
    private int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    private static final String CHANNEL_ID = "FileUploadNotification";


    public NotificationHelper(Context context) {
        mContext = context;

        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void createNotification(int newTask) {

        try {

            NOTIFICATION_ID = newTask;

            Intent notificationIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            builder.setAutoCancel(false)
                    .setContentTitle("")
                    .setContentText("")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setContentIntent(pendingIntent)
                    .setOngoing(false)
                    .setNumber(100)
                    .build();

            // notificationId is a unique int for each notification that you must define
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        } catch (Exception e) {

            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void progressUpdate(int currentImageCount, int totalImages) {
        try {

            Intent notificationIntent = new Intent();

            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            builder.setAutoCancel(false)
                    .setContentTitle("")
                    .setContentText("")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setProgress(totalImages, currentImageCount, false)
                    .setOngoing(false)
                    .setNumber(100)
                    .build();


            // notificationId is a unique int for each notification that you must define
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
            notificationManager.notify(NOTIFICATION_ID, builder.build());


        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void completed(int total) {

        mNotificationManager.cancel(NOTIFICATION_ID);
        try {

            Intent notificationIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            builder.setAutoCancel(false)
                    .setContentTitle("")
                    .setContentText("")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .setOngoing(false)
                    .setProgress(0, 0, false)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setNumber(100)
                    .build();


            // notificationId is a unique int for each notification that you must define
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
            notificationManager.notify(NOTIFICATION_ID, builder.build());


        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void failed() {

        mNotificationManager.cancel(NOTIFICATION_ID);


        try {

            Intent notificationIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            builder.setAutoCancel(false)
                    .setContentTitle("")
                    .setContentText("")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentIntent(pendingIntent)
                    .setOngoing(false)
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .setNumber(100)
                    .build();

            // notificationId is a unique int for each notification that you must define
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void clearNotification() {
        mNotificationManager.cancel(NOTIFICATION_ID);
    }
}

