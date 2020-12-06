package com.hello7890.mediatools.work;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpegHelper;
import com.arthenica.mobileffmpeg.Level;
import com.arthenica.mobileffmpeg.LogCallback;
import com.arthenica.mobileffmpeg.LogMessage;
import com.arthenica.mobileffmpeg.Signal;
import com.arthenica.mobileffmpeg.Statistics;
import com.arthenica.mobileffmpeg.StatisticsCallback;
import com.hello7890.mediatools.FuncDes;
import com.hello7890.mediatools.FuncState;
import com.hello7890.mediatools.IExcuteListener;
import com.hello7890.mediatools.IFFmpegInterface;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;


//https://www.jianshu.com/p/84a12977dc26
//https://www.jianshu.com/p/384ba020428a

/**
 * 单任务一次执行一个功能
 */
public class WorkService extends Service implements LogCallback, StatisticsCallback {
    private static final String TAG = "Work3Service";

    private RemoteCallbackList<IExcuteListener> mListenerList = new RemoteCallbackList<>();

    private Handler mCallbackHandler;

    private Handler mWorkHandler;


    private static ConcurrentHashMap<Long, FuncDes> mIdMap = new ConcurrentHashMap();


    private LinkedBlockingDeque<FuncDes> mBlockingDeque = new LinkedBlockingDeque<>();


    private Thread executeThread = new Thread(){
        @Override
        public void run() {
            while (true){
                try {
                    FuncDes fundes = mBlockingDeque.take();
                    if(fundes==null){
                        continue;
                    }

                    long id = fundes.getId();
                    mIdMap.put(id, fundes);
                    int result = FFmpegHelper.execute(fundes.getCmd(), id);
                    if(result!=0){
                        fundes.setState(FuncState.FAIL);
                    }else {
                        fundes.setState(FuncState.COMPLETE);
                    }
                    onExecuteEnd(fundes);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void apply(LogMessage message) {

    }

    @Override
    public void apply(Statistics statistics) {
        mCallbackHandler.post(() -> update(statistics));
    }


    private void update(Statistics statistics) {
        long executionId= statistics.getExecutionId();
        final FuncDes funcDes = mIdMap.get(executionId);
        Log.d(TAG, "update() called with: statistics = [" + statistics + "]" + funcDes + "   :" + mIdMap);
        if (funcDes == null) {
            return;
        }
        funcDes.setProgress((int) (statistics.getTime()*100f/funcDes.getDuration()));
        funcDes.setExecuteTime(statistics.getTime());
        int n = mListenerList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            IExcuteListener listener = mListenerList.getBroadcastItem(i);
            if (listener == null) {
                continue;
            }
            try {
                listener.onProgress(funcDes);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mListenerList.finishBroadcast();
    }


    private void onExecuteEnd(final FuncDes funcDes){
        mCallbackHandler.post(new Runnable() {
            @Override
            public void run() {
                int n = mListenerList.beginBroadcast();
                for (int i = 0; i < n; i++) {
                    IExcuteListener listener = mListenerList.getBroadcastItem(i);
                    if (listener == null) {
                        continue;
                    }
                    try {
                        listener.complete(funcDes);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                mListenerList.finishBroadcast();
            }
        });
    }







    @Override
    public void onCreate() {
        super.onCreate();
        Config.ignoreSignal(Signal.SIGXCPU);
        Config.setLogLevel(Level.AV_LOG_DEBUG);
        Config.enableLogCallback(this);
        Config.enableStatisticsCallback(this);
        HandlerThread callbackThread = new HandlerThread("callback");
        callbackThread.start();
        mCallbackHandler = new Handler(callbackThread.getLooper());

        HandlerThread workThread = new HandlerThread("work");
        workThread.start();
        mWorkHandler = new Handler(workThread.getLooper());


        executeThread.start();

    }





    private Binder mBinder = new IFFmpegInterface.Stub() {
        @Override
        public void execute(final FuncDes fundes) throws RemoteException {
            Log.d(TAG, "excute() called with: fundes = [" + fundes + "]");
            mWorkHandler.post(() -> {
                try {
                    mBlockingDeque.put(fundes);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }

        @Override
        public void cancel() throws RemoteException {

        }

        @Override
        public void register(IExcuteListener listener) throws RemoteException {
            mListenerList.register(listener);
            Log.d(TAG, "register() called with: listener = [" + listener + "]");
        }

        @Override
        public void unregister(IExcuteListener listener) throws RemoteException {
            mListenerList.unregister(listener);
            Log.d(TAG, "unregister() called with: listener = [" + listener + "]");
        }
    };





















}
