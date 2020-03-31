package com.example.krishibondhuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Email_Login extends AppCompatActivity {
    EditText editText1,editText2;
    TextView signup_new;
    Button login_button;
    FirebaseAuth mAuth;
    String userMobile;


    public static final String KEY = "KEY";
    int checker = 1;

    public static final String USER_PREF = "USER_PREF" ;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_email__login );
        editText1 = findViewById(R.id.login_email_id);
        editText2 = findViewById(R.id.login_password_id);
        signup_new = findViewById(  R.id.account_create_id );
        login_button = findViewById(R.id.button_login_id);
        mAuth = FirebaseAuth.getInstance();

//        Bundle extra = getIntent().getExtras();
//        if (extra!=null){
//            userMobile = extra.getString("MobileNumber");
//        }

        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        sharePrefValueUpdate();

        final ProgressDialog progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "দয়া করে অপেক্ষা করুন" );

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText1.getText().toString().trim();
                String password = editText2.getText().toString();


                if (!TextUtils.isEmpty( email ) && !TextUtils.isEmpty( password ) ){
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                if (mAuth.getCurrentUser().isEmailVerified()){
                                    progressDialog.dismiss();
                                    Intent intent = new Intent( Email_Login.this, DrawerActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    intent.putExtra( "UserMobile",userMobile );
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "স্বাগতম", Toast.LENGTH_SHORT).show();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "দয়া করে ই-মেল যাচাই করুন", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "বাতিল করা হয়েছে", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText( getApplicationContext(), "সঠিক ই-মেইল এবং পাসওয়ার্ড প্রবেশ করান ", Toast.LENGTH_SHORT ).show();
                }
            }
        });

        signup_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Email_Registration.class);
                startActivity(intent);
            }
        });
    }

    private void sharePrefValueUpdate() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(KEY, checker);
        editor.commit();
    }
}
