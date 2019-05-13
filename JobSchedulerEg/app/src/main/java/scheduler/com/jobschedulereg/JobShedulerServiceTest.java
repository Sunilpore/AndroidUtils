package scheduler.com.jobschedulereg;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobShedulerServiceTest extends JobService {

    private static final String TAG = "GeofenceServiceJobScheduler";
    FusedLocationProviderClient mFusedLocationProviderClient;
    LocationManager locationManager;
    public Location myLocation;
    private boolean jobCancelled = false,gpsStatus= false;

    @Override
    public boolean onStartJob(JobParameters params) {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        LogHelper.d("Job started");
        doBackGroundWork(params);
        return true;
    }

    private void doBackGroundWork(JobParameters params) {

        new Thread(() -> {
            Looper.prepare();

            for(int i=0;i<10;i++) {
                LogHelper.d("run" + i);
                /*if (jobCancelled) {
                    return;
                }*/
            }

            while (!gpsStatus) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                 if(locationManager==null){
                     LogHelper.d("locationManager is null");
                     locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                 }else{
                     LogHelper.d("locationManager is initialize already");
                 }

                if (locationManager != null) {
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                      //  Location myLocation =

                        Criteria criteria = new Criteria();
                        criteria.setPowerRequirement(Criteria.POWER_LOW);
                        criteria.setAccuracy(Criteria.ACCURACY_LOW);

                        String locationProvider = locationManager.getBestProvider(criteria, true);
                        locationManager.requestLocationUpdates(locationProvider, 1000 * 60 * 2, 1, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {


                                LogHelper.d("lat: " + location.getLatitude() + " \t long: " + location.getLongitude());
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                                LogHelper.d("loc status: " + status);
                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                                LogHelper.d("provider enable: " + provider);
                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                                LogHelper.d("provider disable: " + provider);
                            }
                        });
                        myLocation = locationManager.getLastKnownLocation(locationProvider);

                       // LogHelper.d("latitude: "+myLocation.getLatitude()+" \tlongitude: "+myLocation.getLongitude());

                        /*mFusedLocationProviderClient.getLastLocation().addOnSuccessListener( getApplicationContext(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {

                                if (location != null) {
                                    LogHelper.d( "LasKnown loc " +
                                            "Long: " + location.getLongitude() +
                                            " | Lat: " + location.getLatitude());

                                }
                            }
                        });*/
                        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {

                                LogHelper.d("lat: " + location.getLatitude() + " \t long: " + location.getLongitude());
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                                LogHelper.d("loc status: " + status);
                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                                LogHelper.d("provider enable: " + provider);
                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                                LogHelper.d("provider disable: " + provider);
                            }

                        });*/
                        LogHelper.d("GPS is Enabled");

                    } else {
                        gpsStatus = true;
                        LogHelper.d("GPS is Disabled");
                        launchNotification();
                        jobFinished(params, false);
                    }
                }

            }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LogHelper.d(e.getMessage());
                }

            }

            jobFinished(params,false);


            /*if(jobCancelled){
                return;
            }

        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    if(!gpsStatus){
                        Toast.makeText(this, "Service is started", Toast.LENGTH_SHORT).show();
                        gpsStatus = true;
                    }
                    LogHelper.d("GPS is Enabled");
                }else {
                    LogHelper.d("GPS is Disabled");
                    Toast.makeText(this, "GPS is disable", Toast.LENGTH_SHORT).show();
                    jobFinished(params,false);
                }
            }*/

        Looper.loop();
        }).start();

    }

    @Override
    public boolean onStopJob(JobParameters params) {
        LogHelper.d("job cancel before completion");
        jobCancelled = true;
        return true;
    }


    private void launchNotification() {

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
                    .setContentTitle("Notification for GPS")
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
