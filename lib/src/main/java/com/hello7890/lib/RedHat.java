package com.hello7890.lib;

import java.text.SimpleDateFormat;

public class RedHat extends Cat{



    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");

        for(int i = 0;i<20;i++){
            long time = getmicTime();
            System.out.print(time);
            System.out.println("===="+sdf.format(time));
        }
    }

    public static Long getmicTime() {
        Long cutime = System.currentTimeMillis() * 1000; // 微秒
        Long nanoTime = System.nanoTime(); // 纳秒
        return cutime + (nanoTime - nanoTime / 1000000 * 1000000) / 1000;
    }
}
