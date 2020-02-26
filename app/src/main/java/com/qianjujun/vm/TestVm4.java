package com.qianjujun.vm;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 18:17
 * @describe
 */
public class TestVm4 extends TestVm2{


    @Override
    public boolean isStickyItem(int dataPosition) {
        return dataPosition==0;
    }
}
