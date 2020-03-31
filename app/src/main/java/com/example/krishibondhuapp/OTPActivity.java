package com.example.krishibondhuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {
    private static final long COUNTDOWN_IN_MILLIS = 10000;
    private static final String KEY_MILLIS_LEFT = "KeyMillisLeft";
    private CountDownTimer countDownTimer;
    private long timeLeftMillis;
    ProgressBar progressBar;
    TextView textViewCountDown;
    EditText verifyCode;
    Button signIn,resend;
    String codeSend,phoneNumber;
    FirebaseAuth mAuth;

    public static final String KEY = "KEY";
    int checker = 1;

    public static final String USER_PREF = "USER_PREF" ;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp );
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        sharePrefValueUpdate();

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.verify_progressBar);
        verifyCode = findViewById(R.id.verCodeid);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        signIn = findViewById(R.id.signin_buttonid);
        resend  =findViewById(R.id.resend_buttonid);

        final Intent intent  = getIntent();
        phoneNumber = intent.getStringExtra("Mobile");
        //for send code
        sendVerifycode();
        //Time countdown ....
        timeLeftMillis = COUNTDOWN_IN_MILLIS;
        startCountdown();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verifyCode.getText().toString().trim();
                if (!code.isEmpty() && code.length() == 6){
                    verifySinginCode();
                }else {
                    Toast.makeText(OTPActivity.this,
                            "Verification code doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerifycode();
                timeLeftMillis  = COUNTDOWN_IN_MILLIS;
                startCountdown();
                resend.setVisibility(View.GONE);
            }
        });
    }
    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftMillis,1000) {
            @Override
            public void onTick(long lon) {
                timeLeftMillis = lon;
                updateCoundown();
            }

            @Override
            public void onFinish() {
                timeLeftMillis = 0;
                updateCoundown();
                resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }
    private void updateCoundown() {
        int min = (int)(timeLeftMillis / 1000) / 60;
        int sec = (int)(timeLeftMillis / 1000) % 60;
        String timeFor = String.format(Locale.getDefault(),"%02d:%02d",min,sec);
        textViewCountDown.setText(timeFor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user =  mAuth.getCurrentUser();
        if (user != null){
            sendMainMenu();
        }
    }
    private void sendMainMenu() {
        Intent intent = new Intent(OTPActivity.this,DrawerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    private void sendVerifycode() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+88"+
                        phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            e.getMessage();
            Toast.makeText(OTPActivity.this, "তথ্য পাওয়া যায়নি", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSend = s;
        }
    };

    private void verifySinginCode() {
        progressBar.setVisibility(View.VISIBLE);
        String code = verifyCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSend,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    sendMainMenu();
                    finish();

                }else {
                    progressBar.setVisibility(View.GONE);
                    if (task.getException() instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(OTPActivity.this,
                                "ভুল যাচাই কোড", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void sharePrefValueUpdate(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY, checker);
        editor.commit();
    }
}
