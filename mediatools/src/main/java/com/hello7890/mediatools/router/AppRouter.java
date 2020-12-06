package com.hello7890.mediatools.router;

import android.app.Activity;

import com.alibaba.android.arouter.launcher.Router;
import com.hello7890.album.bean.Media;

public class AppRouter implements RouterPath{
    public static void toCompressVideo(Activity activity, Media media){
        Router.buildFragment(PATH_COMPRESS_VIDEO).withParcelable("data",media).navigation(activity);
    }
}
