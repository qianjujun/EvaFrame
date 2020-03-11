package com.example.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyClass {


    public static final int LOCATION_LEFT_TOP = 1<<0;
    public static final int LOCATION_RIGHT_TOP = 1<<1;
    public static final int LOCATION_LEFT_BOTTOM = 1<<2;
    public static final int LOCATION_RIGHT_BOTTOM = 1<<3;
    public static void main(String[] args){

        System.out.println(LOCATION_LEFT_TOP);
        System.out.println(LOCATION_RIGHT_TOP);
        System.out.println(LOCATION_LEFT_BOTTOM);
        System.out.println(LOCATION_RIGHT_BOTTOM);

        int state = LOCATION_LEFT_TOP|LOCATION_RIGHT_TOP;

        System.out.println(state);

        System.out.println(state&LOCATION_RIGHT_TOP);




    }


    private static int[] count(int dividerNum,int column,int index){
        int totalWidth = dividerNum*(column-1);
        int itemWidth = totalWidth/column;
        int itemNum = column-1;
        int left = itemWidth*index/itemNum;
        int right = itemWidth*(itemNum-index)/itemNum;
        return new int[]{left,right};
    }



    private static int[] count1(int space,int spanCount,int column){
        int left = column*space/spanCount;
        int right = space-(column+1)*space/spanCount;
        return new int[]{left,right};
    }

    private static int[] count2(int space,int columnNum,int columnIndex,int leftAndRight){
        int totalWidth = space*(columnNum-1);
        int itemWidth = totalWidth/columnNum;
        int itemNum = columnNum-1;
        int left = itemWidth*columnIndex/itemNum;
        int right = itemWidth*(itemNum-columnIndex)/itemNum;

        int offsetWidth = leftAndRight*2/columnNum;
        int offsetLeft = leftAndRight-columnIndex*offsetWidth;
        int offsetRight = offsetWidth-offsetLeft;
        left += offsetLeft;
        right += offsetRight;

        return new int[]{left,right};
    }


    private static int[] count3(int space,int columnNum,int columnIndex,int leftAndRight){
        if(columnNum==1){
            if(columnIndex==0){
                return new int[]{leftAndRight,leftAndRight};
            }
            throw new RuntimeException("break");
        }

        int total = leftAndRight*2+space*(columnNum-1);
        int itemWidth = total/columnNum;
        int end = itemWidth - leftAndRight;
        int step = (leftAndRight-end)/(columnNum-1);
        int left = leftAndRight - columnIndex*step;
        int right = itemWidth-left;
        return new int[]{left,right};
    }
}
