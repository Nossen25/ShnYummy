package com.example.nossenshniyami.Maps;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.nossenshniyami.BusinessModel.Business;
import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;
import com.example.nossenshniyami.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ImageButton btnBackH, btnCirclzition;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
     //מכין את המפה
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnBackH = findViewById(R.id.btnBackH);
        btnCirclzition = findViewById(R.id.btnCirclzition);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnBackH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       //פעולה שמציגה את הסימונים
        EZUI();
        btnCirclzition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //בודק עבור הרשאת מיקום
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // אם אין בקש הרשאת מיקום
                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                    return;
                }

                // תשיג את המיקום הנוכחי
                fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                           //מעביר את המסך למיקום הנוכחי
                            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title("Your Location"));
                        }
                    }
                });
            }
        });



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              //ניתנה גישה
            }
            //אם לא ניתנה גישה
            else
            {
                Toast.makeText(this, "צריך לאפשר גישה למיקום כדי להשתמש בכפתוטר זה", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void addMarkerForBusiness(String businessName, String businessAddress) {
        // משיג את הקורדינטות של העסק
        Geocoder geocoder = new Geocoder(this);

        try {

            List<Address> addresses = geocoder.getFromLocationName(businessAddress, 1);

            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                double latitude = address.getLatitude();
                double longitude = address.getLongitude();

              //מוסיף סימון על המפה
                LatLng businessLocation = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(businessLocation).title(businessName));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(businessLocation, 15)); //תעשה זום על המיקום הנוכחי
            } else {
               //במקרה שלא מוצא קורדינטות
                Toast.makeText(this, "Failed to geocode address"+ " "+ businessName, Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            //מטפל במקרה
            Toast.makeText(this, "Geocoding failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //פעולה שמוסיפה את הסימונים על המפה
    private void EZUI()
    {
        db.collection("Business")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {

                            LinkedList<Business> tempL = new LinkedList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Business bus = new Business();

                                addMarkerForBusiness(document.getData().get("BusinessName").toString(),document.getData().get("BusinessAddress").toString());
                            }

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

}
