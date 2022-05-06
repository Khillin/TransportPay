package com.transport.khata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class entermobilenumberone extends AppCompatActivity {

    EditText enterNumber;
    Button getOtpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entermobilenumberone);

        enterNumber = findViewById(R.id.input_mobile_number);
        getOtpButton = findViewById(R.id.buttongetotp);


        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        getOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enterNumber.getText().toString().trim().isEmpty()){
                    if(enterNumber.getText().toString().trim().length() == 10){

                        progressBar.setVisibility(View.VISIBLE);
                        getOtpButton.setVisibility(View.INVISIBLE);
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enterNumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                entermobilenumberone.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.VISIBLE);
                                        Toast.makeText(entermobilenumberone.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getOtpButton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), verifyenterotptwo.class);
                                        intent.putExtra("mobile", enterNumber.getText().toString());
                                        intent.putExtra("backendotp", backendOtp);
                                        intent.putExtra("ownerId", uid);
                                        startActivity(intent);
//                                        Intent intent = new Intent(getApplicationContext(), Home.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
                                    }
                                }
                        );
                    } else{
                        Toast.makeText(entermobilenumberone.this,"Please Enter correct number",Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(entermobilenumberone.this,"Enter Mobile number",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}