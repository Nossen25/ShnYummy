package com.example.nossenshniyami.Updel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;
import com.google.firebase.auth.FirebaseAuth;

public class UpdelActivty extends AppCompatActivity {


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText editTextFullName,editTextEmail,editTextPassword,editTextAddress,editTextPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updel_activty);
        editTextFullName = findViewById(R.id.editTextFullName);
          editTextEmail = findViewById(R.id.editTextEmail);
          editTextPassword = findViewById(R.id.editTextPassword);
          editTextAddress = findViewById(R.id.editTextAddress);
          editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        Context context =  UpdelActivty.this;
        UpdelMoudle updelModule = new UpdelMoudle(context);
        String name = editTextFullName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String pass = editTextPassword.getText().toString().trim();
        String add = editTextAddress.getText().toString().trim();
        String phone = editTextPhoneNumber.getText().toString().trim();
        Button buttonDelete = findViewById(R.id.buttonDelete);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
//כמו העדכון רק למחיקה מאשר לפי אימייל ומוחק נוכחי
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(context, "בשביל למחוק צריך להכניס אימייל למחיקה", Toast.LENGTH_SHORT).show();
                } else {
                    // Call delete method
                    updelModule.delete(email, new FirebaseHelper.whenDone() {
                        @Override
                        public void whenDoneToUpdate() {
                            // This method is required by the interface but not needed here
                        }

                        @Override
                        public void whenDoneToDelete() {
                            Toast.makeText(context, "המשתמש נמחק", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);

                        }
                    });
                }
            }
        });






//מעדכן לפי אימייל מקבל את הנתונים ומעדכן את הנוכחי

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {



               if (updelModule.CheckBeforeUpdate(name,email,pass,add,phone,UpdelActivty.this))
               {
                   updelModule.Update(email, name, pass, add, phone, -1, new FirebaseHelper.whenDone() {
                       @Override
                       public void whenDoneToUpdate() {
                           Toast.makeText(context, "המשתמש עודכן", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(context, MainActivity.class);
                           startActivity(intent);
                       }

                       @Override
                       public void whenDoneToDelete() {
                           // This method is required by the interface but not needed here
                       }
                   });
               }
               else {return;}
            }
        });

               }
                }



