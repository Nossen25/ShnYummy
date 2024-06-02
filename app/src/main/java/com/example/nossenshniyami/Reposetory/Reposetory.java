package com.example.nossenshniyami.Reposetory;

import android.content.Context;

import com.example.nossenshniyami.Account.Account;
import com.example.nossenshniyami.FirebaseHelper.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

public class Reposetory
{
    //מפעיל פעולות מתוך הפיר בייס + שמןור את המידע פה נשמר בתור ליסט או כל דבר + מפפה לוקח את המידע שצריך
    private FirebaseHelper firebaseHelper;
    private Context context;



    public Reposetory(FirebaseHelper firebaseHelper, Context context) {
        this.firebaseHelper = new FirebaseHelper();
        this.context = context;

    }
    //קוראת לפעולה של הצגת מידע עבור משתמש בהוספת כרטיס אשראי
public void GetInfo(FirebaseAuth ma,FirebaseHelper.userFound userFound)
{
    FirebaseHelper fbg = new FirebaseHelper();
    fbg.getUserInfo(ma.getCurrentUser().getEmail(), new FirebaseHelper.userFound() {
        @Override
        public void onUserFound(String name, String phone) {
            userFound.onUserFound(name,phone);
        }
    });
}

    public void signIN(Account accountMoudle, FirebaseHelper.Completed callback)
    {
        firebaseHelper = new FirebaseHelper();
        firebaseHelper.SignIn(accountMoudle.getEmail(),accountMoudle.getPassword(), new FirebaseHelper.Completed() {
            @Override
            public void onComplete(Boolean flag) {
                callback.onComplete(flag);
            }
        });
    }
    public void ReadAllBusData(FirebaseHelper.ListOfBus callback){
        firebaseHelper.ReadAllBusData(callback);
    }
//

    public void SigNUp(Account accountMoudle, Map<String,Object> map)
    {
        firebaseHelper.SignUp(context, accountMoudle.getEmail(), accountMoudle.getPassword(),map );
    }
    public void Update(String email, String name, String password, String address, String phoneNumber,
                       int toApp, FirebaseHelper.whenDone callback) {
        firebaseHelper.Update(email, name, password, address, phoneNumber, toApp, callback);
    }
    public void delete(String email, FirebaseHelper.whenDone callback) {
        firebaseHelper.delete(email, callback);
    }
}
