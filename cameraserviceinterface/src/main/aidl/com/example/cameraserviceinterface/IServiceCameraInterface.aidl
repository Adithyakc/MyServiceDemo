
package com.example.cameraserviceinterface;
import com.example.cameraserviceinterface.ICameraListener;


interface IServiceCameraInterface {
    /**
     * @brief Method to set the value of setting.
     */
    void setSetting(String setId,boolean status);
    /**
     * @brief Method to get the value of setting
     */
    Map getSettings();
    /**
     * @brief Method to set the value of camera
     */
    void setCamera(String camId);
    /**
     * @brief Method to get the value of camera
     */
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