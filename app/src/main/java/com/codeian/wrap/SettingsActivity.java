package com.codeian.wrap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    EditText deviceIdEditText, intervalEditText, targetEditText;
    Button saveBtn;
    SharedPreferences sharedPreferences;
    public static final String deviceId =  "deviceId";
    public static final String interval =  "interval";
    public static final String target =  "target";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        deviceIdEditText = findViewById(R.id.deviceIdText);
        intervalEditText = findViewById(R.id.intervalText);
        targetEditText = findViewById(R.id.targetText);
        saveBtn = findViewById(R.id.saveBtn);

        sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(deviceId, deviceIdEditText.getText().toString());
                editor.putString(interval, intervalEditText.getText().toString());
                editor.putString(target, targetEditText.getText().toString());
                editor.apply();
                Toast.makeText(SettingsActivity.this, "Saved!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(SettingsActivity.this, StatusActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        deviceIdEditText = findViewById(R.id.deviceIdText);
        intervalEditText = findViewById(R.id.intervalText);
        targetEditText = findViewById(R.id.targetText);

        sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);

        if (sharedPreferences.contains(deviceId)){
            deviceIdEditText.setText(sharedPreferences.getString(deviceId, ""));
        }
        if (sharedPreferences.contains(interval)){
            intervalEditText.setText(sharedPreferences.getString(interval, ""));
        }
        if (sharedPreferences.contains(target)){
            targetEditText.setText(sharedPreferences.getString(target, ""));
        }
    }

}