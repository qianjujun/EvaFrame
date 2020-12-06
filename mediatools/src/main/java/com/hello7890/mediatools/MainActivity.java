package com.hello7890.mediatools;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.view.View;

import com.hello7890.album.MediaOptions;
import com.hello7890.album.MediaTool;
import com.hello7890.album.bean.Media;
import com.hello7890.mediatools.router.AppRouter;
import com.qianjujun.frame.permission.IPermission;
import com.qianjujun.frame.permission.PermissionBean;
import com.qianjujun.frame.permission.PermissionCallBack;
import com.qianjujun.frame.permission.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void compressVideo(View view) {
        PermissionUtils.requestPermission(this, PermissionBean.create("").addItem(IPermission.WRITE_EXTERNAL_STORAGE),
                new PermissionCallBack() {
                    @Override
                    public void onPermissionGranted() {
                        AppRouter.toCompressVideo(MainActivity.this,new Media());
                       // MediaTool.toSelectAlbum(MainActivity.this, MediaOptions.build(101).setMax(1).setMediaType(MediaOptions.TYPE_VIDEO));
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK||data==null){
            return;
        }

        Media media = data.getParcelableExtra("data");

        switch (requestCode){
            case 101:
                AppRouter.toCompressVideo(this,media);
                break;
        }
    }
}
