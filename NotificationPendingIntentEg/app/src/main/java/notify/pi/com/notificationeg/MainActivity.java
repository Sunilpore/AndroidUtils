package notify.pi.com.notificationeg;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button notificationBut, nextBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationBut = findViewById(R.id.but_notification);
        nextBut = findViewById(R.id.but_next_page);


        nextBut.setOnClickListener(view->{
            Intent i = new Intent(this,SecondActivity.class);
            startActivity(i);
        });

        notificationBut.setOnClickListener(view->{

            launchNotification(1);

            /*Intent i = new Intent(this,NotificationActivity.class);

            PendingIntent pi = PendingIntent.getActivity(this,0,i,0);

            Notification nf = new Notification.Builder(this)
                    .setSmallIcon(android.R.drawable.btn_star)
                    .setContentTitle("Notification for DC world")
                    .setContentText("Click notification to DC world")
                    .setAutoCancel(true)
                    .setContentIntent(pi)
                    .build();

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.notify(2,nf);*/

        });


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
