package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get references to the buttons
        ImageView contactButton = findViewById(R.id.contactButton);
        ImageView locationButton = findViewById(R.id.locationButton);

        // Set onClick listeners
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to execute when the contact button is clicked
                Toast.makeText(MainActivity.this, "Contact Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code to execute when the location button is clicked
                Toast.makeText(MainActivity.this, "Location Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
