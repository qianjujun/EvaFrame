package com.qianjujun;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/3/3 14:00
 * @describe
 */
public class TestData {
    private static final Random random = new Random();
    private static int index = 0;

    public static List<String> createTestStringList(){
        int max = random.nextInt(25)+1;
        return createTestStringList(max);
    }

    public static List<String> createTestStringList(int max){
        List<String> list = new ArrayList<>();
        for(int i = 0;i<max;i++){
            list.add("数据："+(index++));
        }
        return list;
    }
}
