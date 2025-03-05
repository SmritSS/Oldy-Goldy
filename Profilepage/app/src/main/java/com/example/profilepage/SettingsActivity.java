package com.example.profilepage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    private Switch notificationSwitch, nightModeSwitch;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "SettingsPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings2);

        // Enable edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.linearLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Enable Back Button if applicable
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Initialize switches
        notificationSwitch = findViewById(R.id.switch1);
        nightModeSwitch = findViewById(R.id.switch2);

        // Load saved preferences
        notificationSwitch.setChecked(sharedPreferences.getBoolean("notifications_enabled", false));
        nightModeSwitch.setChecked(sharedPreferences.getBoolean("night_mode_enabled", false));

        // Handle switch changes
        notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("notifications_enabled", isChecked).apply();
        });

        nightModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("night_mode_enabled", isChecked).apply();
            // Implement dark mode logic if needed
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
