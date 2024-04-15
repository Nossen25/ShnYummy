package com.example.nossenshniyami;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nossenshniyami.Account.AccountFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setSelectedItemId(R.id.Home);


        bottom_navigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.Account) {

                replaceFragment(new AccountFragment());
            } else if (itemId == R.id.Home) {

                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.Cart) {
                replaceFragment(new ShoppingFragment());
            }
            return true;
        });

        //יקבל מהקרדיט קרד אינטנט ויעביר לאקונט פרגמט ואז יציג הערה

    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment); // Use the correct container view ID
        fragmentTransaction.commit();

    }

    public void ReplaceNavBar()
    {
        bottom_navigation.setSelectedItemId(R.id.Account);
    }

}