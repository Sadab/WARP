package com.codeian.wrap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;

public class StatusActivity extends AppCompatActivity implements LinearTimer.TimerListener {
    LinearTimerView linearTimerView;
    LinearTimer linearTimer;
    Button startBtn;
    TextView timeRemaining;
    ImageView settingIcon;

    SharedPreferences sharedPreferences;
    public static final String deviceId =  "deviceId";
    public static final String interval =  "interval";
    public static final String target =  "target";

    String id,delay,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        sharedPreferences = getSharedPreferences("application", Context.MODE_PRIVATE);

        id = sharedPreferences.getString(deviceId, "");
        delay = sharedPreferences.getString(interval, "30");
        amount = sharedPreferences.getString(target, "");

        long duration = Integer.parseInt(delay) * 1000;
        linearTimerView = (LinearTimerView) findViewById(R.id.timer);
        startBtn = findViewById(R.id.startBtn);
        timeRemaining = findViewById(R.id.timerTxt);
        settingIcon = findViewById(R.id.settingIcon);

        linearTimer = new LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(duration)
                .timerListener(this)
                .getCountUpdate(LinearTimer.COUNT_DOWN_TIMER, 1000)
                .build();

        // Start the timer.
        findViewById(R.id.startBtn).setOnClickListener(view -> {
            try {
                new Network().postRequest(id);
                linearTimer.startTimer();
            } catch (IllegalStateException | IOException e) {
                Toast.makeText(StatusActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        settingIcon.setOnClickListener(view -> {
            Intent intent = new Intent(StatusActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void animationComplete() {
        Log.i("Animation", "animationComplete");
        linearTimer.resetTimer();
    }

    @Override
    public void timerTick(long tickUpdateInMillis) {
        //Log.i("Time left", String.valueOf(tickUpdateInMillis));
        String formattedTime = String.format("%02d",
                TimeUnit.MILLISECONDS.toSeconds(tickUpdateInMillis)
                        - TimeUnit.MINUTES
                        .toSeconds(TimeUnit.MILLISECONDS.toHours(tickUpdateInMillis)));
        timeRemaining.setText(formattedTime + "s");
        startBtn.setEnabled(false);
    }

    @Override
    public void onTimerReset() {
        timeRemaining.setText(delay+"s");
        startBtn.setEnabled(true);
    }
}






