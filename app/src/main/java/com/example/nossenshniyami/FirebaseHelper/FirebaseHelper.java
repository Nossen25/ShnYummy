package com.example.nossenshniyami.FirebaseHelper;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.nossenshniyami.BusinessModel.Business;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class FirebaseHelper {
   private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;




    public FirebaseHelper() {
        this.firestore = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
    }


    public interface userFound {
        void onUserFound(String name, String phone);
    }

    public interface Completed {
        void onComplete(Boolean flag);
    }

    //פעולה שלפי האימייל של המשתמש "מוציאה" את הטלפון והשם המלא שלו
    public void getUserInfo(String email, userFound callback) {

        firestore.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.getData().get("Email").equals(email)) {
                            callback.onUserFound(document.getData().get("FullName").toString(), document.getData().get("PhoneNumber").toString());
                        }
                    }
                    if (task.getResult() == null) {
                        callback.onUserFound(null, null);
                    }



                }
            }
        });
    }
//פעולת ההתחברות לפי אימייל וסיסמא
    public void SignIn(String email, String password, Completed callback) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // התחבר בהצלחה תעדכן את הUI למשתמש המחובר
                            FirebaseUser user = mAuth.getCurrentUser();
                            callback.onComplete(true);
                        } else {
                            callback.onComplete(false);
                        }
                    }
                });
    }

//פעולת הרישום לפי אימייל וסיסמא שומר את הנתונים אבל מתחבר רק לפי אימייל וסיסמא
    public void SignUp(Context context, String email, String password, Map<String, Object> user) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // הרישום צלח
                        firestore.collection("Users") // Adjust collection name as needed
                                .add(user)
                                .addOnSuccessListener(documentReference ->
                                        Toast.makeText(context, "Sign Up Success", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error adding document", e);
                                    Toast.makeText(context, "Error adding user data", Toast.LENGTH_SHORT).show();
                                });
                    } else {

                        Toast.makeText(context, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public interface ListOfBus {
        void onGotBus(LinkedList<Business> listOfBus);
    }
//תציג את כל העסקים במסך הבית
    public void ReadAllBusData(ListOfBus callback) {
        firestore.collection("Business")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            LinkedList<Business> tempL = new LinkedList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Business bus = new Business();
                                bus.setAddress(document.getData().get("BusinessAddress").toString());
                                bus.setName(document.getData().get("BusinessName").toString());
                                bus.setPhoneNumber(document.getData().get("PhoneNumber").toString());

                                // תכניס את הURL מהסטורג' של פיירסטור
                                String imageURL = document.getData().get("Image").toString();
                                bus.setImageURL(imageURL);

                                tempL.add(bus);
                            }
                            callback.onGotBus(tempL);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
    public interface InfoCallback {
        void onInfoReceived(String info);
    }
//תציג את המידע של העסק במסך הבית כאשר לוחצים על עסק
public void GetInfoF(String phoneNumber, InfoCallback callback) {
    firestore.collection("Business")
            .whereEqualTo("PhoneNumber", phoneNumber)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String info = document.getString("Basic Info");
                        callback.onInfoReceived(info);
                        return; //ברגע שמצאת עסק עם המספר טלפון הזה תחזיר את המידע שלו
                    }
                    // אם לא מצא עסק שתואם להגדרה
                    callback.onInfoReceived(null);
                } else {

                    Log.e(TAG, "Failed to get documents.", task.getException());
                    callback.onInfoReceived(null);
                }
            });
}
//פעולת העדכון לפי אימייל כלומר מעדכן הכל אבל מזהה את מי לעדכן לפי האימייל
    public void Update(String email, String name, String password, String address, String phoneNumber, int toApp, whenDone callback) {
        firestore.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskUserList) {
                        if (taskUserList.isSuccessful()) {
                            for (QueryDocumentSnapshot document : taskUserList.getResult()) {
                                if (document.getData().get("Email").toString().equals(email)) {
                                    Map<String, Object> userData = new HashMap<>();
                                    if (toApp == -1) {
                                        userData.put("FullName", name);
                                        userData.put("Email", email);
                                        userData.put("Password", password);
                                        userData.put("Address", address);
                                        userData.put("PhoneNumber", phoneNumber);
                                        firestore.collection("Users").document(document.getId()).update(userData).
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    callback.whenDoneToUpdate();
                                                } else {
                                                    Log.w(TAG, "Error updating document.", task.getException());
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", taskUserList.getException());
                        }
                    }
                });
    }

    public interface whenDone {
        void whenDoneToUpdate();
        void whenDoneToDelete();
    }
    //מחיקה לפי האימייל מוחק את המשתמש לפי האימייל שלו
    public void delete(String email, whenDone callback) {
        firestore.collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> taskUserList) {
                        if (taskUserList.isSuccessful()) {
                            for (QueryDocumentSnapshot document : taskUserList.getResult()) {
                                if (document.getData().get("Email").toString().equals(email)) {
                                    firestore.collection("Users").document(document.getId()).delete().
                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                callback.whenDoneToDelete();
                                            } else {
                                                Log.w(TAG, "Error deleting document.", task.getException());
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", taskUserList.getException());
                        }
                    }
                });
    }
}













