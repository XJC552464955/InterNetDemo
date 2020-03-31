package com.example.handlerdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //控件
    private TextView txt;

    //全局变量
    private String str;
    private Handler handler2;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    txt.setText(str);
                    break;
                case 1:
                    txt.setText("what:"+msg.what+" arg1:"+msg.arg1+" arg2:"+msg.arg2+" 随机数:"+((Random)msg.obj).nextInt(1000));
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = findViewById(R.id.txt1);
        new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();  //准备开始一个消息循环，系统会自动为主线程开启消息循环
                handler2 = new Handler(){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        super.handleMessage(msg);
                        Toast.makeText(MainActivity.this,"由主线程传递过来的Message，它的waht是"+msg.what,Toast.LENGTH_SHORT).show();
                    }
                };
                Looper.loop();  //消息循环
            }
        }.start();
    }

    public void myclick(View view) {
        switch (view.getId()){
            case R.id.btn:
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        str = get();
                        Log.e("TAG", str);
                        //sendEmptyMessage(what) what属性可以用来区别信息来自哪个线程
                        handler.sendEmptyMessage(0);
                }
                }.start();
                break;
            case R.id.btn1:
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        //Message类实例与使用
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = 666;
                        message.arg2 = 888;
                        message.obj = new Random();
                        handler.sendMessage(message);
                    }
                }.start();
                break;
            case R.id.btn2:
                handler2.sendEmptyMessage(3);
                break;
            case R.id.btn3:
                startActivity(new Intent(MainActivity.this,TimerActivity.class));
            case R.id.btn4:
                startActivity(new Intent(MainActivity.this,GetImgActivity.class));
        }
    }

    public String get(){
        try {
            URL url = new URL("https://www.imooc.com/api/teacher?type=2&cid=1");  //url实例

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");   //设置请求方式
            connection.setConnectTimeout(6000);   //请求超时事件

            if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){  //判断响应是否正确
                InputStream input = connection.getInputStream();   //从HttpURLConnection中获取输入流
                ByteArrayOutputStream output = new ByteArrayOutputStream();  //实例化一个缓存流
                int n = 0;
                byte[] b = new byte[1024];

                while ((n=input.read(b))!=-1){
                    output.write(b,0,n);
                }
                String msg = new String(output.toByteArray());
                Log.e("TAG", msg);
                return msg;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
