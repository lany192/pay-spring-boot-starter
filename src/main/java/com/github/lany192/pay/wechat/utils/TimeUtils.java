package com.github.lany192.pay.wechat.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static String getTimeStart(Date date) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(date);
    }

    /**
     * minute分钟后失效
     */
    public static String getTimeExpire(Date startDate, int minute) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + minute);
        return format.format(calendar.getTime());
    }
}
