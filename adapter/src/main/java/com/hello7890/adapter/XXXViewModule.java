package com.hello7890.adapter;


/**
 * 莫要继承我  用于过渡包权限
 * @param <T>
 */
public abstract class XXXViewModule<T> extends BaseViewModule<T>{

    @Override
    protected BaseViewModule getWrapViewModule() {
        return super.getWrapViewModule();
    }

    @Override
    protected int getSpanCount(int dataPosition) {
        return super.getSpanCount(dataPosition);
    }
}
