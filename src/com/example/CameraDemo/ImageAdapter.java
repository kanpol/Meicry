package com.example.CameraDemo;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Activity activity;
    private final Integer[] mThumbIds;

    public ImageAdapter(Activity activity, Integer[] mThumbIds) {
        this.activity = activity;
        this.mThumbIds = mThumbIds;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) activity
	            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	        View gridView;

	        if (convertView == null) {

	            gridView = new View(activity);

	            // get layout from mobile.xml
	            gridView = inflater.inflate(R.layout.item, null);

	            // set image based on selected text
	            ImageView imageView = (ImageView) gridView
	                    .findViewById(R.id.grid_item_image);


	            imageView.setImageResource(mThumbIds[position]);
	            

	        } else {
	            gridView = (View) convertView;
	        }

	        return gridView;
	}

}
