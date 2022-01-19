package com.example.myservicedemo.Stub;

import android.os.RemoteException;
import android.util.Log;

import com.example.cameraserviceinterface.ICameraListener;
import com.example.cameraserviceinterface.IServiceCameraInterface;
import com.example.myservicedemo.CameraAppServiceManager;

import java.util.HashMap;
import java.util.Map;

public class ServiceCameraInterface extends IServiceCameraInterface.Stub {

    private final CameraAppServiceManager mCameraAppServiceManager =
            CameraAppServiceManager.getCameraAppServiceManager();

    @Override
    public void setSetting(String setId, boolean status) throws RemoteException {
        Log.d("Attez","setSetting");
          mCameraAppServiceManager.setSetting(setId,status);
    }

    @Override
    public HashMap<String,Boolean> getSettings() throws RemoteException {
        return mCameraAppServiceManager.getSettings();
    }

    @Override
    public void setCamera(String camId) throws RemoteException {
       mCameraAppServiceManager.setCamera(camId);
    }

    @Override
    public String getCamera() throws RemoteException {
        return mCameraAppServiceManager.getCamera();
    }

    @Override
    public void registerAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraAppServiceManager.registerAsyncConnection(mCameraListener);
    }

    @Override
    public void unregisterAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraAppServiceManager.unregisterAsyncConnection(mCameraListener);
    }


}
