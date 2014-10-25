package com.mylife.tianqi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zhangyong on 14/10/25.
 */
public class DateFormatUtil {

    public static String getChineseDayOfWeek(String date) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        if(Calendar.MONDAY == dayofweek){
            return "星期一";
        }
        if(Calendar.TUESDAY == dayofweek){
            return "星期二";
        }
        if(Calendar.THURSDAY == dayofweek){
            return "星期三";
        }
        if(Calendar.WEDNESDAY == dayofweek){
            return "星期四";
        }
        if(Calendar.FRIDAY == dayofweek){
            return "星期五";
        }
        if(Calendar.SATURDAY == dayofweek){
            return "星期六";
        }
        if(Calendar.SUNDAY == dayofweek){
            return "星期日";
        }
        return null;
    }

}
