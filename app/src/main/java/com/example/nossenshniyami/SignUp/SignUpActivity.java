package com.example.nossenshniyami.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossenshniyami.Account.Account;
import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.MainActivity;
import com.example.nossenshniyami.R;
import com.example.nossenshniyami.Reposetory.Reposetory;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextEmail, editTextPassword, editTextAddress, editTextPhoneNumber;
    private Button btnSignUp;
    private FirebaseAuth mAuth;

    private FirebaseHelper firebaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        btnSignUp = findViewById(R.id.btnSignUp);
        firebaseHelper = new FirebaseHelper(this);
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



                //הרשמה
                Account account = new Account(email,password);
                Reposetory reposetory = new Reposetory(firebaseHelper,SignUpActivity.this);
                Map<String, Object> user = new HashMap<>();
                                    user.put("FullName", editTextFullName.getText().toString().trim());
                                    user.put("Email", editTextEmail.getText().toString().trim());
                                    user.put("Password", editTextPassword.getText().toString().trim());
                                    user.put("Address", editTextAddress.getText().toString().trim());
                                    user.put("PhoneNumber", editTextPhoneNumber.getText().toString().trim());
//

                reposetory.SigNUp(account,user);
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
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