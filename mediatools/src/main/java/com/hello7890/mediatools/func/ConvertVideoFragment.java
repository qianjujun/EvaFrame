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
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        try {
            mFFmpeg.unregister(mExcuteListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private IExcuteListener mExcuteListener = new IExcuteListener.Stub() {


        @Override
        public void onProgress(FuncDes des) throws RemoteException {

        }

        @Override
        public void complete(FuncDes des) throws RemoteException {

        }


    };




    @Override
    protected String createCmd() {
        return null;
    }



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


            }
        });


    }





    @Override
    protected void onVideoInfo(String info, long bit) {
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
