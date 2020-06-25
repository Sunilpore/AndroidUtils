package com.myserviceeg.job_intent_service;

import android.app.IntentService;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.myserviceeg.LogHelper;
import com.myserviceeg.R;

import static com.myserviceeg.App.CHANNEL_ID;
import static com.myserviceeg.job_intent_service.JobIntentServiceActivity.START_SERVICE_ARG;

public class ExampleJobIntentService extends JobIntentService {

    static void enqueueWork(Context context, Intent work){
        enqueueWork(context,ExampleJobIntentService.class,123,work);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        LogHelper.d("onCreate...");

        //enqueueWork();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        LogHelper.d("onHandleIntent");

        String input = intent.getStringExtra(START_SERVICE_ARG);

        for(int i=0;i<10;i++){
            LogHelper.d(input+ " - "+ i);
            if(isStopped()) return;
            SystemClock.sleep(1000);
        }

    }

    @Override
    public void onDestroy() {
        LogHelper.d("onDestroy...");
        super.onDestroy();
    }

    @Override
    public boolean onStopCurrentWork() {
        LogHelper.d("onStopCurrentWork");  // It will return true if service stop due to some reason
        return super.onStopCurrentWork();
    }

}
