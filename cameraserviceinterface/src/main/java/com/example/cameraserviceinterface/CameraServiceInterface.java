package com.example.cameraserviceinterface;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class CameraServiceInterface {

    /**
     * variable to store Context instance.
     */
    private Context mContext;
    /**
     * variable to store object of Intent.
     */
    private Intent mIntent;
    /**
     * Member variable to hold service package name.
     */
    private static final String SERVICE_PKG_NAME = "com.example.myservicedemo";
    /**
     * Member variable to hold action.
     */
    private static final String SERVICE_ACTION = "com.example.myservicedemo.start";
    /**
     * variable to store object of IServiceCameraInterface.
     */
    private IServiceCameraInterface mServiceCameraInterface;
    /**
     * variable to store object of IBaseAidlInterface.
     */
    private IBaseAidlInterface mBaseAidlInterface;
    /**
     * variable to store object of ServiceConnection.
     */
    private IServiceConnectionCallback mServiceConnectionCallback;
    /**
     * variable to store object of ServiceConnection.
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        /**
         * @brief Call back method onServiceConnected, for bind service call
         * @param name : component name
         * @param service : service
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBaseAidlInterface = IBaseAidlInterface.Stub.asInterface(service);
            if (mBaseAidlInterface != null)
            {
                try {
                    mServiceCameraInterface = mBaseAidlInterface.getSyncConnection();
                } catch (RemoteException e) {
                    Log.e("onServiceConnected", e.toString());
                }
            }
            mServiceConnectionCallback.onServiceConnected(name,service);
        }
        /**
         * @brief Call back method onServiceDisconnected
         * @param name : component name
         */

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceConnectionCallback.onServiceDisconnected(name);
        }
    };

    /**
     * @brief Method to initialize this class.
     * @param context : context
     */
    public void initialize(Context context) {
        mContext = context;
        mIntent = new Intent();
        mIntent.setPackage(SERVICE_PKG_NAME);
        mIntent.setAction(SERVICE_ACTION);
    }

    /**
     * @brief Method for binding to service
     */

    public void bindServiceApp() {

        mContext.bindService(mIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }
    /**
     * @brief Method for unbinding Rvc app service
     */
    public void unbindRvcAppService() {
        mContext.unbindService(mServiceConnection);
    }
    /**
     * @brief Method to set service connection callback
     * @param serviceConnectionCallback: callback object to register
     */
    public void setServiceConnectionCallback(
            IServiceConnectionCallback serviceConnectionCallback) {
        mServiceConnectionCallback = serviceConnectionCallback;
    }
    /**
     * @brief Method to get camera interfaces
     * @return IServiceCameraInterface: object with camera interfaces
     */
    public IServiceCameraInterface getCameraInterface() {
        return mServiceCameraInterface;
    }

    /**
     * @brief Interface for passing notification to subscribers.
     */
    public interface IServiceConnectionCallback{
        /**
         * @brief Call back method onServiceConnected, for bind service call
         * @param name: component name
         * @param service: service
         */
        void onServiceConnected(ComponentName name, IBinder service);
        /**
         * @brief Call back method onServiceDisconnected
         * @param name: component name
         */
        void onServiceDisconnected(ComponentName name);

    }
}
