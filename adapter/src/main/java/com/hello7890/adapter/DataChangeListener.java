package com.hello7890.adapter;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/20 14:35
 * @describe
 */
public interface DataChangeListener {

    void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition);

    void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition, Object payload);



    void onDataSizeChangeByInserted(BaseViewModule viewModule, int positionStart, int itemCount);

    void onDataSizeChangeByRemove(BaseViewModule viewModule, int positionStart, int itemCount);

    void onDataSizeChange(BaseViewModule viewModule);



}
