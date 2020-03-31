package com.example.internetdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonActivity extends AppCompatActivity {
    private TextView txt1;
    private ListView txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJson();
            }
        });
    }

    public void parseJson(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                //获取要解析json字符串
                String str = get();
                Log.e("TAG", "=========" +str);
                //实例化JSONObject对象
                try {
                    JSONObject jsonObject = new JSONObject(str);
                    //获取json整数值
                    final int status = jsonObject.getInt("status");
                    //获取json字符串
                    final String msg = jsonObject.getString("msg");
//                    //获取json对象
//                    JSONObject date = jsonObject.getJSONObject("data");
//                    //获取json对象内的属性
//                    final String title = date.getString("title");

                    //实列化list数据源
                    List<Map<String,Object>> date = new ArrayList<>();

                    //获取json数组
                    JSONArray array = jsonObject.getJSONArray("data");
                    //遍历数组，将数组内的对象/数据取出
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject obj = array.getJSONObject(i);
                        String name = obj.getString("name");
                        int id  = obj.getInt("id");
                        Log.e("TAG", "name" +name+"id"+id);
                        //添加数据到数据源
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name);
                        map.put("id",id);
                        date.add(map);
                    }

                    String[] from = {"name","id"};
                    int[] to = {R.id.name,R.id.id};

                    final SimpleAdapter adapter = new SimpleAdapter(JsonActivity.this,date,R.layout.arrayparse,from,to);

                    //显示到界面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt2.setAdapter(adapter);
//                            txt1.setText("status:"+status+"msg:"+msg+"title:"+title);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String get() {
        //1.实例化URL对象
        try {
            URL url = new URL("https://www.imooc.com/api/teacher?type=2&cid=1");

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
