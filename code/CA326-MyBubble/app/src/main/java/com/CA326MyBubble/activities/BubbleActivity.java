package com.CA326MyBubble.activities;

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
import com.CA326MyBubble.constructors.BubbleArray;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BubbleActivity extends AppCompatActivity {

    private EditText emailField;
    private Button bubbleButton;
    private Button leaveBubble;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        emailField = (EditText) findViewById(R.id.bubbleEmail);
        bubbleButton = (Button) findViewById(R.id.bubbleButton);
        leaveBubble = (Button) findViewById(R.id.leaveBubble);

        ArrayList<String> bubbleList = new ArrayList<String>();

        bubbleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bubbleEmail = emailField.getText().toString();

                if (!TextUtils.isEmpty(bubbleEmail)) {
                    DocumentReference docRef = db.collection("Emails").document(bubbleEmail);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()) {
                                    String bubbleAddr = document.get("BT_Add").toString();
                                    BubbleArray.bubbleAddrs.add(bubbleAddr);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"Error: There is no user associated with this email address", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                System.out.println("Failure");
                            }
                        }
                    });
                }
                emailField.setText("");
            }
        });

        leaveBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        });
    }
}
