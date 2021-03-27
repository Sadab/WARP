package com.codeian.wrap;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import io.github.krtkush.lineartimer.LinearTimer;
import io.github.krtkush.lineartimer.LinearTimerView;

public class StatusActivity extends AppCompatActivity {
    LinearTimerView linearTimerView;
    ImageView startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        linearTimerView = (LinearTimerView) findViewById(R.id.timer);
        startBtn = findViewById(R.id.startBtn);
        linearTimerView.setProgressColor(Color.parseColor("#E23369"));

        LinearTimer linearTimer = new LinearTimer.Builder()
                .linearTimerView(linearTimerView)
                .duration(10 * 1000)
                .build();

        // Start the timer.
        findViewById(R.id.startBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearTimer.startTimer();
            }
        });

        // Reset the timer
        findViewById(R.id.timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    linearTimer.resetTimer();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    Toast.makeText(StatusActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






