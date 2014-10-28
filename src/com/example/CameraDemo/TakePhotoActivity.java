package com.example.CameraDemo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.Inflater;

public class TakePhotoActivity extends Activity {

	private final static String TAG = "CameraActivity";
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private File picture;
	private Button btnSave;
    private int cameraPosition = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.camera_preview);
		setupViews();

        HorizontalListView hlv = (HorizontalListView) findViewById(R.id.hlv_mask_list);
        hlv.setAdapter(maskAdapter);
/*        HorizontalListView thlv = (HorizontalListView) findViewById(R.id.hlv_template_list);
        thlv.setAdapter(baseAdapter);*/
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
            if (camera != null) {
                camera.stopPreview();// stop preview
                camera.release(); // Release camera resources
                camera = null;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void setupViews() {
		surfaceView = (SurfaceView) findViewById(R.id.takePicturePreview);
		surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(surfaceCallback);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		btnSave = (Button) findViewById(R.id.take);

		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				takePic();
			}
		});

        Button btnChange = (Button) findViewById(R.id.change);
        btnChange.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if (cameraPosition == 1) {
                        //现在是后置，变更为前置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            try {
                                camera.setPreviewDisplay(surfaceHolder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setPictureFormat(PixelFormat.JPEG);
                            camera.setDisplayOrientation(90);
                            camera.setParameters(parameters); // Setting camera parameters
                            camera.startPreview();//开始预览
                            cameraPosition = 0;
                            break;
                        }
                    } else {
                        //现在是前置， 变更为后置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            try {
                                camera.setPreviewDisplay(surfaceHolder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Camera.Parameters parameters = camera.getParameters();
                            parameters.setPictureFormat(PixelFormat.JPEG);
                            camera.setDisplayOrientation(90);

                            camera.setParameters(parameters); // Setting camera parameters
                            camera.startPreview();//开始预览
                            cameraPosition = 1;
                            break;
                        }
                    }
                }
            }
        });
	}

	private void takePic() {
//		camera.stopPreview();// stop the preview
		try {
			camera.takePicture(null, null, pictureCallback); // picture
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

	// Photo call back
	Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
		// @Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// new SavePictureTask().execute(data);

			/* onPictureTaken传入的第一个参数即为相片的byte */
			
			/*picture = new File(Environment.getExternalStorageDirectory() + "/"
                    + System.currentTimeMillis() + ".jpg");

			try {

				FileOutputStream fos=new FileOutputStream(picture);
	            fos.write(data); 
	            fos.close(); 
				Toast.makeText(TakePhotoActivity.this, "拍照结束,请查看" + picture.toString(), Toast.LENGTH_SHORT).show();

			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}*/
//			camera.startPreview();
 /*           byte[] newdata = new byte[data.length];
            System.arraycopy(data, 0, newdata, 0, data.length);*/
            Intent intent = new Intent(TakePhotoActivity.this, PhotoPreview.class);
            //intent.putExtra("photo_data", newdata);
            TakePhotoActivity.this.startActivity(intent);
		}
	};

	// SurfaceHodler Callback handle to open the camera, off camera and photo
	// size changes
	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		public void surfaceCreated(SurfaceHolder holder) {
			Log.i(TAG, "surfaceCallback====");
			camera = Camera.open(); // Turn on the camera
			try {
				if(holder!=null)
				camera.setPreviewDisplay(holder); // Set Preview
			} catch (IOException e) {
				e.printStackTrace();
				camera.release();// release camera
				camera = null;
			}
			
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Log.i(TAG, "====surfaceChanged");
			Camera.Parameters parameters = camera.getParameters();
			parameters.setPictureFormat(PixelFormat.JPEG);
            camera.setDisplayOrientation(90);

            if (parameters.getSupportedFocusModes().contains(
                    Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        Log.i("xxxxxxxxxxxxxxx", String.valueOf(success));
                    }
                });
            }

            if (parameters.getSupportedFlashModes().contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            }
			// parameters.set("rotation", 180); // Arbitrary rotation
			// camera.setDisplayOrientation(0);
//			parameters.setPreviewSize(400, 300); // Set Photo Size
			camera.setParameters(parameters); // Setting camera parameters
			camera.startPreview(); // Start Preview
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.i(TAG, "====surfaceDestroyed");
//			try {
                if (camera != null) {
                    camera.setPreviewCallback(null);
                    camera.stopPreview();// stop preview
                    camera.release(); // Release camera resources
                    camera = null;
                }
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
			
		}
	};

    private BaseAdapter maskAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 10;
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
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.photo_mask_list_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.iv_mask);
            return view;
        }
    };

 /*   private BaseAdapter baseAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 10;
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
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.photo_mask_list_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.iv_mask);
            return view;
        }
    };*/
}