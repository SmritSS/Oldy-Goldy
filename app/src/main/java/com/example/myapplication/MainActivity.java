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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ViewPager viewPager = findViewById(R.id.viewPager);

        // Add example images
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.trunk);
        imageList.add(R.drawable.trunk2);

        // Set up the adapter
        ImageAdapter adapter = new ImageAdapter(this, imageList);
        viewPager.setAdapter(adapter);
    }
    profile.setOnClickListener(new ViewOnClickListener()){
        @Override
                public void onClick(View v){
                    startActivity(new Intent(MainActivity.this, MainActivity.this));
        }
    }

}