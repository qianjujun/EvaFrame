package com.qianjujun.frame.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.qianjujun.frame.R;
import com.qianjujun.frame.views.stateview.IErrorViewConfig;
import com.qianjujun.frame.views.stateview.StateView;


/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/16 17:24
 * @describe
 */
abstract class BetterLoadFragment extends BetterBaseFragment {
    private StateView mStateView;

    @Override
    protected void loadContentView(ViewStub viewStub) {
        viewStub.setLayoutResource(R.layout.better_layout_fragment_base);
        mStateView = (StateView) viewStub.inflate();
        mStateView.setSetErrorView(configErrorView());
        View contentView = getLayoutView(mStateView);
        if(contentView==null){
            return;
        }
        ViewGroup parent = (ViewGroup) contentView.getParent();
        if(parent!=null){//view已被加入
            return;
        }
        mStateView.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView(contentView);

    }


    protected IErrorViewConfig configErrorView(){
        return null;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View contentView);

    protected View getLayoutView(ViewGroup container){
        return LayoutInflater.from(mActivity).inflate(getLayoutId(),container,false);
    }

    @Override
    public void showLoadingView() {
        mStateView.showLoadingView();
    }

    @Override
    public void showErrorView(int errorCode, String msg) {
        mStateView.showErrorView(errorCode,msg);
    }

    @Override
    public void showEmptyView() {
        mStateView.showEmptyView();
    }

    @Override
    public void showSuccessView() {
        mStateView.showSuccessView();
    }
}
