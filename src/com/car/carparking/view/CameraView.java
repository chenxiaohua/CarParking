package com.car.carparking.view;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Environment;
import android.os.StatFs;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
 
public class CameraView extends SurfaceView implements SurfaceHolder.Callback, Camera.PictureCallback {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private boolean mAutoFocus;
    private int LARGEST_WIDTH = 0;  
    private int LARGEST_HEIGHT= 0;
    
    public interface Listener {
        public void update();
    }
    private com.car.carparking.view.AutoScanCarPlate.Listener mListener;

    public void setListener(com.car.carparking.view.AutoScanCarPlate.Listener listener) {
        mListener = listener;
    }


    public static int getScreenWidth(Context context){
    	WindowManager man = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    	Display dis = man.getDefaultDisplay();
    	return dis.getWidth();
    }
	public CameraView( Context context, AttributeSet attrs )	{
		this( context, attrs, 0 );
	}
	public CameraView(Context context, AttributeSet attrs, int defStyle ){
		super( context, attrs, defStyle ); 
		LARGEST_WIDTH = getScreenWidth(context);
		LARGEST_HEIGHT = LARGEST_WIDTH;
		mHolder = getHolder();
		mHolder.addCallback(this);

		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

 
    public void surfaceCreated(SurfaceHolder holder) {
    	try {
    		mCamera = Camera.open();
    		
    		Camera.Parameters parameters = mCamera.getParameters();
    		int bestWidth = 0;  
    		int bestHeight = 0;
   		 	parameters.set("rotation", 90); 
   		 	mCamera.setDisplayOrientation(90);
    		List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes(); 
    		if (previewSizes!=null && previewSizes.size() > 1)  {  
    			Iterator<Camera.Size> cei = previewSizes.iterator();  
    			while (cei.hasNext())  {  
    				Camera.Size aSize = cei.next();
    				if (aSize.width > bestWidth && aSize.width <= LARGEST_WIDTH  
    				        		    && aSize.height > bestHeight  
    				        		    && aSize.height <= LARGEST_HEIGHT) {  
    				     bestWidth = aSize.width;  
    				     bestHeight = aSize.height;  
    				}  
    			}
    			if (bestHeight != 0 && bestWidth != 0) {   
                    parameters.setPreviewSize(bestWidth, bestHeight);  
                    parameters.setPictureSize(bestWidth, bestHeight);
                 }  
             mCamera.setParameters(parameters); 
             mCamera.setPreviewDisplay(holder);
    		}
        } catch (Exception e) {
        	mCamera.release();  
        }
    }
 
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        
        Size previewSize=parameters.getPreviewSize();
        setLayoutParams(new LinearLayout.LayoutParams(width, (int)(width*previewSize.width/previewSize.height)));
        //parameters.setPreviewSize(width, height);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }
 
    public void surfaceDestroyed(SurfaceHolder holder) {
    	mCamera.setPreviewCallback(null);
    	mCamera.stopPreview();
    	mCamera.release();
    	mCamera =null;
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
    		mCamera.autoFocus(null);
    		mAutoFocus =true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP && mAutoFocus ==true) {
        	mCamera.takePicture(null, null, this);
        	mAutoFocus =false;
        }
        return true;
    }
 
    public void onPictureTaken(byte[] data, Camera camera) {
    	try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/car.jpg";
            data2file(data, path);
        } catch (Exception e) {
        }
        camera.startPreview();
    	mListener.update();
    }
 
    private void data2file(byte[] w, String fileName) throws Exception {
        FileOutputStream out =null;
        try{ 
                    String storage = Environment.getExternalStorageDirectory().getAbsolutePath();  
                    StatFs fs = new StatFs(storage);
                    long available = (long)fs.getAvailableBlocks()*(long)fs.getBlockSize();
                    if(available<w.length){ 
                    	Toast.makeText(getContext(), "Storage is not available!!!", Toast.LENGTH_LONG).show();
                        return;  
                    }  
                    File file = new File(fileName);  
                    if(!file.exists())  
                        file.createNewFile();  
                    FileOutputStream fos = new FileOutputStream(file);  
                    fos.write(w);  
                    fos.close();   
        }catch(Exception e){  
            e.printStackTrace();  
            if (out !=null)
                out.close();
            throw e; 
        }
    }
 
}