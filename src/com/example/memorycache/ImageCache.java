package com.example.memorycache;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.example.util.DownImage;

public class ImageCache {

	

	public static void setBitmapToImageView(String url, ImageView imageView) {
		new DownImage(imageView).execute(url);
	}

	/*** 获得一张图片,从三个地方获取,首先是内存缓存,然后是文件缓存,最后从网络获取 ***/
	public static Bitmap getBitmap(String url, Context _context) {
		ImageMemoryCache memoryCache = new ImageMemoryCache(_context);
		ImageFileCache fileCache = new ImageFileCache();
		// 从内存缓存中获取图片
		Bitmap result = null;//memoryCache.getBitmapFromCache(url);
		if (result == null) {
			// 文件缓存中获取
			result = fileCache.getImage(url);
			if (result == null) {
				// 从网络获取
				result = ImageGetFromHttp.downloadBitmap(url);
				if (result != null) {
					fileCache.saveBitmap(result, url);
					memoryCache.addBitmapToCache(url, result);
				}
			} else {
				// 添加到内存缓存
				memoryCache.addBitmapToCache(url, result);
			}
		}
		return result;
	}
}
