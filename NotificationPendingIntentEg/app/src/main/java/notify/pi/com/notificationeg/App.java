package notify.pi.com.notificationeg;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static String CHANNEL_ID = "egNotificationChannel";


    @Override
    public void onCreate() {
        super.onCreate();

        //This is essential to launch notification from service class
        createNotificationChannel();

    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel servicechannel = new NotificationChannel(CHANNEL_ID,"Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(servicechannel);
        }

    }

}
