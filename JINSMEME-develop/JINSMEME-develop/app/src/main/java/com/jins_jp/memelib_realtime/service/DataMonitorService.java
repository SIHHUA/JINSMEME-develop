package com.jins_jp.memelib_realtime.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jins_jp.meme.MemeLib;
import com.jins_jp.meme.MemeRealtimeData;
import com.jins_jp.meme.MemeRealtimeListener;


public class DataMonitorService extends Service {
    public static final String TAG = "MyService";
    private MemeLib memeLib;

    @Override
    public void onCreate() {
        super .onCreate();
        memeLib = MemeLib.getInstance();
        memeLib.startDataReport(new MemeRealtimeListener() {
            @Override
            public void memeRealtimeCallback(MemeRealtimeData memeRealtimeData) {
                Log.d(TAG, memeRealtimeData.getAccX()+"");
            }
        });
        Log.d(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");

        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
