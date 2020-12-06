package com.hello7890.mediatools.work;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arthenica.mobileffmpeg.FFmpegHelper;
import com.google.gson.Gson;
import com.hello7890.mediatools.FuncDes;
import com.qianjujun.frame.utils.SPUtils;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Work2Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    private HandlerThread mExecuteThread;
//    private HandlerThread mLogCallBackThread;
//    private HandlerThread mTaskThread;
//    private Handler mTaskHandler;
//
//    private BlockingQueue<FuncDes> queue = new PriorityBlockingQueue<>(100);
//
//    private static final AtomicInteger mPriorityNUm = new AtomicInteger(3000);
//
//    private static final AtomicLong mExecutionIdCounter = new AtomicLong(3000);
//
//    private static ConcurrentHashMap<Long,FuncDes> mIdMap = new ConcurrentHashMap();
//
//
//    private static final Gson mGson = new Gson();
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mTaskThread = new HandlerThread("task");
//        mTaskThread.start();
//        mTaskHandler = new Handler(mTaskHandler.getLooper());
//        mPriorityNUm.set(0);
//
//
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//    private synchronized void updateFuncDesToDisk(){
//        Iterator<FuncDes> iterator = queue.iterator();
//        String json = mGson.toJson(iterator);
//
//    }
//
//    public void execute(@NonNull FuncDes funcDes){
//        mTaskHandler.post(() -> {
//            try {
//                queue.put(funcDes);
//                updateFuncDesToDisk();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//
//    public void execute(@NonNull List<FuncDes> funcDesList)  {
//        mTaskHandler.post(() -> {
//            for(FuncDes funcDes:funcDesList){
//                try {
//                    queue.put(funcDes);
//                    updateFuncDesToDisk();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//
//    public void priorityTask(String id){
//        synchronized (queue){
//            int priority = mPriorityNUm.decrementAndGet();
//            for(FuncDes funcDes:queue){
////                if(TextUtils.equals(id,funcDes.getId())){
////                    funcDes.setPriority(priority);
////                }
//            }
//        }
//    }
//
//
//    private void onTaskOver(FuncDes funcDes){
//
//    }
//
//
//
//
//    private Runnable mExecuteRunnable = new Runnable() {
//        @Override
//        public void run() {
//            while (true){
//                try {
//                    FuncDes funcDes = queue.take();
//                    long executeId = funcDes.getId();
//                    int result = FFmpegHelper.execute(funcDes.getCmd(),executeId);
//                    if(result!=0){
//                        funcDes.setState(FuncDes.State.FAIL);
//                        onTaskOver(funcDes);
//                        return;
//                    }
//
//                    mIdMap.put(executeId,funcDes);
//
//
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    };



}
