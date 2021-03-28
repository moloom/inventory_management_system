package com.mo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取当前时间，转换成一串代码
 */
public class MyToString {
    private static String myToString;
    private static String str1;

    {
        Date currentTime = new Date(); // 获取当前时间
        String str1 = Long.toString(currentTime.getTime()); // 获取从1970至今的秒数，并转换为字符串
        SimpleDateFormat sft = new SimpleDateFormat("yyyyMM"); // 要转换的字符串格式---yyyyMMdd
        this.myToString = sft.format(currentTime); // 把现在的时间转换为字符串
        this.str1 = str1;
        this.str1 = str1.substring(5, 11);
    }

    public String getMyToString() {
        return myToString + str1;
    }

    public Date getDate() {
        return null;
    }

}
