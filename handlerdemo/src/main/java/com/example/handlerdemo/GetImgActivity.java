package com.example.handlerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetImgActivity extends AppCompatActivity {
    private ImageView image;
    private Button getimg;
    private Drawable drawable;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            image.setImageDrawable(drawable);
            Log.e("TAG",msg.what+"");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_img);
        image = findViewById(R.id.img);
        getimg = findViewById(R.id.getimg);
        getimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        drawable = get();
                        Log.e("TAG","aaa");
                        handler.sendEmptyMessage(1);
                    }
                }.start();
            }
        });
    }

    public Drawable get(){
        try {
            URL url = new URL("https://img2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6000);

            InputStream is = conn.getInputStream();
            //获取数据流，并转为Drawble资源
            Drawable d= Drawable.createFromStream(is,"aa");
            return d;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
