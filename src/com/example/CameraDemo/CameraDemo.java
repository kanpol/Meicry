package com.example.CameraDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CameraDemo extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.theme_panel);

        /*LinearLayout layout = (LinearLayout) findViewById(R.id.ll_xx);
        Log.i("xxx", String.valueOf(layout));
        for (int i = 0; i < 15; i++) {
            ImageView im = new ImageView(this);
            im.setImageResource(R.drawable.head);
            layout.addView(im);
        }*/
        Intent intent = new Intent(this, TakePhotoActivity.class);
        startActivity(intent);
    }
}
