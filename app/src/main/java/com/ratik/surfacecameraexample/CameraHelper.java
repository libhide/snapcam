package com.ratik.surfacecameraexample;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;

/**
 * Created by Ratik on 25/12/15.
 */
public class CameraHelper {

    public static final String TAG = CameraHelper.class.getSimpleName();
    public static int CURRENT_CAMERA_ID = -1;

    /** Check if this device has a camera */
    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    /** A safe way to get an instance of the Camera object. */
    @SuppressWarnings("deprecation")
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            CURRENT_CAMERA_ID = Camera.CameraInfo.CAMERA_FACING_FRONT;
            c = Camera.open(CURRENT_CAMERA_ID);
        } catch (Exception e) {
            Log.e(TAG, "Failed to open Camera");
        }
        return c;
    }

    @SuppressWarnings("deprecation")
    public static void setCameraDisplayOrientation(Activity activity, int cameraId,
                                                   Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}
