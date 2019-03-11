package com.chen.fy.videophone.utils;

import java.util.Formatter;
import java.util.Locale;

public class Util {

//    private StringBuilder mFormatBuilder;
//    private Formatter mFormatter;
//
//    public Util(){
//        //转化成字符串的事件
//        mFormatBuilder = new StringBuilder();
//        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
//    }

    /**
     * 将毫秒转化成: 1:20:30这里形式
     */
    public static String stringForTime(int timeMs){
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());
        int totalSeconds = timeMs/1000;
        int seconds = totalSeconds%60;
        int minutes = (totalSeconds/60)%60 ;
        int hours = totalSeconds/3600;
        mFormatBuilder.setLength(0);
        if(hours>0){
            return mFormatter.format("%d:%02d:%02d",hours,minutes,seconds).toString();
        }else{
            return mFormatter.format("%02d:%02d",minutes,seconds).toString();
        }
    }
}
