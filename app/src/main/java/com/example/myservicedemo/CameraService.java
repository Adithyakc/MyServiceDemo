package com.example.myservicedemo;

import android.app.Service;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myservicedemo.Stub.ServiceBaseInterface;

public class CameraService extends Service {

    private final CameraAppServiceManager mCameraAppServiceManager =
            CameraAppServiceManager.getCameraAppServiceManager();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

//        Log.d("CameraService","OnBind");
        return new ServiceBaseInterface();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCameraAppServiceManager.setContext(getApplicationContext());
    }

//    public CameraService() {
////        mCameraAppServiceManager.setContext(getApplicationContext());
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("CameraService", "OnStart");
        return Service.START_STICKY;
    }



}
