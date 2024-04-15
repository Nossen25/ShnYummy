package com.example.nossenshniyami.FirebaseHelper;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class FirebaseHelper {
    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    Context context;

    public FirebaseHelper(Context context) {
        this.firestore = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.context = context;
    }

    public interface userFound {
        void onUserFound(String name, String phone);
    }

    public interface Completed {
        void onComplete(Boolean flag);
    }

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

                    //

                }
            }
        });
    }

    public void SignIn(String email, String password, Completed callback) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            callback.onComplete(true);
                        } else {
                            callback.onComplete(false);
                        }
                    }
                });
    }


    public void SignUp(Context context, String email, String password, Map<String, Object> user)
    {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User sign-up successful
                        firestore.collection("Users") // Adjust collection name as needed
                                .add(user)
                                .addOnSuccessListener(documentReference ->
                                        Toast.makeText(context, "Sign Up Success", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> {
                                    Log.e(TAG, "Error adding document", e);
                                    Toast.makeText(context, "Error adding user data", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // User sign-up failed
                        Toast.makeText(context, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Other helper methods
}

//    public void SignUp(String email, String password, Map<String, Object> user) {
//
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//
//
//                            // Add a new document with a generated ID
//                            firestore.collection("U8*95/7l,;insers")
//                                    .add(user)
//                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                        @Override
//                                        public void onSuccess(DocumentReference documentReference) {
//
//
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//
//                                        }
//                                    });
//
////                                      את המעבר דף באקטיביטי הרגיל
////                            // Sign in success, update UI with the signed-in user's information
////                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
////                            startActivity(intent);
//                        } else {
//                            // If sign in fails, display a message to the user.
//
//                        }
//                    }
//                });
//
//
//    }}



