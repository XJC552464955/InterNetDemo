package com.example.internetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private EditText acount;
    private EditText passw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        acount = findViewById(R.id.acount);
        passw = findViewById(R.id.passw);

    }

    public void myclick(View view){
        switch (view.getId()){
            case R.id.get:
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MainActivity.this,GSONActivity.class));
                            }
                        });
//                        get();
                    }
                }.start();
                break;
            case R.id.post:
                final String account = acount.getText().toString();
                final String password = passw.getText().toString();
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
//                        post(account,password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(MainActivity.this,JsonActivity.class));
                            }
                        });
                    }
                }.start();
                break;
        }
    }

    private void get() {
        //1.实例化URL对象
        try {
            URL url = new URL("https://fanyi.baidu.com/?aldtype=16047#en/zh/");

            //2.获取HttpURLConnection
            // URL中的openConnection()方法返回的参数是HttpURLConnection的子类
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //3.设置和请求相关的属性
            //请求方式
            conn.setRequestMethod("GET");
            //请求超时时长
            conn.setConnectTimeout(6000);

            //4.获取响应码 200(HTTP_OK)成功  404为请求到指定资源  500服务器异常
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                //5.判断响应码并获取响应的数据(响应的正文)
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream  baos = new ByteArrayOutputStream();
                //在循环中读取输入流
                byte[] b = new byte[1024];
                int n = 0;
                while((n=in.read(b))!=-1){
                    //将字节数组里的内容存入一个缓存流
                    //参数1：待写入的数组
                    //参数2：起点
                    //参数3：长度
                    baos.write(b,0, n);
                }
                String msg = new String(baos.toByteArray());
                Log.e("TAG",msg+"=====");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void post(String account,String password) {
        //1.实例化URL对象
        try {
            URL url = new URL("https://www.imooc.com/api/okhttp/postmethod");

            //2.获取HttpURLConnection
            // URL中的openConnection()方法返回的参数是HttpURLConnection的子类
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //3.设置和请求相关的属性
            //请求方式
            conn.setRequestMethod("GET");
            //请求超时时长
            conn.setConnectTimeout(6000);

            //设置允许输出，因为要将数据从客户端输出到服务器
            conn.setDoOutput(true);
            //设置请求数据的类型，约定俗成的类型，照抄就行
            conn.setRequestProperty("Content-type","application/x-www-form-urlencoded");

            //获得输出流
            OutputStream out = conn.getOutputStream();
            //写数据
            out.write(("account:"+account+"&password"+password).getBytes());

            //4.获取响应码 200(HTTP_OK)成功  404为请求到指定资源  500服务器异常
            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                //5.判断响应码并获取响应的数据(响应的正文)
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream  baos = new ByteArrayOutputStream();
                //在循环中读取输入流
                byte[] b = new byte[1024];
                int n = 0;
                while((n=in.read(b))!=-1){
                    //将字节数组里的内容存入一个缓存流
                    //参数1：待写入的数组
                    //参数2：起点
                    //参数3：长度
                    baos.write(b,0, n);
                }
                String msg = new String(baos.toByteArray());
                Log.e("TAG",msg+"=====");
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
