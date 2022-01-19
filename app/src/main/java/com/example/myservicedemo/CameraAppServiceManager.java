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

public class CameraAppServiceManager {
    CameraService mCameraService;
    Context mContext;
    String delay  = "Camera Delay Settings";
    String guidline = "Camera Static Guideline Settings";
    String swing = "Swing Door Settings";
    String cargo = "Cargo Cam Dynamic Center lines";
    String trailer = "Trailer Camera Settings";
    private final RemoteCallbackList<ICameraListener>
            mCameraNotificationCallbackList = new RemoteCallbackList<>();

    private Handler mHandler = new Handler();
    /**
     * Member variable to store camera status;
     */
    private boolean mCameraStatus;
    private boolean mCameraGuid;
    private boolean mCameraSwing;
    private boolean mCameracargo;
    private boolean mCameraTrailer;
    private String mCameraId;
    SharedPreferences sh;
    SharedPreferences sh1;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor editor1;
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

    public void registerAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraNotificationCallbackList.register(mCameraListener);
    }

    public void unregisterAsyncConnection(ICameraListener mCameraListener) throws RemoteException {
        mCameraNotificationCallbackList.unregister(mCameraListener);
    }

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

     void notify(String cameraCode,boolean status){
         try {
             mCameraListener.notifyCameraSetting(cameraCode,status);
         } catch (RemoteException e) {
             e.printStackTrace();
         }
     }
    public HashMap<String,Boolean> getSettings(){
        Log.d("AESPA","getSetting status"+mCameraId+" "+mCameraStatus);
        HashMap<String,Boolean> hashMap = new HashMap<>();
        sh = mContext.getSharedPreferences("CAMERA SETTING",Context.MODE_PRIVATE);
        mCameraStatus = sh.getBoolean(delay,false);
        mCameraGuid = sh.getBoolean(guidline,false);
//        mCameraSwing = sh.getBoolean(swing,false);
//        mCameracargo = sh.getBoolean(cargo,false);
//        mCameraTrailer = sh.getBoolean(trailer,false);
        hashMap.put(delay,mCameraStatus);
        hashMap.put(guidline,mCameraGuid);
        hashMap.put(swing,false);
        hashMap.put(cargo,false);
        hashMap.put(trailer,false);
        return hashMap;
    }


    String mActiveCamera;
    public void setCamera(String camId) {
        mActiveCamera = camId;
        sh1 = mContext.getSharedPreferences("CAMERA SETTING", Context.MODE_PRIVATE);
        editor1 = sh.edit();
        editor1.putString("1",camId);
        editor1.commit();
        Log.d("MyCam","CameraStatus"+sh1.getString("1",camId));
    }


    public String getCamera(){
        sh1 = mContext.getSharedPreferences("CAMERA SETTING", Context.MODE_PRIVATE);
        mActiveCamera = sh1.getString("1",mActiveCamera);
        Log.d("MyCamID","CameraStatus"+mActiveCamera);
        return mActiveCamera;
    }


}
