package com.CA326MyBubble.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.CA326MyBubble.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {

    private EditText reg_fullname_field;
    private EditText reg_email_field;
    private EditText reg_pass_field;
    private EditText reg_conf_pass_field;
    private Button reg_btn;
    private Button reg_login_btn;
    SimpleArcLoader simpleArcLoader;
    Context context = this;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        reg_email_field = (EditText) findViewById(R.id.regEmail);
        reg_pass_field = (EditText) findViewById(R.id.regPassword);
        reg_conf_pass_field = (EditText) findViewById(R.id.regPasswordconf);
        reg_btn = (Button) findViewById(R.id.button_reg);
        reg_login_btn = (Button) findViewById(R.id.button_login);
        simpleArcLoader = findViewById(R.id.loader);
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

                String email = reg_email_field.getText().toString();
                String pass = reg_pass_field.getText().toString();
                String confirm_pass = reg_conf_pass_field.getText().toString();
                String btAddr = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(confirm_pass) ) {
                    if (pass.equals(confirm_pass)) {
                        Map<String, String> emailsMap = new HashMap<>();
                        emailsMap.put("BT_Add",btAddr);
                        simpleArcLoader.start();
                        simpleArcLoader.setVisibility(View.VISIBLE);

                        firebaseFirestore.collection("Emails").document(email).set(emailsMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(RegisterUser.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent setupIntent = new Intent(RegisterUser.this, SetupActivity.class);
                                    startActivity(setupIntent);
                                    finish();

                                } else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(RegisterUser.this, "Error: " + errorMessage, Toast.LENGTH_LONG);
                                }
                                simpleArcLoader.setVisibility(View.INVISIBLE);
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
