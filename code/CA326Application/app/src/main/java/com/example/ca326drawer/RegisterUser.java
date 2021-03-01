package com.example.ca326drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.invoke.ConstantCallSite;

public class RegisterUser extends AppCompatActivity {

    private EditText reg_fullname_field;
    private EditText reg_email_field;
    private EditText reg_pass_field;
    private EditText reg_conf_pass_field;
    private Button reg_btn;
    private Button reg_login_btn;
    private ProgressBar reg_progress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        reg_fullname_field = (EditText) findViewById(R.id.regFullName);
        reg_email_field = (EditText) findViewById(R.id.regEmail);
        reg_pass_field = (EditText) findViewById(R.id.regPassword);
        reg_conf_pass_field = (EditText) findViewById(R.id.regPasswordconf);
        reg_btn = (Button) findViewById(R.id.button_reg);
        reg_login_btn = (Button) findViewById(R.id.button_login);
        reg_progress = (ProgressBar) findViewById(R.id.loginprogress);

        reg_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterUser.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullName = reg_fullname_field.getText().toString();
                String email = reg_email_field.getText().toString();
                String pass = reg_pass_field.getText().toString();
                String confirm_pass = reg_conf_pass_field.getText().toString();

                if (!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass) ) {
                    if (pass.equals(confirm_pass)) {

                        reg_progress.setVisibility(View.VISIBLE);
                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    sendToMain();
                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterUser.this, "Error: " + errorMessage, Toast.LENGTH_LONG);
                                }
                                reg_progress.setVisibility(View.INVISIBLE);
                            }
                        });
                    } else {
                        Toast.makeText(RegisterUser.this, "Password does not match", Toast.LENGTH_LONG);
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(RegisterUser.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}