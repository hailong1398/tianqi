package com.mylife.tianqi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.mylife.tianqi.api.Navigator360;
import com.mylife.tianqi.util.DateFormatUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private ImageView weather_img;

    private TextView temperature;

    private TextView discription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        new myThread().start();
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private void initView() {
        weather_img = (ImageView) findViewById(R.id.weather_img);
        temperature = (TextView) findViewById(R.id.temperature);
        discription = (TextView) findViewById(R.id.discription);
    }

    class myThread extends Thread {
        public void run() {
            //°C
            String danwei = "°C";
            String split = "/";
            JSONObject jsonObject = Navigator360.getWeatherInfo("");
            Log.i("天气信息：", jsonObject.toString());
            if (jsonObject != null) {
                try {
                    JSONArray weather = jsonObject.getJSONArray("weather");
                    if (weather != null) {
                        JSONObject today = weather.getJSONObject(0);
                        //日期
                        String date = today.getString("date");
                        JSONObject info = today.getJSONObject("info");
                        //白天
                        //["0","晴","9","无持续风向","微风"]
                        JSONArray day = info.getJSONArray("day");
                        Log.i("day:", day.toString());
                        //晚上
                        JSONArray night = info.getJSONArray("night");

                        //设置今天的天气
                        temperature.setText(day.getString(2) + danwei + split + night.getString(2) + danwei);

                        //设置今天的描述 类似：10/20 星期一 小雨 微风
                        discription.setText(date + " " + DateFormatUtil.getChineseDayOfWeek(date) + " " + day.getString(4) + "转" + night.getString(4));

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }

}
