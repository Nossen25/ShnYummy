package com.example.nossenshniyami.HelpAboutUs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nossenshniyami.Main.MainActivity;
import com.example.nossenshniyami.R;

public class HelpUs extends AppCompatActivity {
private Button aboutUsButton,helpButton,BackHome;
private TextView infoTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_us);
        helpButton=findViewById(R.id.helpButton);
        aboutUsButton=findViewById(R.id.aboutUsButton);

        infoTextView=findViewById(R.id.infoTextView);
        BackHome=findViewById(R.id.backHomeButton);
        infoTextView.setTextSize(18);
        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTextView.setText("Welcome to our app!\n\n"
                        + "We're dedicated to reducing food waste by connecting you with delicious, unsold food from local restaurants, cafes, bakeries, and supermarkets at a reduced price. "
                        + "Our mission is to save food, save money, and support a more sustainable planet.\n\n"
                        + "Simply choose your food box, buy the food through the app, Contact the business directly to arrange pickup and enjoy your delicious food.\n "
                        + "Join us in making a positive impact on both your wallet and the environment. Together, let's enjoy great food and contribute to a greener future!");
                infoTextView.setVisibility(View.VISIBLE);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoTextView.setText("Welcome! If you need assistance, you’ve come to the right place. Before heading back to the home page by clicking the BACK HOME button, let me guide you through some features of the app:\n" +
                        "Surplus Food:\n" +
                        "\n" +
                        "On the home page, you will find a variety of surplus food available from local businesses.\n" +
                        "Click on the photos to view details and add items to your cart.\n" +
                        "Remember to log in, sign up, or add your credit card in the account page and shopping page before adding items to your cart.\n" +
                        "Pickup Information:\n" +
                        "\n" +
                        "Unsure where to pick up your surplus food? Check the map for locations.\n" +
                        "Contact the business directly to arrange pickup and enjoy your delicious food.\n" +
                        "Managing Your Account:\n" +
                        "\n" +
                        "Want to add a business, update, or delete your user profile?\n" +
                        "Use the respective buttons on the home page to manage your account.");
                infoTextView.setVisibility(View.VISIBLE);
            }
        });
        BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpUs.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}