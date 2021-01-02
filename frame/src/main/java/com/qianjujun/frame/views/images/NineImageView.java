package com.qianjujun.frame.views.images;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;


import com.hello7890.adapter.BaseViewHolder;
import com.hello7890.adapter.BaseViewModule;
import com.hello7890.adapter.DataChangeListener;

import java.util.List;

public class NineImageView extends ViewGroup implements DataChangeListener {

    private NineLayoutVm<?> nineLayoutVm;

    public NineImageView(Context context) {
        super(context);
    }

    public NineImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NineImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(Context context){
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void measureOne(){

    }





    public void setNineLayoutVm(NineLayoutVm<?> nineLayoutVm) {
        this.nineLayoutVm = nineLayoutVm;
        BaseViewHolder viewHolder = null;
        recyclerHolder();
        for(int index = 0;index<nineLayoutVm.size();index++){
            int type = nineLayoutVm.getItemViewType(index);
            viewHolder = getRecyclerHolder(type);
            if(viewHolder==null){
                viewHolder = nineLayoutVm.onCreateViewHolder(this,type);
            }
            if(viewHolder==null){
                throw new RuntimeException("null viewHolder");
            }
            bindViewHolder(nineLayoutVm,viewHolder,index);
        }
        requestLayout();


    }

    private void recyclerHolder(){

    }

    private BaseViewHolder getRecyclerHolder(int type){
        return null;
    }


    private SparseArray<List<BaseViewHolder>> holder = new SparseArray<>();




    private BaseViewHolder getViewHolder(View child){
        return null;
    }


    private void bindViewHolder(NineLayoutVm<?> nineLayoutVm,BaseViewHolder viewHolder,int index){

    }

    @Override
    public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition) {

    }

    @Override
    public void onDataItemRangeChanged(BaseViewModule viewModule, int dataPosition, int itemCount, int layoutPosition, Object payload) {

    }

    @Override
    public void onDataSizeChangeByInserted(BaseViewModule viewModule, int positionStart, int itemCount) {

    }

    @Override
    public void onDataSizeChangeByRemove(BaseViewModule viewModule, int positionStart, int itemCount) {

    }

    @Override
    public void onDataSizeChange(BaseViewModule viewModule) {

    }
}
