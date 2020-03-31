package com.example.handlerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
    private TextView title,timer,txt;
    private ImageView timebtn;
    private boolean flag = false;
    private int i = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int min = msg.arg1/60;
            int sec = msg.arg1 % 60;
            //三元运算符计算
            String time = (min < 10 ? "0" + min : min + "")+":"+(sec < 10 ? "0" + sec : ""+sec);
            timer.setText(time);

            //重置属性
            if(flag == false){
                title.setText("计时器");
                timebtn.setImageResource(R.mipmap.began01_white);
                txt.setText("用时:"+time);
                timer.setText("00:00");
                i = 0;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        title = findViewById(R.id.title);
        timer = findViewById(R.id.timer);
        txt = findViewById(R.id.txt);
        timebtn = findViewById(R.id.timer_btn);

        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag == false){
                    flag = true;
                    title.setText("计时中···");
                    timebtn.setImageResource(R.mipmap.stop02_white);
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            while (flag){
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                i++;
                                Message msg = new Message();
                                msg.arg1 = i;
                                handler.sendMessage(msg);
                            }
                        }
                    }.start();

                }else{
                    flag = false;
                }


            }
        });
    }
}
