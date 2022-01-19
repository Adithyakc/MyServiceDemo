package com.example.myservicedemo.Stub;

import android.os.RemoteException;
import android.util.Log;

import com.example.cameraserviceinterface.ICameraListener;
import com.example.cameraserviceinterface.IServiceCameraInterface;
import com.example.myservicedemo.CameraAppServiceManager;

import java.util.HashMap;
import java.util.Map;

public class ServiceCameraInterface extends IServiceCameraInterface.Stub {

    /**
     * Member variable to store {@link CameraAppServiceManager} object.
     */
    private final CameraAppServiceManager mCameraAppServiceManager =
            CameraAppServiceManager.getCameraAppServiceManager();

    /**
     * @brief Method to setting the value of camera settings.
     * @param setId : setting name
     *        status : status of the setting.(true or false).
     */
    @Override
    public void setSetting(String setId, boolean status) throws RemoteException {
        Log.d("Attez","setSetting");
          mCameraAppServiceManager.setSetting(setId,status);
    }
    /**
     * @brief Method to get the setting values of the camera settings.
     * @return setting name and setting status.
     */
    @Override
    public HashMap<String,Boolean> getSettings() throws RemoteException {
        return mCameraAppServiceManager.getSettings();
    }

    /**
     * @brief Method to set the last active camera.
     * @param camId : camera ID
     */
    @Override
    public void setCamera(String camId) throws RemoteException {
       mCameraAppServiceManager.setCamera(camId);
    }

    /**
     * @brief Method to get previous active camera
     * @return camera : camera
     */

    @Override
    public String getCamera() throws RemoteException {
        return mCameraAppServiceManager.getCamera();
    }
    /**
     * @brief Api to register async connection
     * @param mCameraListener : ICameraListener object
     */
    @Override
    public void registerAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraAppServiceManager.registerAsyncConnection(mCameraListener);
    }

    /**
     * @brief Api to unregister async connection
     * @param mCameraListener : ICameraListener object
     */
    @Override
    public void unregisterAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraAppServiceManager.unregisterAsyncConnection(mCameraListener);
    }


}
