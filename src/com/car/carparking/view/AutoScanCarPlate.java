package com.car.carparking.view;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

import com.car.carparking.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;


public class AutoScanCarPlate extends Activity implements View.OnClickListener{
	CameraView mCameraView; 
	private String mPath = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		new Handler().postDelayed(new Runnable() {
		@Override
		public void run(){
	        try{
	        	if(!fileIsExists(mPath+"/svm.xml")){
	        		
					copyDataToSD("must/svm.xml",mPath+"/svm.xml");

	            }
	        	if(!fileIsExists(mPath+"/ann.xml")){
	            	copyDataToSD("must/ann.xml",mPath+"/ann.xml");
	            }
	        }catch(IOException ex){
	        	
	        }
		}
	}, 0);
        initView();

    }
    private void copyDataToSD(String strInputFileName,String strOutFileName) throws IOException 
    { 	
    	File file = new File(strOutFileName);   
        file.createNewFile(); 
        InputStream myInput;  
        OutputStream myOutput = new FileOutputStream(strOutFileName);  
        myInput = this.getAssets().open(strInputFileName); 
        byte[] buffer = new byte[4096];  
        int length = myInput.read(buffer);
        while(length > 0){
            myOutput.write(buffer, 0, length); 
            length = myInput.read(buffer);
        }
        
        myOutput.flush();  
        myInput.close();  
        myOutput.close();        
    }  
    private boolean fileIsExists(String name){
        try{
                File f=new File(name);
                if(!f.exists()){
                    return false;
                }
                
        }catch (Exception e) {
                // TODO: handle exception
                return false;
        }
        return true;
    }
    public interface Listener {
        public void update();
    }
    private void initView(){
        //Intent intent = getIntent();  
    	setContentView(R.layout.cameraview);  
    	 
    	mCameraView = (CameraView)findViewById(R.id.cameraview); 
    	mCameraView.setListener(
    	  new Listener() {
            public void update() {
        		String svmpath = mPath+"/svm.xml";
        		String annpath = mPath+"/ann.xml";
        		String imgpath = mPath+"/car.jpg";
        		byte[] resultByte = CarPlateDetection.ImageProc(imgpath,svmpath,annpath);
        		if(resultByte==null){
        			Intent intent = new Intent(AutoScanCarPlate.this, CarListView.class);
             	   	setResult(RESULT_CANCELED,intent);
             	   	AutoScanCarPlate.this.finish();
        		}
        		try{
            		String result = new String(resultByte,"GBK");
            		Intent intent = new Intent(AutoScanCarPlate.this, CarListView.class);
             	   	intent.putExtra("name", result);
             	   	setResult(RESULT_OK,intent);
             	   	AutoScanCarPlate.this.finish();
        		}catch(UnsupportedEncodingException e){
        			e.printStackTrace();
        			
        		}
            }
        });
    }
    
    @Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
	}
    @Override
    protected void onResume() {
        super.onResume(); 
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_9, this,
				mLoaderCallback);
    }
    
	private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS: {				
				System.loadLibrary("imageproc");
			}
				break;
			default: {
				super.onManagerConnected(status);
			}
				break;
			}
		}
	};

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        initView();
    }
}
