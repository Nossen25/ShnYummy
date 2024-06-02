package com.example.nossenshniyami.Admin;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;

public class AdminActivity extends AppCompatActivity {
    private Button btnAddBusiness;
    private Button btnAddPhoto;
    private ImageView imageView;
    private static Bitmap photo;
    private static final int CAMERA_REQUEST_CODE = 250;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private EditText BusinessName, BusinessAddress, PhoneNumber, BInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        askAboutCamera();
        setContentView(R.layout.activity_admin);
         BInfo = findViewById(R.id.BInfo);
        btnAddBusiness = findViewById(R.id.btnAddBusiness);
        btnAddPhoto = findViewById(R.id.btnAddPhoto);
        imageView = findViewById(R.id.imgview);
        BusinessName = findViewById(R.id.BusinessName);
        BusinessAddress = findViewById(R.id.BusinessAddress);
        PhoneNumber = findViewById(R.id.PhoneNumber);
        AdminMoudle adminMoudle= new AdminMoudle();


        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //פותח מצלמה
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                CameraResultLauncher.launch(cameraIntent);
            }
        });





        btnAddBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = BusinessName.getText().toString().trim();
                String address = BusinessAddress.getText().toString().trim();
                String phone = PhoneNumber.getText().toString().trim();
                String info = BInfo.getText().toString().trim();

            //אם הדרישות עובדות אז תוסיף את העסק
                if (adminMoudle.CheckIfBusOk(name,address,phone,info,imageView,AdminActivity.this))
                {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    //ממיר את התמונה לביטים ושומר אותה במערך ביטים
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    byte[] data = outputStream.toByteArray();

                    //מכניס לסטורג'
                    String path = "images/" + UUID.randomUUID() + ".png";
                    StorageReference imagesRef = storage.getReference(path);

                    UploadTask uploadTask = imagesRef.putBytes(data);

                    Map<String, Object> business = new HashMap<>();
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    business.put("Image", uri.toString());
                                    business.put("BusinessName",name);
                                    business.put("BusinessAddress", address);
                                    business.put("PhoneNumber", phone);
                                    business.put("Basic Info", info);

                                    db.collection("Business")
                                            .add(business)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                                               //מבקש גישה לסמס
                                                    if (ContextCompat.checkSelfPermission(AdminActivity.this, Manifest.permission.SEND_SMS) !=
                                                            PackageManager.PERMISSION_GRANTED) {
                                                        ActivityCompat.requestPermissions(AdminActivity.this, new String[]{Manifest.permission.SEND_SMS}
                                                                , MY_PERMISSIONS_REQUEST_SEND_SMS);
                                                    } else {
                                                        sendSms();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w(TAG, "Error adding document", e);
                                                }
                                            });

                                    Toast.makeText(AdminActivity.this, "העסק התווסף", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdminActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
                }
            }

                } );


    }

    //מבקש הרשאה לסמס
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
//הודעת הסמס
    private void sendSms() {
        String str = "תודה שבחרת להוסיף עסק";
        SmsManager smsManager = SmsManager.getDefault();
        String phone = PhoneNumber.getText().toString().trim();
        smsManager.sendTextMessage(phone, null, str, null, null);
    }

//



//פעות פתיחת מצלמה ושמירת התמונה
    ActivityResultLauncher<Intent> CameraResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        //שומר את התמונה
                        photo = (Bitmap) result.getData().getExtras().get("data");
                        imageView.setImageBitmap(photo);
                    }
                }
            });

    //ספריה חיצונית ששואלת עבור הרשאה למצצלמה
    private void askAboutCamera(){

        EasyPermissions.requestPermissions(this, "From this point on camera permission is required.",
                CAMERA_REQUEST_CODE, Manifest.permission.CAMERA );

    }



}

