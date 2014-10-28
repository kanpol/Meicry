package com.example.CameraDemo;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class PhotoPreview extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_preview);

       /* Intent intent = getIntent();
        byte[] data = intent.getByteArrayExtra("photo_data");
        ImageView img = (ImageView) findViewById(R.id.photo);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        img.setImageBitmap(bitmap);*/
    }
}
