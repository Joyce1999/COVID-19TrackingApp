package com.CA326MyBubble.Layout_UI.bubble;

import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.CA326MyBubble.App_Activities.MainActivity;
import com.CA326MyBubble.App_Constructors.BubbleArray;
import com.CA326MyBubble.App_Interfaces.FragListener;
import com.CA326MyBubble.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BubbleFragment extends Fragment {

    private EditText emailField;
    private TextView bubbleDescription;
    private Button bubbleButton;
    private Button clearBubble;

    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_bubble, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        emailField = (EditText) root.findViewById(R.id.bubbleEmail);
        bubbleButton = (Button) root.findViewById(R.id.bubbleButton);
        bubbleDescription = (TextView) root.findViewById(R.id.bubbleDescription);
        clearBubble = (Button) root.findViewById(R.id.clearBubble);


        bubbleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bubbleEmail = emailField.getText().toString();

                if (!TextUtils.isEmpty(bubbleEmail)) {
                    DocumentReference docRef = db.collection("Emails").document(bubbleEmail);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String bubbleAddr = document.get("BT_Add").toString();
                                    BubbleArray.bubbleAddrs.add(bubbleAddr);
                                    Toast.makeText(root.getContext(), "Success!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(root.getContext(), "Error: There is no user associated with this email address", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                System.out.println("Failure");
                            }
                        }
                    });
                }
                emailField.setText("");
            }
        });

        clearBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BubbleArray.bubbleAddrs.clear();
                Toast.makeText(getContext(),"Bubble Cleared!",Toast.LENGTH_SHORT).show();
        }
        });
        return root;
    }
}

