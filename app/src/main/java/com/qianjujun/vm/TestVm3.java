package com.qianjujun.vm;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 17:19
 * @describe
 */
public class TestVm3 extends TestVm1{


    @Override
    public boolean isStickyItem(int dataPosition) {
        return dataPosition==0;
    }
}
