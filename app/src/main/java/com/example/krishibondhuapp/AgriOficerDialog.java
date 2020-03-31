package com.example.krishibondhuapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;

import static androidx.fragment.app.DialogFragment.*;


public class AgriOficerDialog extends DialogFragment {

    private EditText email,password;
    private Button loginButton;

    private FirebaseAuth mAuth;

    static AgriOficerDialog newInstance() {
        return new AgriOficerDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle( STYLE_NORMAL, R.style.FullScreenDialog);

    }


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.agri_officer_loginscreen_dialog, container, false);

        mAuth = FirebaseAuth.getInstance();

        ImageButton close = view.findViewById(R.id.close_Image_Button_Id);

        email = view.findViewById(R.id.admin_EmailEditTextId);
        password = view.findViewById(R.id.admin_PassEditText_Id);
        loginButton = view.findViewById(R.id.admin_LoginButton_Id);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = email.getText().toString();
                String s2 = password.getText().toString();
                if (!TextUtils.isEmpty(s1) && !TextUtils.isEmpty(s2)){
                    signin();
                }else {
                    Toast.makeText(getContext(), "Please Enter Valid Email and Password", Toast.LENGTH_SHORT).show();
                    email.setError("Enter Valid Email");
                    password.setError("Enter valid Password");
                }
            }
        });



        return view;
    }

    private void signin() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String e1 = email.getText().toString();
        String e2 = password.getText().toString();
        mAuth.signInWithEmailAndPassword(e1, e2)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
//                            Intent intent = new Intent(getContext(),FragmentAgriOfficer.class);
//                            startActivity(intent);
                            Toast.makeText(getContext(), "Matched", Toast.LENGTH_SHORT).show();
                            dismiss();
                            progressDialog.dismiss();
                            inputDialogCall();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Email and Password does not match.",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }
                });

    }

    public void inputDialogCall(){

        DialogFragment dialog = AgriOficerInputDialog.newInstance();
        dialog.show(getFragmentManager(), "tag");
    }

}
