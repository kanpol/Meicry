package com.example.CameraDemo;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

public class PhotoPreview extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_preview);

       /* Intent intent = getIntent();
        byte[] data = intent.getByteArrayExtra("photo_data");
        ImageView img = (ImageView) findViewById(R.id.photo);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        img.setImageBitmap(bitmap);*/

        Log.i("xxxxxxxxxxxx", getCacheDir().getAbsolutePath());
        String cachePhoto = getIntent().getStringExtra("image_path");
      /*  BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(cachePhoto, options);*/
        ImageView img = (ImageView) findViewById(R.id.photo);
        Bitmap bitmap = BitmapFactory.decodeFile(cachePhoto);
        img.setImageBitmap(bitmap);

        Log.i("xxxxxxxxxxx", "width:" + bitmap.getWidth() + "height:" + bitmap.getHeight());
    }
}
