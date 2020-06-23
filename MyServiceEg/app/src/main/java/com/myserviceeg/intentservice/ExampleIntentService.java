package com.myserviceeg.intentservice;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.myserviceeg.LogHelper;
import com.myserviceeg.R;

import static com.myserviceeg.App.CHANNEL_ID;

public class ExampleIntentService extends IntentService {

    private PowerManager.WakeLock wakelock;

    public ExampleIntentService() {
        super("ExampleIntentService");
        setIntentRedelivery(true);   //true -> START_REDLIVER_INTENT , false -> NON_STICKY
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogHelper.d("onCreate...");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakelock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                "ExampleApp:Wakelock");
        wakelock.acquire(6000);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle("Example Intent Service")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build();
            startForeground(1,notification);
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        LogHelper.d("onHandleIntent");

        String input = intent.getStringExtra("inputExtra");

        for(int i=0;i<10;i++){
            LogHelper.d(input+ " - "+ i);
            SystemClock.sleep(1000);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wakelock.release();
        LogHelper.d("onDestroy, wakelock release...");
    }

}
