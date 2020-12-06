package com.hello7890.album.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;

import com.qianjujun.frame.base.BetterBaseActivity;
import com.qianjujun.frame.base.BetterErrorFragment;

public class AlbumActivity extends BetterBaseActivity {
    @Override
    protected Fragment onCreateFragment() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String fragmentName = bundle.getString(KEY_FRAGMENT_NAME);
        Fragment fragment;
        if(TextUtils.isEmpty(fragmentName)){
            fragment = new BetterErrorFragment();
        }else {
            fragment = createFragmentByName(fragmentName);
        }
        if(fragment==null){
            fragment = new BetterErrorFragment();
        }
        fragment.setArguments(bundle);
        return fragment;

    }

    protected Fragment createFragmentByName(String name){
        try {
            return getSupportFragmentManager().getFragmentFactory().instantiate(getClassLoader(),name);
        }catch (Exception e){
            return null;
        }
    }

}
