package com.example.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyClass {
    public static void main(String[] args){
//        List<String> list = new ArrayList<>();
//        String str;
//        String str1 = "sdfsdf";
//        for(int index = 0;index<10;index++){
//            str = "str"+index;
//            list.add(str);
//            list.add(str);
//            list.add(str);
//        }
//        list.add(str1);
//        list.add(str1);
//
//        System.out.println(list);
//
//        list.remove(str1);
//        System.out.println(list);

        int[] result = count(40,2,1);

        System.out.println(Arrays.toString(result));
    }


    private static int[] count(int dividerNum,int column,int index){
        int totalWidth = dividerNum*(column-1);
        int itemWidth = totalWidth/column;
        int itemNum = column-1;
        int left = itemWidth*index/itemNum;
        int right = itemWidth*(itemNum-index)/itemNum;
        return new int[]{left,right};
    }
}
