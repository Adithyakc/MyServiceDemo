package com.example.cameraserviceinterface.constants;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CameraHmiServiceConstants {
    /**
     * Camera Service trace log tag.
     */
    public static final String TAG = "CameraService";

    /**
     * @brief All camera type constants are added here
     */
    @IntDef({ICameraTypes.NO_CAMERAS_AVAILABLE, ICameraTypes.REAR_VIEW_CAMERA, ICameraTypes.FORWARD_FACING_CAMERA,
            ICameraTypes.CARGO_CAMERA, ICameraTypes.AUX_CAMERA})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ICameraTypes {
        int NO_CAMERAS_AVAILABLE = 0;
        int REAR_VIEW_CAMERA = 1;
        int FORWARD_FACING_CAMERA = 2;
        int CARGO_CAMERA = 3;
        int AUX_CAMERA = 4;
    }
}
