package com.qianjujun.frame.base;


import android.view.View;
import android.view.ViewStub;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qianjujun.frame.R;

/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/16 18:13
 * @describe
 */
public abstract class BetterModuleFragment extends BetterBaseFragment {
    protected RecyclerView mRecyclerView;
    @Override
    protected void loadContentView(ViewStub viewStub) {
        viewStub.setLayoutResource(getLayoutId());
        View view = viewStub.inflate();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(createLayoutManger());
        initModule(mRecyclerView,view);
    }

    protected @LayoutRes int getLayoutId(){
        return R.layout.better_fragment_module;
    }

    protected abstract void initModule(RecyclerView recyclerView,View contentView);


    protected RecyclerView.LayoutManager createLayoutManger(){
        return new LinearLayoutManager(mActivity);
    }


}
