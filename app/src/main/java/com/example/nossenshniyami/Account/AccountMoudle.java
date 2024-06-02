package com.example.nossenshniyami.Account;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AccountMoudle
{
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();


    public AccountMoudle(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public Boolean CanLogIn()
    {
        if (firebaseAuth.getCurrentUser()==null)
        {
            return  true;
        }

        return  false;
    }
    public Boolean CanLogOut()
    {
        if (firebaseAuth.getCurrentUser()!=null)
        {
            return  true;
        }

        return  false;
    }

    public Boolean LogInCheck(Account account,Context context)
    {
        if(account.getEmail().equals(""))
        {
            Toast.makeText(context, "אסור להשאיר את האימייל ריק", Toast.LENGTH_SHORT).show();
            return  false;

        }
        if(account.getPassword().equals(""))
        {
            Toast.makeText(context, "אסור להשאיר את הסיסמא ריקה", Toast.LENGTH_SHORT).show();
            return  false;
        }

        return  true;
    }


}
