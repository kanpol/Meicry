package com.example.CameraDemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

public class XCameraDemo extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.home_photo_item);
    }
}
