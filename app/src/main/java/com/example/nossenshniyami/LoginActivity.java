package com.example.nossenshniyami;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private TextView tvSignUp;
    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextPassword = findViewById(R.id.editTextPassword);
//        btnLogin = findViewById(R.id.btnLogin);
//        tvSignUp = findViewById(R.id.tvSignUp);
//
//        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle login button click
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Implement your login logic here
                // For example, you can check credentials and navigate to the next screen
                // For simplicity, I'm just showing a toast message
                if(editTextEmail.getText().toString().equals(""))
                {
                  editTextEmail.setError("אסור להשאיר ריק");
                }
                if(editTextPassword.getText().toString().equals(""))
                {
                    editTextPassword.setError("אסור להשאיר ריק");
                }
               if(editTextPassword.getText().toString()!=("")&&editTextEmail.getText().toString()!=("")) {
                    Toast.makeText(LoginActivity.this, "Login clicked\nEmail: " + email + "\nPassword: " + password, Toast.LENGTH_SHORT).show();
                }
//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    Toast.makeText(LoginActivity.this, "SS", Toast.LENGTH_SHORT).show();
//                                }
//                                else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//
//                                }
//                            }
//                        });
            }
        });
    }

    public void openSignUpActivity(View view) {
        // Handle sign-up button click
        // You can navigate to the sign-up activity or perform any other action
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}