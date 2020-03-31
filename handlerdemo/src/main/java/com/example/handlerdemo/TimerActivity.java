package com.example.handlerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    private TextView title,timer,txt;
    private ImageView timebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        title.findViewById(R.id.title);
        timer.findViewById(R.id.timer);
        txt.findViewById(R.id.txt);
        
        timebtn.findViewById(R.id.timer_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
