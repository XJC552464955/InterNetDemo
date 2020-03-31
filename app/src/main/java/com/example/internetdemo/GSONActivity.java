package com.example.internetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.transform.Templates;

public class GSONActivity extends AppCompatActivity {
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);

        txt = findViewById(R.id.txt_gson);
        findViewById(R.id.btn_gson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psrseByGson();
            }
        });
    }

    private void psrseByGson() {
        //实例化GSON对象（工具对象）
        final Gson gson = new Gson();

        //实例化Book类
        Book b = new Book("数据结构","aaa","塾读书数据结构飒飒打撒");
        //toJson将对象转为Json字符串
        String str = gson.toJson(b);
        Log.e("TAG", "gson" +str);

        new Thread(){
            @Override
            public void run() {
                super.run();
                String msg = get();
                //参数1：被解析的json数据，用来存储数据的实体类
                final JsonParseClass parse = gson.fromJson(msg,JsonParseClass.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt.setText("status:"+parse.getStatus()+"\nmsg:"+parse.getMsg()+"\ncontent:"+parse.getData().getContent());
                    }
                });
//                Log.e("TAG","status:"+parse.getStatus()+"\nmsg:"+parse.getMsg()+"\ncontent:"+parse.getData().getContent());
            }
        }.start();
    }

    private String get() {
        //1.实例化URL对象
        try {
            URL url = new URL("https://www.imooc.com/api/teacher?type=3&cid=1");

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
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
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
//                Log.e("TAG",msg+"=====");
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
