package com.qianjujun;

import android.graphics.Color;

public class ColorUtils {
    private static int[] colors = new int[]{Color.RED,Color.BLUE,Color.YELLOW,Color.GREEN,Color.GRAY};

    public static int getColor(int dataPosition){
        return colors[dataPosition%5];
    }
}
