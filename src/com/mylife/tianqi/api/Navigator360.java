package com.mylife.tianqi.api;

import android.util.Log;
import com.mylife.tianqi.HttpRequestUtil;
import org.json.JSONObject;

/**
 * Created by zhangyong on 14-10-12.
 * 从360上抓取数据 首页导航上用的
 */
public class Navigator360 {

    public static JSONObject getWeatherInfo(String city) {
        try {
            String url = "http://cdn.weather.hao.360.cn/sed_api_weather_info.php?code=101010100&v=2&param=weather&app=hao360&_jsonp=__jsonp10__&t=2355130";
            String rtn = HttpRequestUtil.doGet(url);
            int first = rtn.indexOf('(');
            int last = rtn.indexOf(')');
            String json = rtn.substring(first + 1, last);
            System.out.println(json);
            Log.i("json:" ,json);
            JSONObject object = new JSONObject(json);
            System.out.println(object.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //http://cdn.weather.hao.360.cn/sed_api_weather_info.php?code=101010100&v=2&param=weather&app=hao360&_jsonp=__jsonp10__&t=2355130
    public static void main(String[] args) {
        System.out.println(123);

    }

}
