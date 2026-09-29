package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryAlerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        Button btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);

        boolean expiryAlertsEnabled = getPreferences(MODE_PRIVATE)
                .getBoolean("expiry_alerts", true);

        switchExpiryAlerts.setChecked(expiryAlertsEnabled);

        switchExpiryAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {

            getPreferences(MODE_PRIVATE)
                    .edit()
                    .putBoolean("expiry_alerts", isChecked)
                    .apply();
        });
    }
}