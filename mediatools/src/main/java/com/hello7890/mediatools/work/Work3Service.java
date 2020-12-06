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
import java.util.concurrent.atomic.AtomicLong;

public class Work3Service extends Service implements LogCallback, StatisticsCallback {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void apply(LogMessage message) {

    }

    @Override
    public void apply(Statistics statistics) {

    }

    //    private static final AtomicLong executionIdCounter = new AtomicLong(3000);
//
//    public static final String TAG = "WorkService";
//
//    // 系统提供的专门用于删除跨进程 listener 的类
//    private RemoteCallbackList<IExcuteListener> mListenerList = new RemoteCallbackList<>();
//
//
//    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS,
//            new LinkedBlockingDeque<>(100));
//
//
//    private Handler mCallbackHandler;
//
//    private static ConcurrentHashMap<Long,FuncDes> mIdMap = new ConcurrentHashMap();
//
//
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        Config.ignoreSignal(Signal.SIGXCPU);
//        Config.setLogLevel(Level.AV_LOG_DEBUG);
//        Config.enableLogCallback(this);
//        Config.enableStatisticsCallback(this);
//        HandlerThread callbackThread = new HandlerThread("callback");
//        callbackThread.start();
//        mCallbackHandler = new Handler(callbackThread.getLooper());
//
//        new Thread(checkResult).start();//开启合并线程
//
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder;
//    }
//
//    @Override
//    public boolean onUnbind(Intent intent) {
//        return true;
//    }
//
//    @Override
//    public void onRebind(Intent intent) {
//        super.onRebind(intent);
//    }
//
//    @Override
//    public void apply(LogMessage message) {
//        Log.d(TAG, " [" + message.getText() + "]");
//    }
//
//    //20:50  --- 21:35
//
//    @Override
//    public void apply(Statistics statistics) {
//        parseProgress(statistics);
//    }
//
//
//    private void parseProgress(final Statistics statistics){
//        Log.d(TAG, "parseProgress() called with: statistics = [" + statistics + "]");
//        mCallbackHandler.post(new Runnable() {
//            @Override
//            public void run() {
//               int time = statistics.getTime();
//
//               update(statistics);
//            }
//        });
//    }
//
//    private void parseSuccess(final FuncDes funcDes){
//        mCallbackHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                int n = mListenerList.beginBroadcast();
//                for(int i = 0; i < n; i++){
//                    IExcuteListener listener = mListenerList.getBroadcastItem(i);
//                    if(listener == null){
//                        continue;
//                    }
//                    try {
//                        listener.complete(funcDes.getId(),funcDes.getIndex());
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }
//                mListenerList.finishBroadcast();
//            }
//        });
//    }
//
//
//    private void update(Statistics statistics){
//        long exuteId = statistics.getExecutionId();
//        final FuncDes funcDes = mIdMap.get(exuteId);
//        Log.d(TAG, "update() called with: statistics = [" + statistics + "]"+funcDes+"   :"+mIdMap);
//        if(funcDes==null){
//            return;
//        }
//        int n = mListenerList.beginBroadcast();
//        for(int i = 0; i < n; i++){
//            IExcuteListener listener = mListenerList.getBroadcastItem(i);
//            if(listener == null){
//                continue;
//            }
//            try {
//                listener.onPogress(getClass().getSimpleName(),funcDes.getId(),funcDes.getIndex(),statistics.getTime());
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//        mListenerList.finishBroadcast();
//    }
//
//
//
//
//
//    private Binder mBinder = new IFFmpegInterface.Stub() {
//        @Override
//        public void excute(FuncDes fundes) throws RemoteException {
//            Log.d(TAG, "excute() called with: fundes = [" + fundes + "]");
//            Task task = new Task(fundes);
//            executor.execute(task);
//        }
//
//        @Override
//        public void register(IExcuteListener listener) throws RemoteException {
//            mListenerList.register(listener);
//            Log.d(TAG, "register() called with: listener = [" + listener + "]");
//        }
//
//        @Override
//        public void unregister(IExcuteListener listener) throws RemoteException {
//            mListenerList.unregister(listener);
//            Log.d(TAG, "unregister() called with: listener = [" + listener + "]");
//        }
//    };
//
//
//    private BlockingQueue<FuncDes> queue = new LinkedBlockingDeque<>();
//
//
//     class Task implements Runnable{
//
//        private final FuncDes funcDes;
//        private final long executeId;
//
//        Task(FuncDes funcDes) {
//            this.funcDes = funcDes;
//            this.executeId = executionIdCounter.decrementAndGet();
//            mIdMap.put(executeId,funcDes);
//        }
//
//        @Override
//        public void run() {
//            Log.e("qianjujun", "run: "+ Thread.currentThread().getName() + "  "+funcDes.getCmd());
//
//            int result = FFmpegHelper.execute(funcDes.getCmd(),executeId);
//
//
//
//            long id = funcDes.getId();
//            if(result!=0){
//                //取消所有任务
//                //FFmpeg.cancel();
//                return;
//            }
//            if(funcDes.getTotal()==1){
//                //回调接口
//                parseSuccess(funcDes);
//
//                return;
//            }
//
//            try {
//                queue.put(funcDes);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                //结束所有id任务流
//            }
//
//        }
//    }
//
//
//    private Runnable checkResult = new Runnable() {
//         private Map<Long,List<FuncDes>> map = new HashMap<>();
//        @Override
//        public void run() {
//            while (true){
//                try {
//                    FuncDes des = queue.take();
//                    List<FuncDes> list;
//                    if(map.containsKey(des.getId())){
//                        list = map.get(des.getId());
//                    }else {
//                        list = new ArrayList<>();
//                        map.put(des.getId(),list);
//                    }
//                    list.add(des);
//                    Log.d(TAG, "run() called"+list.size());
//                    if(list.size()==des.getTotal()){
//                        map.remove(des.getId());
//                        //执行合并任务
//                        parseSuccess(des);
//                    }
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };
}
