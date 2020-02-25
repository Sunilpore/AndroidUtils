package com.intentpickereg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MainActTag";
    private static final int SOME_REQUEST_CODE = 23;
    private static final int RESOLVE_HINT = 32;

    Button btnPickEmail, btnPickMobNo;
    TextView tvEmail, tvMobNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


/*        Intent intent =
                AccountPicker.newChooseAccountIntent(
                        new AccountChooserOptions.Builder()
                                .setAllowableAccountsTypes(Arrays.asList("com.google"))
                                .build());*/

        tvEmail = findViewById(R.id.tv_email);
        tvMobNo = findViewById(R.id.tv_mob_no);
        btnPickEmail = findViewById(R.id.btn_pick_mail);
        btnPickMobNo = findViewById(R.id.btn_pick_mob_no);

        btnPickEmail.setOnClickListener(v->{
            Intent emailIntent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                    false, null, null, null, null);
            startActivityForResult(emailIntent, SOME_REQUEST_CODE);

        });


        btnPickMobNo.setOnClickListener(v->{
            GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Auth.CREDENTIALS_API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            googleApiClient.connect();

            HintRequest hintRequest = new HintRequest.Builder()
                    .setPhoneNumberIdentifierSupported(true)
                    .build();

            PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(
                    googleApiClient, hintRequest);
            try {
                startIntentSenderForResult(intent.getIntentSender(),
                        RESOLVE_HINT, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                Log.d(TAG,""+e.getMessage());
            }
        });


    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if (requestCode == SOME_REQUEST_CODE ) {
                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                tvEmail.setText(accountName);
                Log.d(TAG,"acc name: "+accountName);
            } else if(requestCode == RESOLVE_HINT){
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                String mobNo = credential.getId();  // will need to process phone number string
                tvMobNo .setText(mobNo);
                Log.d(TAG,"mob no: "+mobNo);
            }
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
