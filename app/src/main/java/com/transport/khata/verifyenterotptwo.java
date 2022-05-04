package com.transport.khata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verifyenterotptwo extends AppCompatActivity {
    EditText inputOtp1, inputOtp2, inputOtp3, inputOtp4, inputOtp5, inputOtp6;
    String getotpbackend;
    Button verifyButtonClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyenterotptwo);

        final Button verifyButtonClick = findViewById(R.id.buttonsubmitotp);

        inputOtp1 = findViewById(R.id.inputotp1);
        inputOtp2 = findViewById(R.id.inputotp2);
        inputOtp3 = findViewById(R.id.inputotp3);
        inputOtp4 = findViewById(R.id.inputotp4);
        inputOtp5 = findViewById(R.id.inputotp5);
        inputOtp6 = findViewById(R.id.inputotp6);

        TextView textview = findViewById(R.id.textmobilenumbershow);
        textview.setText(String.format("+91-%s", getIntent().getStringExtra("mobile")));

        getotpbackend = getIntent().getStringExtra("backendotp");

        final ProgressBar progressbarVerifyOtp = findViewById(R.id.progressbar_verify_otp);

        verifyButtonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputOtp1.getText().toString().trim().isEmpty() &&
                        !inputOtp2.getText().toString().trim().isEmpty() &&
                        !inputOtp3.getText().toString().trim().isEmpty() &&
                        !inputOtp4.getText().toString().trim().isEmpty() &&
                        !inputOtp5.getText().toString().trim().isEmpty() &&
                        !inputOtp6.getText().toString().trim().isEmpty()){

                    String enterCodeOtp = inputOtp1.getText().toString() +
                            inputOtp2.getText().toString() +
                            inputOtp3.getText().toString() +
                            inputOtp4.getText().toString() +
                            inputOtp5.getText().toString() +
                            inputOtp6.getText().toString();

                    if (getotpbackend != null){
                        progressbarVerifyOtp.setVisibility(View.VISIBLE);
                        verifyButtonClick.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                getotpbackend, enterCodeOtp
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressbarVerifyOtp.setVisibility(View.GONE);
                                verifyButtonClick.setVisibility(View.VISIBLE);

                                if(task.isSuccessful()){
                                    Intent intent = new Intent(getApplicationContext(), Home.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(verifyenterotptwo.this,"Enter correct OTP",Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                    }else{
                        Toast.makeText(verifyenterotptwo.this,"Please check internet connection",Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(verifyenterotptwo.this,"OTP verified",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(verifyenterotptwo.this,"Enter complete OTP",Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        nextOtpTextBox();

        TextView resendOtp = findViewById(R.id.secondotp);

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        verifyenterotptwo.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(verifyenterotptwo.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackendOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = newbackendOtp;
                                Toast.makeText(verifyenterotptwo.this,"OTP resend Successful",Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }
        });

    }

    private void nextOtpTextBox() {
        inputOtp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputOtp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputOtp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputOtp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputOtp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputOtp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}