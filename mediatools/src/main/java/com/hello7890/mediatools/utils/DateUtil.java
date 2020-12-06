package com.hello7890.mediatools.utils;

import java.text.SimpleDateFormat;

public class DateUtil {
    private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

    public static final String getDateForUs(){
        return yyyyMMddHHmmss.format(System.currentTimeMillis());
    }


    public static final long getUs(){
        long cutime = System.currentTimeMillis() * 1000; // 微秒
        long nanoTime = System.nanoTime(); // 纳秒
        return cutime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;
    }


}
