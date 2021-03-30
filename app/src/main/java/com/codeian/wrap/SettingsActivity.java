package com.codeian.wrap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {
    EditText deviceIdEditText, intervalEditText, targetEditText;
    ImageView backIcon;
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
        backIcon = findViewById(R.id.backIcon);
        intervalEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (Integer.parseInt(charSequence.toString()) < 30){
                        intervalEditText.setError("Minimum 30");
                        saveBtn.setEnabled(false);
                    } else {
                        saveBtn.setEnabled(true);
                    }
                } catch (NumberFormatException exception){
                    Log.d("Number format error", exception.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        //intervalEditText.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "99")});

        sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);

        backIcon.setOnClickListener(view -> {
            if(TextUtils.isEmpty(sharedPreferences.getString(deviceId, "")) || TextUtils.isEmpty(sharedPreferences.getString(interval, "")) || TextUtils.isEmpty(sharedPreferences.getString(target, ""))){
                Toast.makeText(SettingsActivity.this, "Save the values first!", Toast.LENGTH_LONG).show();
            }
            else {
                startActivity(new Intent(SettingsActivity.this, StatusActivity.class));
            }
        });

        saveBtn.setOnClickListener(view -> {
            if(TextUtils.isEmpty(deviceIdEditText.getText().toString()) || TextUtils.isEmpty(intervalEditText.getText().toString()) || TextUtils.isEmpty(targetEditText.getText().toString())){
                Toast.makeText(SettingsActivity.this, "Fields can't be empty", Toast.LENGTH_LONG).show();
            } else {
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