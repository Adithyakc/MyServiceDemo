
package com.example.cameraserviceinterface;
import com.example.cameraserviceinterface.IServiceCameraInterface;

interface IBaseAidlInterface {

    /**
     * @brief Api to get sync connection
     */
    IServiceCameraInterface getSyncConnection();

}