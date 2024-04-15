package com.example.nossenshniyami.Admin;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nossenshniyami.MainActivity;
import com.example.nossenshniyami.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {
private Button btnAddBusiness;
private TextView textesek;
private EditText BusinessName,BusinessAddress,PhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnAddBusiness=findViewById(R.id.btnAddBusiness);
        textesek=findViewById(R.id.textesek);
        BusinessName=findViewById(R.id.BusinessName);
        BusinessAddress=findViewById(R.id.BusinessAddress);
        PhoneNumber=findViewById(R.id.PhoneNumber);
btnAddBusiness.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v)
    {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("BusinessName", BusinessName.getText().toString().trim());
        user.put("BusinessAddress", BusinessAddress.getText().toString().trim());
        user.put("PhoneNumber", PhoneNumber.getText().toString().trim());

// Add a new document with a generated ID
        db.collection("Business")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
        Toast.makeText(AdminActivity.this, " Sign Up Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(AdminActivity.this, MainActivity.class);
        startActivity(intent);
    }
});


    }
}

