package com.mylife.tianqi.api;

import android.util.Log;
import com.mylife.tianqi.util.HttpRequestUtil;
import org.json.JSONObject;

/**
 * Created by zhangyong on 14-10-12.
 * 从360上抓取数据 首页导航上用的
 */
public class Navigator360 {

    //{"weather":[{"date":"2014-10-15","info":{"night":["0","晴","6","北风","3-4 级"],"day":["0","晴","23","北风","5-6 级"]}},{"date":"2014-10-16","info":{"night":["0","晴","8","无持续风向","微风"],"day":["0","晴","22","无持续风向","微风"],"dawn":["0","晴","6","北风","3-4 级"]}},{"date":"2014-10-17","info":{"night":["0","晴","9","无持续风向","微风"],"day":["0","晴","22","无持续风向","微风"],"dawn":["0","晴","8","无持续风向","微风"]}},{"date":"2014-10-18","info":{"night":["1","多云","12","无持续风向","微风"],"day":["0","晴","21","无持续风向","微风"],"dawn":["0","晴","9","无持续风向","微风"]}},{"date":"2014-10-19","info":{"night":["2","阴","12","无持续风向","微风"],"day":["1","多云","20","无持续风向","微风"],"dawn":["1","多云","12","无持续风向","微风"]}}],"time":1413382473,"area":[["北京","01"],["北京","0101"],["北京","101010100"]]}
    public static JSONObject getWeatherInfo(String city) {
        try {
            String url = "http://cdn.weather.hao.360.cn/sed_api_weather_info.php?code=101010100&v=2&param=weather&app=hao360&_jsonp=__jsonp10__&t=2355130";
            String rtn = HttpRequestUtil.doGet(url);
            int first = rtn.indexOf('(');
            int last = rtn.indexOf(')');
            String json = rtn.substring(first + 1, last);
            JSONObject object = new JSONObject(json);
            return object;
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
