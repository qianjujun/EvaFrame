package com.qianjujun.frame.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.qianjujun.frame.R;
import com.qianjujun.frame.utils.FrameConstant;


/**
 * @author qianjujun
 * @email qianjujun@163.com
 * @createTime 2020/1/16 17:23
 * @describe
 */


public class BetterBaseActivity extends AppCompatActivity implements FrameConstant {
    protected Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(savedInstanceState);

    }
    protected void onCreateView(@Nullable Bundle savedInstanceState){
        setContentView(R.layout.better_base_activity);
        if(savedInstanceState==null){
            Fragment fragment = onCreateFragment();
            if(fragment==null){
                return;
            }
            replaceFragment(fragment);
        }
    }

    private void replaceFragment(Fragment fragment){
        replaceFragment(fragment,R.id.activity_container);
    }

    protected void replaceFragment(Fragment fragment ,int containerId){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId,fragment);
        transaction.commitAllowingStateLoss();
    }

    protected Fragment onCreateFragment(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fragmentName = bundle.getString(KEY_FRAGMENT_NAME);
        Fragment fragment;
        if(TextUtils.isEmpty(fragmentName)){
            fragment = new BetterErrorFragment();
        }else {
            fragment = (Fragment) ARouter.getInstance().build(fragmentName).navigation();
        }
        if(fragment==null){
            fragment = new BetterErrorFragment();
        }
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_container);
        if(fragment!=null){
            mHandler.post(() -> fragment.onActivityResult(requestCode,resultCode,data));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
