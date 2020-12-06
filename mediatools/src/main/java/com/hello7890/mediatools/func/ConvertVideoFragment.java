package com.hello7890.mediatools.func;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hello7890.mediatools.Cmd;
import com.hello7890.mediatools.FuncDes;
import com.hello7890.mediatools.IExcuteListener;
import com.hello7890.mediatools.IFFmpegInterface;
import com.hello7890.mediatools.R;
import com.hello7890.mediatools.databinding.FragmentTestServiceBinding;
import com.hello7890.mediatools.router.RouterPath;
import com.hello7890.mediatools.utils.FileUtils;
import com.hello7890.mediatools.work.Work2Service;
import com.hello7890.mediatools.work.WorkService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


@Route(path = RouterPath.PATH_COMPRESS_VIDEO)
public class ConvertVideoFragment extends BaseFuncFragment<FragmentTestServiceBinding> implements ServiceConnection {

    public static final String PATH1 = "/storage/emulated/0/TestVideo/test1.mp4";
    public static final String PATH2 = "'/storage/emulated/0/TestVideo/test2.mp4'";
    public static final String PATH3 = "/storage/emulated/0/TestVideo/test3.mp4";

    public static final String PATH = "/storage/emulated/0/TestVideo/test.mp4";

    public static final String TAG = "ConvertVideoFragment";

    private IFFmpegInterface mFFmpeg;

    private IFFmpegInterface mFFmpeg2;
    private Handler mHandler = new Handler();



    private HandlerThread mHandlerThread = new HandlerThread("ffmpeg 执行线程");
    private Handler mWorkHandler;



    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "onServiceConnected() called with: name = [" + name.getClassName() + "], service = [" + name.getShortClassName() + "]");
        try {
            mFFmpeg = IFFmpegInterface.Stub.asInterface(service);
            mFFmpeg.register(mExcuteListener);
        } catch (RemoteException e) {
            e.printStackTrace();
            showText("onServiceConnected" + e.getMessage());
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        try {
            mFFmpeg.unregister(mExcuteListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        showText("onServiceDisconnected");
    }


    private IExcuteListener mExcuteListener = new IExcuteListener.Stub() {


        @Override
        public void onProgress(FuncDes des) throws RemoteException {

        }

        @Override
        public void complete(FuncDes des) throws RemoteException {

        }


    };

    private int totalD = (22*60+25)*1000;

    private String processProgress(long timeInMilliseconds,int index){
        int duration = index < 0 ? totalD*2:totalD;
        String completePercentage = new BigDecimal(timeInMilliseconds).multiply(new BigDecimal(100)).divide(new BigDecimal(duration), 0, BigDecimal.ROUND_HALF_UP).toString();
        return completePercentage;
    }


    Map<String,Integer> map = new HashMap<>();
    private void showProgress(long id,int index, int porgress){
        Log.d(TAG, "showProgress() called with: id = [" + id + "], index = [" + index + "], porgress = [" + porgress + "]");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(index==-1){
                    showText(mDataBinding.tvProgress1,processProgress(porgress,-1));
                    return;
                }

                if(index ==0){
                    showText(mDataBinding.tvProgress21,processProgress(porgress,index));
                }else if(index ==1){
                    showText(mDataBinding.tvProgress22,processProgress(porgress,index));
                }
//                try {
//                    int total = Integer.parseInt(mDataBinding.tvProgress21.getText().toString())+Integer.parseInt(mDataBinding.tvProgress22.getText().toString());
//                    showText(mDataBinding.tvProgress2,processProgress(total));
//                }catch (Exception e){
//
//                }


            }
        });
    }

    private void showText(TextView tv,String text){
        tv.setText(text);
    }


    private void showText(String str) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                String text = mDataBinding.tvText.getText().toString();
                text += "\n" + str;
                mDataBinding.tvText.setText(text);
            }
        });

    }


    @Override
    protected String createCmd() {
        return null;
    }

    long startTime;

    @Override
    protected void initView(FragmentTestServiceBinding dataBinding) {

        Log.d(TAG, "initView() called with: dataBinding = [" + mMedia.getPath() + "]" + mMedia.getDuration());

        mHandlerThread.start();
        mWorkHandler = new Handler(mHandlerThread.getLooper());
        mDataBinding.btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFFmpeg == null) {
                    return;
                }

                startTime = System.currentTimeMillis();

                showText("开始时间：" + startTime);
                mWorkHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        perferm(1);
                    }
                });
            }
        });


        mDataBinding.btnUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                showText("开始时间：" + startTime);
                Cmd cmd = new Cmd();
                cmd.append("-hwaccel")
                        .append("mediacodec")
                        .append("-y")
                        .append("-i")
                        .append(PATH)
                        .append("-ss")
                        .append("00:00:00")
                        .append("-t")
                        .append("00:22:25")
                        .append("-b:v")
                        .append("1000k")
                        .append("-preset superfast")
                        .append(FileUtils.getExportFile().getAbsolutePath());

                long id = FuncDes.generateId();
                try {
                    mFFmpeg.excute(new FuncDes(id,cmd.toString(),0,1));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }


            }
        });

        mDataBinding.btnUnbind1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                showText("开始时间：" + startTime);
                long id = FuncDes.generateId();
                Cmd cmd = new Cmd();
                cmd.append("-hwaccel")
                        .append("mediacodec")
                        .append("-y")
                        .append("-i")
                        .append(PATH)
                        .append("-ss")
                        .append("00:22:25")
                        .append("-t")
                        .append("00:22:25")
                        .append("-b:v")
                        .append("1000k")
                        .append("-preset superfast")
                        .append(FileUtils.getExportFile().getAbsolutePath());
                try {
                    mFFmpeg2.excute(new FuncDes(id,cmd.toString(),1,1));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mDataBinding.btnUnbind2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = System.currentTimeMillis();
                showText("开始时间：" + startTime);

                Cmd cmd = new Cmd();
                cmd.append("-hwaccel")
                        .append("mediacodec")
                        .append("-y")
                        .append("-i")
                        .append(PATH)
                        .append("-ss")
                        .append("00:00:00")
                        .append("-t")
                        .append("00:22:25")
                        .append("-b:v")
                        .append("1000k")
                        .append("-preset superfast")
                        .append(FileUtils.getExportFile().getAbsolutePath());

                Cmd cmd2 = new Cmd();
                cmd2.append("-hwaccel")
                        .append("mediacodec")
                        .append("-y")
                        .append("-i")
                        .append(PATH)
                        .append("-ss")
                        .append("00:22:25")
                        .append("-t")
                        .append("00:22:25")
                        .append("-b:v")
                        .append("1000k")
                        .append("-preset superfast")
                        .append(FileUtils.getExportFile().getAbsolutePath());

                long id = FuncDes.generateId();
                try {
                    mFFmpeg.excute(new FuncDes(id,cmd.toString(),0,1));
                    mFFmpeg.excute(new FuncDes(id,cmd2.toString(),1,1));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });






    }


    private void perferm(int num) {
        String thread = "";
        if (num > 1) {
            thread = "-threads " + num;
        }

        Cmd cmd = new Cmd();
        cmd.append("-hwaccel")
                .append("mediacodec")
                .append("-y")
                .append("-i")
                .append(PATH)
                .append(thread)
                //.append("-c:a copy")
                .append("-b:v")
                .append("1000k")
                .append("-preset superfast")
                .append(FileUtils.getExportFile().getAbsolutePath());


        try {
            mFFmpeg.excute(new FuncDes(cmd.toString()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onVideoInfo(String info, long bit) {
        showText(info);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test_service;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = new Intent(mActivity, WorkService.class);
        mActivity.bindService(intent, ConvertVideoFragment.this, Context.BIND_AUTO_CREATE);


        Intent intent1 = new Intent(mActivity, Work2Service.class);
        mActivity.bindService(intent1, ConvertVideoFragment.this, Context.BIND_AUTO_CREATE);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mActivity.unbindService(ConvertVideoFragment.this);
    }


    /**
     * 一小时的秒数
     */
    private static final int HOUR_SECOND = 60 * 60;

    /**
     * 一分钟的秒数
     */
    private static final int MINUTE_SECOND = 60;

    public static String formatTime(int second) {
        if (second <= 0) {

            return "00:00:00";
        }

        int hours = second / HOUR_SECOND;
        if (hours > 0) {

            second -= hours * HOUR_SECOND;
        }

        int minutes = second / MINUTE_SECOND;
        if (minutes > 0) {

            second -= minutes * MINUTE_SECOND;
        }

        return (hours >= 10 ? (hours + "")
                : ("0" + hours) + ":" + (minutes >= 10 ? (minutes + "") : ("0" + minutes)) + ":"
                + (second >= 10 ? (second + "") : ("0" + second)));
    }

}
