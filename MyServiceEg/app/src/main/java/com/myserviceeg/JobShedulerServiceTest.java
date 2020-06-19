package com.myserviceeg;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobShedulerServiceTest extends JobService {

    private static final String TAG = "GeofenceServiceJobScheduler";
    LocationManager locationManager;
    public Location myLocation;
    private boolean jobCancelled = false,gpsStatus= false;

    @Override
    public boolean onStartJob(JobParameters params) {

        LogHelper.d("Job started");
        doBackGroundWork(params);
        return true;
    }

    private void doBackGroundWork(JobParameters params) {

        new Thread(() -> {
            Looper.prepare();

            for(int i=0;i<10;i++) {
                LogHelper.d("run" + i +"\t"+jobCancelled);
                if (jobCancelled) {
                    return;
                }

                if(i%2 == 0){
                    launchNotification(i);
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    LogHelper.e(e.getMessage());
                }

            }

            LogHelper.d("job finished");
            jobFinished(params,false);


        Looper.loop();
        }).start();

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogHelper.d("job cancel before completion");
        jobCancelled = true;
        return true;
    }

    private void launchNotification(int count) {

        Intent i = new Intent(this,NotificationActivity.class);

        PendingIntent pi = PendingIntent.getActivity(this,0,i,0);

        Notification nf = null;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123", name, importance);
            channel.setDescription(description);

            nf = new Notification.Builder(this,channel.getId())
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle("Test Notification "+count)
                    .setContentText("Click notification See Activity")
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .build();
            nm.createNotificationChannel(channel);
        }else {
            nf = new Notification.Builder(this)
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle("Notification for GPS")
                    .setContentText("Click notification See Activity")
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .build();
        }

        nm.notify(123,nf);
       // nm.notify(2,nf);
    }


}
