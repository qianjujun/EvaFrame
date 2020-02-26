package com.qianjujun.frame.adapter;

import androidx.annotation.IntRange;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/22 13:48
 * @describe
 */
public abstract class GridViewModule<T> extends ViewModule<T> {
    @Override
    public int getSize() {
        if(size()==0){
            return 0;
        }
        return size() + 1;
    }

    private int _getSpanCount(int position) {
        int dataPosition = position - getStartPosition();
        if (dataPosition == getSize() - 1) {//最后一列占满全列
            return 1;
        }
        if (isStickyItem(dataPosition)) {//吸顶item占满全列
            return 1;
        }
        return getSpanCount(dataPosition);
    }

    protected abstract @IntRange(from = 1)
    int getSpanCount(int dataPosition);

    @Override
    protected final boolean isGridLayout() {
        return true;
    }

    @Override
    int getSpanSize(int position) {
        int maxSpanSize = getMaxSpanSize();
        if (maxSpanSize <= 1) {
            return 1;
        }
        int spanCount = _getSpanCount(position);
        if ((maxSpanSize % spanCount) != 0) {
            throw new RuntimeException("最大列数必须是所取列数的整数倍");
        }
        return maxSpanSize / spanCount;
    }

}
