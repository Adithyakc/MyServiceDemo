package com.example.myservicedemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.example.cameraserviceinterface.ICameraListener;
import static com.example.cameraserviceinterface.constants.CameraHmiServiceConstants.TAG;
import java.util.HashMap;

/**
 * @brief Camera app service manager which will manage all service common functionality's.
 */
public class CameraAppServiceManager {

    /**
     * Member variable to store CameraService object
     */
    CameraService mCameraService;
    /**
     * Member variable to store context
     */
    Context mContext;
    /**
     * Member variable to store Delay Settings;
     */
    String delay  = "Camera Delay Settings";
    /**
     * Member variable to store Static Guideline Settings;
     */
    String guidline = "Camera Static Guideline Settings";
    /**
     * Member variable to store Swing Door Settings;
     */
    String swing = "Swing Door Settings";
    /**
     * Member variable to store Dynamic Center lines;
     */
    String cargo = "Cargo Cam Dynamic Center lines";
    /**
     * Member variable to store Trailer Camera Settings;
     */
    String trailer = "Trailer Camera Settings";

    /**
     * Member variable for keeping camera callback listeners
     */
    private final RemoteCallbackList<ICameraListener>
            mCameraNotificationCallbackList = new RemoteCallbackList<>();
    /**
     * Member variable to store handler object
     */
    private Handler mHandler = new Handler();
    /**
     * Member variable to store camera delay setting;
     */
    private boolean mCameraStatus;
    /**
     * Member variable to store camera Guideline status;
     */
    private boolean mCameraGuid;

    /**
     * Member variable to store camera Swing status;
     */
    private boolean mCameraSwing;

    /**
     * Member variable to store camera Cargo status;
     */
    private boolean mCameracargo;
    /**
     * Member variable to store camera Trailer status;
     */
    private boolean mCameraTrailer;
    /**
     * Member variable to store camera id;
     */
    private String mCameraId;
    /**
     * Member variable to store Shared preference object.
     */
    SharedPreferences sh;
    /**
     * Member variable to store Shared preference object.
     */
    SharedPreferences sh1;
    /**
     * Member variable to store Shared preference Editor object.
     */
    SharedPreferences.Editor editor;
    /**
     * Member variable to store Shared preference Editor object.
     */

    SharedPreferences.Editor editor1;
    /**
     * Static variable to store singleton object of this class
     */

    private static final CameraAppServiceManager CAMERA_APP_SERVICE_MANAGER = new
            CameraAppServiceManager();

    /**
     * @brief Method to get singleton object of this class
     * @return CameraAppServiceManager: singleton object of this class
     */
    public static CameraAppServiceManager getCameraAppServiceManager() {
        return CAMERA_APP_SERVICE_MANAGER;
    }

    private ICameraListener mCameraListener = new ICameraListener.Stub(){

        /**
         * @brief This api will be invoked in camera status change
         * @param status : boolean value.
         *        setId : String value.
         */
        @Override
        public void notifyCameraSetting(String setId,boolean status) throws RemoteException {

            mHandler.post(new Runnable() {
                @Override
                public void run() {

                    Log.d(TAG, "Inside notifyCameraStatus:" + mCameraStatus+setId);
                    int broadcastCount = mCameraNotificationCallbackList.beginBroadcast();
                    /**
                     * This for loop iterates through number of application bind to service
                     */
                    try {
                        for (int i = 0; i < broadcastCount; i++) {
                            mCameraNotificationCallbackList.getBroadcastItem(i)
                                    .notifyCameraSetting(mCameraId,mCameraStatus);
                        }
                    } catch (RemoteException e) {
                        Log.e(TAG, "notifyCameraStatus " + e);
                    } finally {
                        mCameraNotificationCallbackList.finishBroadcast();
                    }
                }
            });
        }


    };

    public void setContext(Context context){
         mContext = context;
    }

    /**
     * @brief Api to register async connection
     * @param mCameraListener : ICameraListener object
     */

    public void registerAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraNotificationCallbackList.register(mCameraListener);
    }

    /**
     * @brief Api to unregister async connection
     * @param mCameraListener : ICameraListener object
     */
    public void unregisterAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraNotificationCallbackList.unregister(mCameraListener);
    }
    /**
     * @brief Method to setting the value of camera settings.
     * @param setId : setting name
     *        status : status of the setting.(true or false).
     */
    public void setSetting(String setId,boolean status){
       Log.d("SERVICEBTS","hoooo"+mCameraService);
       for (int i=0;i<2;i++){
           sh = mContext.getSharedPreferences("CAMERA SETTING", Context.MODE_PRIVATE);
           editor = sh.edit();
           editor.putBoolean(setId,status);
           editor.commit();
       }
         notify(setId,status);
        Log.d("CamSet","hello i got value"+mCameraStatus);
     }

    /**
     * @brief Method to notify the HMI.
     * @return setting name and setting status.
     */

     void notify(String cameraCode,boolean status){
         try {
             mCameraListener.notifyCameraSetting(cameraCode,status);
         } catch (RemoteException e) {
             e.printStackTrace();
         }
     }

    /**
     * @brief Method to get the setting values of the camera settings.
     * @return setting name and setting status.
     */
    public HashMap<String,Boolean> getSettings(){

        HashMap<String,Boolean> hashMap = new HashMap<>();
        sh = mContext.getSharedPreferences("CAMERA SETTING",Context.MODE_PRIVATE);
        mCameraStatus = sh.getBoolean(delay,false);
        mCameraGuid = sh.getBoolean(guidline,false);
        mCameraSwing = sh.getBoolean(swing,false);
        mCameracargo = sh.getBoolean(cargo,false);
        mCameraTrailer = sh.getBoolean(trailer,false);
        hashMap.put(delay,mCameraStatus);
        hashMap.put(guidline,mCameraGuid);
        hashMap.put(swing,false);
        hashMap.put(cargo,false);
        hashMap.put(trailer,false);
        return hashMap;
    }
    /**
     * Member variable to store previous active camera.
     */

    String mActiveCamera;
    /**
     * @brief Method to set the last active camera.
     * @param camId : camera ID
     */
    public void setCamera(String camId) {
        mActiveCamera = camId;
        sh1 = mContext.getSharedPreferences("CAMERA SETTING", Context.MODE_PRIVATE);
        editor1 = sh.edit();
        editor1.putString("1",camId);
        editor1.commit();
        Log.d("MyCam","CameraStatus"+sh1.getString("1",camId));
    }

    /**
     * @brief Method to get previous active camera
     * @return camera : camera
     */
    public String getCamera(){
        sh1 = mContext.getSharedPreferences("CAMERA SETTING", Context.MODE_PRIVATE);
        mActiveCamera = sh1.getString("1",mActiveCamera);
        Log.d("MyCamID","CameraStatus"+mActiveCamera);
        return mActiveCamera;
    }


}
