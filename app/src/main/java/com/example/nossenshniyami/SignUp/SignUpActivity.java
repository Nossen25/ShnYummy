package com.example.nossenshniyami.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossenshniyami.Account.Account;
import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAddress, editTextPhoneNumber;
    private Button btnSignUp, btnReturn;
    private FirebaseHelper firebaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnReturn = findViewById(R.id.btnReturn);
        firebaseHelper = new FirebaseHelper();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName = editTextFullName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();


                SignUpMoudle signUpMoudle = new SignUpMoudle(firebaseHelper,SignUpActivity.this);


                if (signUpMoudle.Check(fullName, email, password, address, phoneNumber, SignUpActivity.this)) {
                    //הרשמה
                    Account account = new Account(email, password);
                    Map<String, Object> user = new HashMap<>();
                    user.put("FullName", editTextFullName.getText().toString().trim());
                    user.put("Email", editTextEmail.getText().toString().trim());
                    user.put("Password", editTextPassword.getText().toString().trim());
                    user.put("Address", editTextAddress.getText().toString().trim());
                    user.put("PhoneNumber", editTextPhoneNumber.getText().toString().trim());
                    signUpMoudle.SignUp(account,user);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);


                }

            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}