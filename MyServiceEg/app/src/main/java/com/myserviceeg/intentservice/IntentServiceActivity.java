package com.myserviceeg.intentservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.myserviceeg.ExampleService;
import com.myserviceeg.R;

public class IntentServiceActivity extends AppCompatActivity {

    public static final String START_SERVICE_ARG = "input_extra";

    EditText edInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        edInput = findViewById(R.id.ed_notification);

    }

    public void startService(View view) {

        String input = edInput.getText().toString();
        Intent serviceIntent = new Intent(this, ExampleIntentService.class);
        serviceIntent.putExtra(START_SERVICE_ARG,input);

        ContextCompat.startForegroundService(this,serviceIntent);
    }

}
