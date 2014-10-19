package com.mylife.tianqi;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.mylife.tianqi.api.Navigator360;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new myThread().start();
        setContentView(R.layout.activity_main);
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    class myThread extends Thread {
        public void run() {
            Navigator360.getWeatherInfo("");
            Message msg = new Message();
            msg.what = 2;
            handler.sendMessage(msg);
        }
    }

}
