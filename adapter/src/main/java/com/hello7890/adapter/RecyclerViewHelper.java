package com.hello7890.adapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class RecyclerViewHelper {
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
