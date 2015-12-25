package com.ratik.surfacecameraexample;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.ImageButton;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    private ImageButton shutterButton;

    private Camera camera;
    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shutterButton = (ImageButton) findViewById(R.id.shutter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get an instance of the Camera
        camera = CameraHelper.getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        cameraPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
        preview.addView(cameraPreview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.release();
        }
    }
}
