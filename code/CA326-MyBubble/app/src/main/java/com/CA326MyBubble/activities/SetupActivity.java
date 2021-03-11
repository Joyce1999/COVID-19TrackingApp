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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.HashMap;
import java.util.Map;

public class SetupActivity extends AppCompatActivity {

    private EditText setupFullName;
    private EditText MobileNumber;
    private EditText setupAddress;
    private EditText setupGender;
    private String user_id;
    Context context = this;
    SimpleArcLoader simpleArcLoader;

    private Button button_setup;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user_id = firebaseAuth.getCurrentUser().getUid();

        setupFullName = (EditText) findViewById(R.id.setupFullName);
        MobileNumber = (EditText) findViewById(R.id.MobileNumber);
        setupAddress = (EditText) findViewById(R.id.setupAddress);
        setupGender = (EditText) findViewById(R.id.setupGender);
        button_setup = (Button) findViewById(R.id.button_setup);
        simpleArcLoader = findViewById(R.id.loader);

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        Toast.makeText(SetupActivity.this, "Data exists", Toast.LENGTH_LONG).show();
                        String Full_name = task.getResult().getString("Full_name");
                        String Mobile_Number = task.getResult().getString("Mobile_Number");
                        String Address = task.getResult().getString("Address");
                        String Gender = task.getResult().getString("Gender");

                        setupFullName.setText(Full_name);
                        MobileNumber.setText(Mobile_Number);
                        setupAddress.setText(Address);
                        setupGender.setText(Gender);
                    } else {
                        Toast.makeText(SetupActivity.this, "Data does not exist", Toast.LENGTH_LONG).show();
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(SetupActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                }
            }
        });

        button_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = setupFullName.getText().toString();
                String mobile = MobileNumber.getText().toString();
                String address = setupAddress.getText().toString();
                String gender = setupGender.getText().toString();
                String btAddr = android.provider.Settings.Secure.getString(context.getContentResolver(), "bluetooth_address");

                if (!TextUtils.isEmpty(user_name)) {
                    String user_id = firebaseAuth.getCurrentUser().getUid();
                    Map<String, String> userMap = new HashMap<>();
                    userMap.put("Full_name", user_name);
                    userMap.put("Mobile_Number", mobile);
                    userMap.put("Address", address);
                    userMap.put("Gender", gender);
                    userMap.put("BT_Add",btAddr);
                    simpleArcLoader.start();
                    simpleArcLoader.setVisibility(View.VISIBLE);

                    firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent mainIntent = new Intent(SetupActivity.this, MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(SetupActivity.this, "(FIRESTORE Error) : " + error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}