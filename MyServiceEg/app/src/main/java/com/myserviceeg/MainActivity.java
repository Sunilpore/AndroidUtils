package com.myserviceeg;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Button startService, stopService;
    private static final int JOB_SHEDULER_ID = 99;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService = findViewById(R.id.start_service_but);
        stopService = findViewById(R.id.stop_service_but);

        mContext = this;



        startService.setOnClickListener(v -> startService());

        stopService.setOnClickListener(v -> cancelSerivce());


    }


    private void startService() {

        ComponentName componentName = new ComponentName(this,JobShedulerServiceTest.class);
        JobInfo info = new JobInfo.Builder(JOB_SHEDULER_ID,componentName)
                //.setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(15*60*1000)
                .build();

        //(5*1000 = 5 sec execute after every 5 sec)

        JobScheduler scheduler = (JobScheduler) mContext.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = Objects.requireNonNull(scheduler).schedule(info);

        if(resultCode == JobScheduler.RESULT_SUCCESS){
            LogHelper.d("success: "+resultCode);
        }else{
            LogHelper.d("fail: "+resultCode);
        }

    }

    private void cancelSerivce(){
        JobScheduler scheduler = (JobScheduler) mContext.getSystemService(JOB_SCHEDULER_SERVICE);
        if (scheduler != null) {
            scheduler.cancel(JOB_SHEDULER_ID);
        } else {
            LogHelper.d("Job is null");
        }
        LogHelper.d("Job cancelled is call");

    }



}
