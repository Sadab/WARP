package com.codeian.wrap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        long duration = 10 * 1000;
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
        findViewById(R.id.startBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    linearTimer.startTimer();
                } catch (IllegalStateException e) {
                    Toast.makeText(StatusActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });

        settingIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatusActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void animationComplete() {
        Log.i("Animation", "animationComplete");
        linearTimer.resetTimer();
    }

    @Override
    public void timerTick(long tickUpdateInMillis) {
        Log.i("Time left", String.valueOf(tickUpdateInMillis));
        String formattedTime = String.format("%02d",
                TimeUnit.MILLISECONDS.toSeconds(tickUpdateInMillis)
                        - TimeUnit.MINUTES
                        .toSeconds(TimeUnit.MILLISECONDS.toHours(tickUpdateInMillis)));
        timeRemaining.setText(formattedTime + "s");
        startBtn.setEnabled(false);
    }

    @Override
    public void onTimerReset() {
        timeRemaining.setText("");
        startBtn.setEnabled(true);
    }

    private String generateUniqueChar(int len){
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        random = new Random();
        String re = "";

        while (re.length() < len) {
            re += chars.charAt(random.nextInt(chars.length() - 1));
        }
        return re;
    }

    private String generateDigit(int len){
        random = new Random();
        String re = "";

        while (re.length() < len){
            re += random.nextInt(10);
        }
        return re;

    }


}






