package scheduler.com.jobschedulereg.geofencing;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

public class SimpleJobIntentService extends JobIntentService {

    static void enqueueWork(Context context, Intent work){
        enqueueWork(context,SimpleJobIntentService.class,195,work);
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        for (int i = 0; i < 5; i++) {
            Log.e("Haha", "onHandleWork: "+i );
        }
    }
}
