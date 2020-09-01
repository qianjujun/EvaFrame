package com.qianjujun.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.qianjujun.R;
import com.qianjujun.frame.base.BetterWebFragment;
import com.qianjujun.frame.views.title.TitleConfig;

import static com.qianjujun.router.RouterPath.PATH_WEB;

@Route(path = PATH_WEB)
public class TestWebFragment extends BetterWebFragment {
    @Override
    protected void initArgs(@NonNull Bundle args) {
        args.putString("url","http://192.168.0.107:8000/");
        super.initArgs(args);
    }

    @Override
    protected TitleConfig getTitleConfig() {
        return mTitleConfig = TitleConfig.build().leftImage(R.mipmap.icon_back_black).create().click(this);
    }

    @Override
    protected void onUpdateTitle(String title) {
        super.onUpdateTitle(title);
        mTitleConfig.updateCenterText(title);
    }

    @Override
    public void leftClick() {
        backPress();
    }

    @Override
    protected boolean onBackPressed() {
        if(mDataBinding.webview.canGoBack()){
            mDataBinding.webview.goBack();
            return true;
        }
        return super.onBackPressed();
    }
}
