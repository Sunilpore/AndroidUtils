package notify.pi.com.notificationeg;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    Button nextBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        nextBut = findViewById(R.id.next_button);

        nextBut.setOnClickListener(view->{

            Intent i = new Intent(this,NotificationActivity.class);
            startActivity(i);
        });


    }


}
