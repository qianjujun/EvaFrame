package com.hello7890.mediatools.func;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFprobe;
import com.arthenica.mobileffmpeg.Level;
import com.arthenica.mobileffmpeg.LogCallback;
import com.arthenica.mobileffmpeg.LogMessage;
import com.arthenica.mobileffmpeg.Signal;
import com.arthenica.mobileffmpeg.Statistics;
import com.arthenica.mobileffmpeg.StatisticsCallback;
import com.hello7890.album.bean.Media;
import com.hello7890.mediatools.Cmd;
import com.hello7890.mediatools.MainActivity;
import com.hello7890.mediatools.R;
import com.qianjujun.frame.base.BetterDbFragment;
import com.qianjujun.frame.utils.RxUtil;
import java.math.BigDecimal;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public abstract class BaseFuncFragment<T extends ViewDataBinding> extends BetterDbFragment<T> {

    protected Media mMedia;
    private Handler mHandler = new Handler();

    private AlertDialog dialog;

    @Override
    protected void initArgs(@NonNull Bundle args) {
        super.initArgs(args);
        mMedia = args.getParcelable("data");
    }

    protected abstract String createCmd();

    @Override
    protected void initView(T dataBinding) {



        getVideoInfo();







    }

    protected void updateDialog(Statistics statistics){
        if(dialog==null){
            dialog = new AlertDialog.Builder(mActivity)
                    .setMessage("进度")
                    .create();
        }
        int timeInMilliseconds = statistics.getTime();
        if (timeInMilliseconds > 0) {
            int totalVideoDuration = 9000;

            String completePercentage = new BigDecimal(timeInMilliseconds).multiply(new BigDecimal(100)).divide(new BigDecimal(totalVideoDuration), 0, BigDecimal.ROUND_HALF_UP).toString();
            dialog.setMessage(String.format("Encoding video: %% %s", completePercentage));
        }
        if(!dialog.isShowing()){
            dialog.show();
        }


    }

    protected void hideDialog(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }


    protected abstract void onVideoInfo(String info,long bit);



    protected long bit;

    @SuppressLint("CheckResult")
    private void getVideoInfo(){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        Flowable.just(mMedia.getPath())
                .map(new Function<String, String>() {

                    @Override
                    public String apply(String s) throws Exception {

                        retriever.setDataSource(s);
                        String bitrateString = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
                        String CAPTURE_FRAMERATE = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CAPTURE_FRAMERATE);
                        //如果媒体包含视频，这个键就会检索它的宽度。
                        String video_width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                        //如果媒体包含视频，这个键就会检索它的高度。
                        String video_height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);

                        bit = Long.parseLong(bitrateString);

                        StringBuilder sb = new StringBuilder();
                        sb.append("码率：");
                        sb.append(bitrateString);
                        sb.append("\n");
                        sb.append("帧率：");


                        sb.append(CAPTURE_FRAMERATE);
                        sb.append("\n");
                        sb.append("分辨率");
                        sb.append(video_width);
                        sb.append("X");
                        sb.append(video_height);
                        return sb.toString();
                    }
                }).compose(RxUtil.rxSchedulerHelper())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        onVideoInfo(s,bit);
                        retriever.release();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        onVideoInfo("未获取",0);
                        retriever.release();
                    }
                });
    }
}
