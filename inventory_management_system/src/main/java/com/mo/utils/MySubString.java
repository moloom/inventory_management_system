package com.mo.utils;

import java.util.ArrayList;
import java.util.List;

public class MySubString {
    /**
     * 处理字符串
     * 把类似这种的字符串 3,4,1  去除分隔号并转为list。
     *
     * @param str
     * @param subSymbol
     * @return
     */
    public static List<String> subString(String str, String subSymbol) {
        List<String> stringList = new ArrayList<>();
        int exit = 0, i = 0, start = 0, end = 0;
        while (true) {
            end = str.indexOf(subSymbol, start);
            if (end == -1) {
                exit = end;
                end = str.length();
            }
            stringList.add(str.substring(start, end));
            start = ++end;
            if (exit == -1)
                break;
        }
        return stringList;
    }

    private static String getString(int t) {
        String m = "";
        if (t > 0) {
            if (t < 10) {
                m = "0" + t;
            } else {
                m = t + "";
            }
        } else {
            m = "00";
        }
        return m;
    }

    /**
     * @param t 毫秒
     * @return
     * @author mo
     */
    public static String format(int t) {
        if (t < 60000) {
            return (t % 60000) / 1000 + "秒";
        } else if ((t >= 60000) && (t < 3600000)) {
            return getString((t % 3600000) / 60000) + "分" + getString((t % 60000) / 1000) + "秒";
        } else {
            return getString(t / 3600000) + ":" + getString((t % 3600000) / 60000) + ":" + getString((t % 60000) / 1000);
        }


    }
}
