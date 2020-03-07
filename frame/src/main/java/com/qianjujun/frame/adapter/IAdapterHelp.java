package com.qianjujun.frame.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/19 16:36
 * @describe
 */
public interface IAdapterHelp {
    /**
     * 查找ViewModule
     * @param viewType adapter的item类型
     * @return
     */
    BaseViewModule findViewModule(int viewType);

    /**
     * 查找ViewModule
     * @param position adapter的position
     * @return
     */
    BaseViewModule findViewModuleByPosition(int position);

    /**
     * 根据adapter的item类型获取module的类型
     * @param viewType
     * @return
     */
    int getModuleViewType(int viewType);


    int getAdapterViewType(int position);

    /**
     * 获取module的数据位置
     * @param viewModule
     * @param position
     * @return
     */
    int getDataPosition(BaseViewModule viewModule,int position);

    /**
     * 获取总数量
     * @return
     */
    int getSize();


    void setLayoutManager(RecyclerView.LayoutManager layoutManager);



    int findCurrentStickyPosition(int position);

    boolean isStickyItem(int position);

    BaseViewHolder onCreateStickyViewHolder(ViewGroup container,int viewType);

    void onBindStickyViewHolder(BaseViewHolder viewHolder,int position);

    void onBindStickyViewHolder(BaseViewHolder viewHolder, int position, List<Object> payloads);

    void changeLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager);

    void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer);

}
