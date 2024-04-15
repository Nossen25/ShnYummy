package com.example.nossenshniyami.Account;

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
}
