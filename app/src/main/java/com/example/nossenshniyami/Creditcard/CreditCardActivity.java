package com.example.nossenshniyami.Creditcard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;
import com.google.firebase.auth.FirebaseAuth;

public class CreditCardActivity extends AppCompatActivity implements View.OnClickListener {
private EditText editTextFullName,editTextEmail,editTextPhoneNumber,editTextCardNumber,editTextExpiryDate,editTextCVC;
private Button btnAddCredit;
private static boolean creditOnff=false;
private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        editTextFullName=findViewById(R.id.editTextFullName);
        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPhoneNumber=findViewById(R.id.editTextPhoneNumber);
        editTextCardNumber=findViewById(R.id.editTextCardNumber);
        editTextExpiryDate=findViewById(R.id.editTextExpiryDate);
        editTextCVC=findViewById(R.id.editTextCVC);
        mAuth = FirebaseAuth.getInstance();
        btnAddCredit=findViewById(R.id.btnAddCredit);
        editTextEmail.setText(mAuth.getCurrentUser().getEmail());
        btnAddCredit.setOnClickListener(this);

//מציג את המידע של המשתמש באדיטקסט
       ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Fetching User");
        pd.setCancelable(false);
        pd.show();
        CreditCardModule creditCardModule = new CreditCardModule(CreditCardActivity.this);
        creditCardModule.GetInfo(mAuth, new FirebaseHelper.userFound() {
            @Override
            public void onUserFound(String name, String phone) {
                editTextFullName.setText(name);
                editTextPhoneNumber.setText(phone);
                pd.dismiss();
            }
        });

        if (editTextPhoneNumber.getText().toString()==null)
        {
            Intent intent =new Intent(CreditCardActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View v)
    {

     if (v==btnAddCredit)
     {
         String name = editTextFullName.getText().toString().trim();
         String email = editTextEmail.getText().toString().trim();
         String phone = editTextPhoneNumber.getText().toString().trim();
         String cardnum = editTextCardNumber.getText().toString().trim();
         String tokef = editTextExpiryDate.getText().toString().trim();
         String cvc = editTextCVC.getText().toString().trim();
        CreditCardModule creditCardModule=new CreditCardModule();
        if (!creditCardModule.CheckCreditcard(name,email,phone,cardnum,tokef,cvc,CreditCardActivity.this))
        {
            return;
        }
        else
        {
            creditOnff=true;
            Intent intent = new Intent(CreditCardActivity.this,MainActivity.class);
            intent.putExtra("creditonff",creditOnff);
            startActivity(intent);
        }


     }

    }
}
