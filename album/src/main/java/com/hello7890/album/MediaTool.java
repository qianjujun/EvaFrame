package com.hello7890.album;

import android.app.Activity;
import android.content.Intent;

import com.hello7890.album.bean.Album;
import com.hello7890.album.ui.AlbumActivity;
import com.hello7890.album.ui.SelectAlbumFragment;
import com.hello7890.album.ui.SelectMediaFragment;
import com.qianjujun.frame.base.BetterBaseActivity;

import static com.qianjujun.frame.utils.FrameConstant.KEY_FRAGMENT_NAME;

public class MediaTool {

    public static void toSelectAlbum(Activity activity, MediaOptions options){
        Intent intent = new Intent(activity, AlbumActivity.class);
        intent.putExtra("options",options);
        intent.putExtra(KEY_FRAGMENT_NAME, SelectAlbumFragment.class.getName());
        activity.startActivityForResult(intent,options.getRequestCode());
    }

    public static void toSelectMedia(Activity activity, MediaOptions options, Album album){
        Intent intent = new Intent(activity, AlbumActivity.class);
        intent.putExtra("options",options);
        intent.putExtra("album",album);
        intent.putExtra(KEY_FRAGMENT_NAME, SelectMediaFragment.class.getName());
        activity.startActivityForResult(intent,options.getRequestCode());
    }

}
