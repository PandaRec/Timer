package com.example.timer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;

import java.time.chrono.HijrahEra;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean isRunning = false;
    private boolean wasRunning = false;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewTimer);
        if(savedInstanceState!=null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds",seconds);
        outState.putBoolean("isRunning",isRunning);
        outState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning=isRunning;
        isRunning=false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        isRunning=wasRunning;
    }

    public void onClickStart(View view) {
        isRunning=true;
    }

    public void onClickPause(View view) {
        isRunning=false;
    }

    public void onClickReset(View view) {
        seconds=0;
        isRunning=false;
    }
    private void runTimer(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int hours =seconds/3600;
                int minutes = (seconds%3600)/60;
                int sec = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,sec);
                textView.setText(time);
                if(isRunning)
                    seconds++;
                handler.postDelayed(this,1000);

            }
        },1000);
    }
}