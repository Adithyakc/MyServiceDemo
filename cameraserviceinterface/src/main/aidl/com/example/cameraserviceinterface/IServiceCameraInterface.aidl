// IServiceCameraInterface.aidl
package com.example.cameraserviceinterface;
import com.example.cameraserviceinterface.ICameraListener;
// Declare any non-default types here with import statements

interface IServiceCameraInterface {
    /**
     * @brief Method to set the value of setting.
     */
    void setSetting(String setId,boolean status);
    /**
     * @brief Method to get the value of setting
     */
    Map getSettings();

    void setCamera(String camId);

    String getCamera();
/**
     * @brief Api to register async connection
     */
     void registerAsyncConnection(ICameraListener mCameraListener);
     /**
      * @brief Api to unregister async connection
      */
     void unregisterAsyncConnection(ICameraListener mCameraListener);

}