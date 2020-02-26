package com.qianjujun.frame.views.stateview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 介绍：适应于各种状态下展示不同页面的View
 * 尤其是网络请求的时候  对应的状态：1，请求中 loading  2，请求成功 ，success   3，请求失败  各种错误状态对应的错误界面   4，空数据界面
 *
 * 作者：qianjujun
 * 邮箱：qianjujun@163.com
 * 时间: 2017-03-06  14:11
 */

public class StateView extends FrameLayout implements IPage {
    private IErrorViewConfig mSetErrorView;


    private View mLoadingView;
    private ILoadAnimation mILoadAnimation;



    private View emptyView;
    private View errorView;






    public StateView(@NonNull Context context) {
        super(context);
        init();
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        mSetErrorView = new IErrorViewConfig() {
            @Override
            public View getErrorView(int state, String message) {
                return new SimpleErrorView(getContext());
            }

            @Override
            public <T extends View & ILoadAnimation> T getLoadingView() {
                return (T) new SimpleLoadingView(getContext());
            }

            @Override
            public View getEmptyView() {
                return new SimpleEmptyView(getContext());
            }
        };
    }


    public void setSetErrorView(final IErrorViewConfig setErrorView){
        if(setErrorView==null){
            return;
        }
        mSetErrorView = new IErrorViewConfig() {
            @Override
            public View getErrorView(int state, String message) {
                View view = setErrorView.getErrorView(state,message);

                return view==null?new SimpleErrorView(getContext()):view;
            }

            @Override
            public <T extends View & ILoadAnimation> T getLoadingView() {
                T t = setErrorView.getLoadingView();
                return t==null? (T) new SimpleLoadingView(getContext()) :t;
            }

            @Override
            public View getEmptyView() {
                View view =  setErrorView.getEmptyView();
                return view==null?new SimpleEmptyView(getContext()):view;
            }
        };


    }


    @Override
    public void showLoadingView() {
        if(mLoadingView==null){
            mLoadingView = mSetErrorView.getLoadingView();
            mILoadAnimation = (ILoadAnimation) mLoadingView;
            addView(mLoadingView);
        }



        setVisibility(mLoadingView,true);
        setVisibility(emptyView,false);
        setVisibility(errorView,false);



        if(mILoadAnimation!=null){
            mILoadAnimation.start();
        }


        if(errorView!=null){//避免不同code对应的错误界面 由于缓存展示的是第一个错误界面
            removeView(errorView);
            errorView = null;
        }



    }

    @Override
    public void showErrorView(int errorCode, String msg) {

        if(mILoadAnimation!=null){
            mILoadAnimation.stop();
        }

        if(errorView==null){
            errorView = mSetErrorView.getErrorView(errorCode,msg);
            addView(errorView);
        }

        setVisibility(mLoadingView,false);
        setVisibility(emptyView,false);
        setVisibility(errorView,true);




    }

    @Override
    public void showEmptyView() {
        if(mILoadAnimation!=null){
            mILoadAnimation.stop();
        }
        if(emptyView==null){
            emptyView = mSetErrorView.getEmptyView();
            addView(emptyView);
        }

        setVisibility(mLoadingView,false);
        setVisibility(emptyView,true);
        setVisibility(errorView,false);

    }

    @Override
    public void showSuccessView() {
        if(mILoadAnimation!=null){
            mILoadAnimation.stop();
        }
        setVisibility(mLoadingView,false);
        if(emptyView!=null){
            removeView(emptyView);
            emptyView = null;
        }
        if(errorView!=null){
            removeView(errorView);
            errorView = null;
        }



//        setVisibility(mLoadingView,false);
//        setVisibility(emptyView,false);
//        setVisibility(errorView,false);


    }


    private void setVisibility(View view , boolean visiable){
        if(view==null){
            return;
        }

        if(visiable){

            if(view.getVisibility()!=VISIBLE){
                view.setVisibility(VISIBLE);
            }

        }else{
            if(view.getVisibility()!=GONE){
                view.setVisibility(GONE);
            }
        }


    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }
}
