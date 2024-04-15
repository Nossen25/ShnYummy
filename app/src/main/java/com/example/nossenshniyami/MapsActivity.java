package com.example.nossenshniyami;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.nossenshniyami.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private ImageButton btnBackH, btnCirclzition; // corrected comma
    private FusedLocationProviderClient fusedLocationClient;
    private static final int REQUEST_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

//        btnCirclzition.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Move the camera to the home marker
//                LatLng homeLatLng = new LatLng(31.953770, 34.920349);
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 16.7f));
//
//            }
//        });
    }

//זה אמור להיות למצוא את המיקום שלי לשאול את יואל וגרטי
    //+ לשאול את אביתר בנוגע לדף הראשי והפרגמנטים ולהסביר לו על הפרוייקט ולשאול לדעתו
    //לתקן את איך שההום פייג נראה בטלפון רגיל ולא בdp
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng home = new LatLng(31.953770, 34.920349);
        mMap.addMarker(new MarkerOptions().position(home).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));

        LatLng sy = new LatLng(31, 34);
        mMap.addMarker(new MarkerOptions().position(sy).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(home));

//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
//                    mMap.addMarker(new MarkerOptions()
//                            .position(latLng)
//                            .title("Your Location"));
//                }
//            }
//        });

        btnCirclzition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for location permissions
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Request location permissions if not granted
                    ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                    return;
                }

                // Get last known location
                fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Move the camera to the current location
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
                // Permission granted, perform action that requires permission
                // You can repeat the code inside onClick method here to handle the button click
            } else {
                // Permission denied
                // Handle accordingly, maybe show a message or disable functionality
            }
        }
    }


}
