package scheduler.com.jobschedulereg;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Button startService, stopService, locBut;
    TextView viewLocAdd;
    private static final int JOB_SHEDULER_ID = 99;
    Switch aSwitchTrial;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService = findViewById(R.id.start_service_but);
        stopService = findViewById(R.id.stop_service_but);
        locBut = findViewById(R.id.get_loc_but);
        viewLocAdd = findViewById(R.id.get_loc_but);
        aSwitchTrial = findViewById(R.id.switch_trial);

        mContext = this;


        aSwitchTrial.setOnCheckedChangeListener(this);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService();
            }
        });

        stopService.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                cancelSerivce();
            }
        });


        locBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);*/

            }
        });


        //18.995114,72.827166 ,  19.003513,72.831973

    }


    private void startService() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ComponentName componentName = new ComponentName(this,JobShedulerServiceTest.class);
            JobInfo info = new JobInfo.Builder(123,componentName)
                    //.setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .setPeriodic(15*60*1000)
                    .build();

            //(5*1000 = 5 sec execute after every 5 sec)

            JobScheduler scheduler = (JobScheduler) mContext.getSystemService(JOB_SCHEDULER_SERVICE);
            int resultCode = scheduler.schedule(info);

            if(resultCode == JobScheduler.RESULT_SUCCESS){
                LogHelper.d("success: "+resultCode);
            }else{
                LogHelper.d("fail: "+resultCode);
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void cancelSerivce(){
        JobScheduler scheduler = (JobScheduler) mContext.getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(JOB_SHEDULER_ID);
        LogHelper.d("Job cancelled is call");

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

}
