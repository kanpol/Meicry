package com.example.util;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import com.example.memorycache.ImageCache;

public class DownImage extends AsyncTask {

		private ImageView imageView;

		public DownImage(ImageView imageView) {
			this.imageView = imageView;
		}

		@Override
		protected Object doInBackground(Object... params) {
			String url = (String)params[0];
			Bitmap bitmap = null;
			try {
				bitmap = ImageCache.getBitmap(url, imageView.getContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Object result) {
			Bitmap bitmap = (Bitmap) result;
			imageView.setImageBitmap(bitmap);
		}
	}