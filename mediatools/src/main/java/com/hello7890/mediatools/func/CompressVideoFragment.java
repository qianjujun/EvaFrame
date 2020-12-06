package com.hello7890.mediatools.func;

import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.hello7890.mediatools.Cmd;
import com.hello7890.mediatools.R;
import com.hello7890.mediatools.databinding.FragmentCompressVideoBinding;
import com.hello7890.mediatools.router.RouterPath;
import com.hello7890.mediatools.utils.FileUtils;

import java.io.File;
import java.text.DecimalFormat;


public class CompressVideoFragment extends BaseFuncFragment<FragmentCompressVideoBinding> {

    private long size;

    @Override
    protected void initView(FragmentCompressVideoBinding dataBinding) {
        super.initView(dataBinding);

        File file = new File(mMedia.getPath());
        size = file.length();

        resultSize(mDataBinding.seekBar.getProgress());

        mDataBinding.player.setUp(mMedia.getPath(),"");
        mDataBinding.player.startVideo();

        mDataBinding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long time = System.currentTimeMillis();
                long result = FFmpeg.executeAsync(createCmd(), new ExecuteCallback() {
                    @Override
                    public void apply(long executionId, int returnCode) {
                       hideDialog();
                        Log.d("qianjujun","执行时间"+(System.currentTimeMillis()-time));
                    }
                });
            }
        });

        mDataBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resultSize(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void resultSize(int progress){
        long result = (long) (size * progress /100f);
        mDataBinding.tvResultSize.setText(formatSize(result));
    }


    public static String formatSize(long size){
        //获取到的size为：1705230
        int GB = 1024 * 1024 * 1024;//定义GB的计算常量
        int MB = 1024 * 1024;//定义MB的计算常量
        int KB = 1024;//定义KB的计算常量
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String resultSize = "";
        if (size / GB >= 1) {
            //如果当前Byte的值大于等于1GB
            resultSize = df.format(size / (float) GB) + "GB";
        } else if (size / MB >= 1) {
            //如果当前Byte的值大于等于1MB
            resultSize = df.format(size / (float) MB) + "MB";
        } else if (size / KB >= 1) {
            //如果当前Byte的值大于等于1KB
            resultSize = df.format(size / (float) KB) + "KB";
        } else {
            resultSize = size + "B";
        }
        return resultSize;
    }





    @Override
    protected void onVideoInfo(String info,long bit) {
        mDataBinding.tvVideoInfo.setText(info);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_compress_video;
    }


    @Override
    protected String createCmd() {

        int bitter = (int) (bit*mDataBinding.seekBar.getProgress()/100f/1000);
        Cmd cmd = new Cmd();
        cmd.append("-hwaccel")
                .append("mediacodec")
                .append("-y")
                .append("-i")
                .append(mMedia.getPath())
                .append("-vcodec copy")
                .append("-b:v")
                .append(bitter+"k")
                .append("-preset superfast")
                .append(FileUtils.getExportFile().getAbsolutePath());


        return cmd.toString();
    }



}
