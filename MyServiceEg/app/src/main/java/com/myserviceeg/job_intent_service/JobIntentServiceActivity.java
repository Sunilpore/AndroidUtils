package com.myserviceeg.job_intent_service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myserviceeg.R;

public class JobIntentServiceActivity extends AppCompatActivity {

    public static final String START_SERVICE_ARG = "input_extra";

    EditText editTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_intent_service);

        editTextInput = findViewById(R.id.edt_txt_ip);

    }

    public void enqueueWork(View view) {
        String input = editTextInput.getText().toString();

        Intent serviceIntent = new Intent(this, ExampleJobIntentService.class);
        serviceIntent.putExtra(START_SERVICE_ARG,input);
        ExampleJobIntentService.enqueueWork(this,serviceIntent);
    }
}
