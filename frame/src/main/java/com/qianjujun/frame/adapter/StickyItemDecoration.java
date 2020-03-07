package com.qianjujun.frame.adapter;

import android.graphics.Canvas;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/21 14:41
 * @describe
 */
public class StickyItemDecoration extends RecyclerView.ItemDecoration {
    public static final String TAG = "StickyItemDecoration";

    private IAdapterHelp adapterHelper;
    private ViewGroup viewRoot;
    FrameLayout container;
    FrameLayout root;
    int[] location = new int[2];
    int[] rootLocation = new int[2];

    Helper helper = new Helper();

    private int currentStickyPosition = -1;
    /**
     * 根据itemType类型缓存viewHolder
     */
    private Map<Integer,BaseViewHolder> stickyHolder = new HashMap<>();

    private Handler mHandler = new Handler();



    private BaseViewHolder getViewHolderByItemViewType(ViewGroup parent,int position){
        int viewType = adapterHelper.getAdapterViewType(position);
        BaseViewHolder viewHolder = null;
        if(stickyHolder.containsKey(viewType)){
            viewHolder =  stickyHolder.get(viewType);
        }
        if(viewHolder==null){
            viewHolder = adapterHelper.onCreateStickyViewHolder(parent,viewType);
            stickyHolder.put(viewType,viewHolder);
        }
        return viewHolder;
    }




    public StickyItemDecoration(IAdapterHelp adapterHelper) {
        this(adapterHelper,null);

    }

    public StickyItemDecoration(IAdapterHelp adapterHelper, ViewGroup viewRoot) {
        this.adapterHelper = adapterHelper;
        this.viewRoot = viewRoot;
        this.adapterHelper.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                if(currentStickyPosition==positionStart){
                    mHandler.postDelayed(() -> handlerView(currentStickyPosition),64);

                }
            }

            @Override
            public void onItemRangeChanged(int positionStart, int itemCount, @Nullable Object payload) {
                super.onItemRangeChanged(positionStart, itemCount, payload);
                if(currentStickyPosition==positionStart){
                    mHandler.postDelayed(() -> handlerView(currentStickyPosition,payload),64);
                }
            }
        });
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (root == null) {
            root = new FrameLayout(parent.getContext());
            root.addView(container = new FrameLayout(parent.getContext()));
            parent.getLocationInWindow(location);
            if(viewRoot==null){
                viewRoot = (ViewGroup) parent.getRootView();
            }
            viewRoot.getLocationInWindow(rootLocation);
            int distanceY = location[1] - rootLocation[1];
            root.setTranslationY(distanceY);
            viewRoot.addView(root, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        View child = parent.getChildAt(0);
        int pos = parent.getChildAdapterPosition(child);
        int stickyPosition = adapterHelper.findCurrentStickyPosition(pos);
        Log.d(TAG, "onDrawOver() called with: c = [stickyPosition<0"+stickyPosition +"  pos:"+pos);
        if(stickyPosition<0){
            container.setVisibility(View.GONE);
            return;
        }

        container.setVisibility(View.VISIBLE);
        if(currentStickyPosition!=stickyPosition){
            currentStickyPosition = stickyPosition;
            handlerView(currentStickyPosition);
        }


        //处理滑动
        View stickyView = getStickyViewInRecyclerView(parent);
        if(stickyView==null){
            container.setTranslationY(0);
            //Log.d(TAG, "onDrawOver() called with: c = [" + "" + "], parent = [" + "" + "], state = [" + state + "]");
            return;
        }
        int top = stickyView.getTop();
        int height = container.getMeasuredHeight();

        if (top <= 0) {
            container.setTranslationY(0);
        } else if (top > 0 && top <= height) {
            container.setTranslationY(top - height);
        } else {
            container.setTranslationY(0);
        }



    }

    /**
     * 获取RecyclerView当前显示的View中 需要吸顶的view
     * @param parent
     * @return
     */
    private View getStickyViewInRecyclerView(RecyclerView parent) {

        int firstCompletePosition = helper.findFirstCompletePosition(parent);
        View child;
        for (int i = 0; i < parent.getChildCount(); i++) {
            child = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(child);
            if (adapterHelper.isStickyItem(pos)&&pos>=firstCompletePosition) {
                return child;
            }
        }

        return null;
    }





    private void handlerView(int stickyPosition) {
        //View view = container.findViewWithTag(stickyPosition + "");

        Log.d(TAG, "handlerView() called with: stickyPosition = [" + stickyPosition + "]");

        BaseViewHolder holder = getViewHolderByItemViewType(container,stickyPosition);
        View itemView = holder.itemView;
        if(itemView.getParent()==null){
            container.addView(itemView);
        }
        adapterHelper.onBindStickyViewHolder(holder,stickyPosition);
        View child;
        for (int i = 0; i < container.getChildCount(); i++) {
            child = container.getChildAt(i);
            if (child == itemView) {
                child.setVisibility(View.VISIBLE);
            } else {
                child.setVisibility(View.GONE);
            }
        }
    }

    private void handlerView(int stickyPosition,Object payload){
        int viewType = adapterHelper.getAdapterViewType(stickyPosition);
        BaseViewHolder viewHolder = null;
        if(stickyHolder.containsKey(viewType)){
            viewHolder =  stickyHolder.get(viewType);
        }
        if(viewHolder==null){
            return;
        }
        List<Object> list = new ArrayList<>();
        list.add(payload);
        adapterHelper.onBindStickyViewHolder(viewHolder,stickyPosition,list);

    }


    private static class Helper{
        private LinearLayoutManager linearLayoutManager;
        private StaggeredGridLayoutManager staggeredGridLayoutManager;
        private RecyclerView.LayoutManager layoutManager;

        public int findFirstCompletePosition(RecyclerView parent){
            RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
            if(this.layoutManager==layoutManager){
                return findFirstCompletePosition(layoutManager);
            }

            this.layoutManager = layoutManager;
            if(layoutManager instanceof LinearLayoutManager){
                linearLayoutManager = (LinearLayoutManager) layoutManager;
            }

            if(layoutManager instanceof StaggeredGridLayoutManager){
                staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            }
            return findFirstCompletePosition(layoutManager);
        }

        private int findFirstCompletePosition(RecyclerView.LayoutManager layoutManager){
            if(linearLayoutManager==layoutManager){
                return linearLayoutManager.findFirstCompletelyVisibleItemPosition();
            }
            if(staggeredGridLayoutManager==layoutManager){
                int[] positions = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
                if(positions.length>0){
                    return positions[0];
                }
            }
            return 0;
        }


    }

}
