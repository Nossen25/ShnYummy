package com.example.nossenshniyami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAddress, editTextPhoneNumber;
    private Button btnSignUp,btnBack;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        btnBack=findViewById(R.id.btnBack);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-up button click
                String fullName = editTextFullName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();


                if(editTextFullName.getText().toString().trim().equals(""))
                {
                    editTextFullName.setError("נא למלא שם מלא");
                    return;
                }
                if (containsHebrew(editTextFullName.getText().toString().trim())==(false))
                {
                    editTextFullName.setError("שם מלא רק בעברית");
                    return;
                }
                if(editTextEmail.getText().toString().trim().equals(""))
                {
                    editTextEmail.setError("נא למלא אימייל");
                    return;
                }
                if(!isValidEmail(editTextEmail.getText().toString().trim()))
                {
                    editTextEmail.setError("מייל לא תקין");
                    return;
                }
                if(editTextPassword.getText().toString().trim().equals(""))
                {
                    editTextPassword.setError("נא למלא סיסמא");
                    return;
                }
                if(editTextAddress.getText().toString().trim().equals(""))
                {
                    editTextAddress.setError("נא למלא כתובת");
                    return;
                }
                if(editTextPhoneNumber.getText().toString().trim().equals(""))
                {
                    editTextPhoneNumber.setError("נא למלא מספר טלפון");
                    return;
                }



                // Implement your sign-up logic here
                // For simplicity, I'm just showing a toast message
//                Toast.makeText(SignUpActivity.this, "Sign Up clicked\nFull Name: " + fullName +
//                        "\nEmail: " + email + "\nPassword: " + password +
//                        "\nAddress: " + address + "\nPhone Number: " + phoneNumber, Toast.LENGTH_SHORT).show();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(SignUpActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = mAuth.getCurrentUser();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public static boolean containsHebrew(String text) {
        // טווח של תווים בעברית
        Pattern pattern = Pattern.compile("[\u0590-\u05FF]+");
        Matcher matcher = pattern.matcher(text);

        // בדיקה האם יש תווים בעברית בטקסט
        return matcher.find();
    }
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}