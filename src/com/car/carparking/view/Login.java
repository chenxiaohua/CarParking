package com.car.carparking.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.content.Intent;

import com.car.carparking.R;
import com.car.carparking.module.AccountManager;

public class Login extends Activity implements View.OnClickListener{
    /**
     * Called when the activity is first created.
     */
	private ImageButton mLoginButton;
	private EditText mUserText;
	private EditText mPwdText;
	static private int INVALID_NULL = -1;
	static private int RET_OK = 0;
	static private int INVALID_PWD = 1;
	static private int INVALID_USER =2;
	private TitleView mTitleView;
	
	private ViewPager mViewPager; 
	private List<ImageView> mImageViews; 

	private int[] mImageResId;
	private List<View> mDots; 

	private int mCurrentItem = 0;
	
	private ScheduledExecutorService mScheduledExecutorService;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			mViewPager.setCurrentItem(mCurrentItem);
		};
	};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mImageResId = new int[] { R.drawable.a, R.drawable.b,R.drawable.a, R.drawable.b,R.drawable.a};

		mImageViews = new ArrayList<ImageView>();

		for (int i = 0; i < mImageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(mImageResId[i]);
			//imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setScaleType(ScaleType.FIT_XY);
			mImageViews.add(imageView);
		}

		mDots = new ArrayList<View>();
		mDots.add(findViewById(R.id.v_dot0));
		mDots.add(findViewById(R.id.v_dot1));
		mDots.add(findViewById(R.id.v_dot2));
		mDots.add(findViewById(R.id.v_dot3));
		mDots.add(findViewById(R.id.v_dot4));

		mViewPager = (ViewPager) findViewById(R.id.vp);
		mViewPager.setAdapter(new MyAdapter());
		mViewPager.setOnPageChangeListener(new MyPageChangeListener());
		
        mLoginButton = (ImageButton)findViewById(R.id.logimgButton);
        mLoginButton.setOnClickListener( this);
        mUserText = (EditText)findViewById(R.id.username);
        mPwdText = (EditText)findViewById(R.id.password);
        mTitleView = (TitleView)findViewById(R.id.titleview);
    }
    private class ScrollTask implements Runnable {

		public void run() {
			synchronized (mViewPager) {
				//mCurrentItem = (mCurrentItem + 1) % mImageViews.size();
				mCurrentItem++;
				mHandler.obtainMessage().sendToTarget(); 
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			mCurrentItem = position;
			position = position % mImageViews.size();
			//mCurrentItem = position;
			mDots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			mDots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}
	}

	private class MyAdapter extends PagerAdapter {
		@Override
		public int getCount() {
			//return mImageResId.length;
			return Integer.MAX_VALUE;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
            int index = arg1 % mImageViews.size();
            ImageView v = mImageViews.get(index);
            if ( index%2 == 0 ) {
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                    }
                });
            }else{
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //
                    }
                });
            }
			((ViewPager) arg0).addView(v);
            return v;
			//return mImageViews.get(arg1% mImageViews.size());
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mImageViews.get(arg1%mImageViews.size()));
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

    
    @Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
    	if(view==mLoginButton){ 
    		int ret = checkLoginInfo();
    		if(ret==INVALID_NULL){
    			Toast.makeText(getApplicationContext(), "username and password can't be empty", Toast.LENGTH_LONG).show();
    	    }else if(ret==RET_OK){
                AccountManager am = AccountManager.getInstance(this);
                Intent intent = new Intent();
                if (am.getCurrentAccount().isAdmin()) {
                    intent.setClassName("com.car.carparking", "com.car.carparking.view.AdminHomeView");
                }else {
                    intent.putExtra("location", am.getCurrentAccount().getLocation());
                    intent.setClassName("com.car.carparking", "com.car.carparking.view.CarListView");
                }
                startActivity(intent);
                Login.this.finish();
    		}else if(ret==INVALID_PWD){
    			Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG).show();
    		}else{
    			Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_LONG).show();
    		}
    	}
	}
    private int checkLoginInfo() {
        String user = mUserText.getText().toString();
        String pwd = mPwdText.getText().toString();
        if(user==null || pwd==null || user.equals("")|| pwd.equals("")){
            return INVALID_NULL;
        }
        AccountManager am = AccountManager.getInstance(this);
        int ret = am.login(user,pwd);;
        if (ret == AccountManager.LOGIN_SUCCESS) {
            return RET_OK;
        }else if (ret == AccountManager.ERROR_NO_ACCOUNT) {
            return INVALID_USER;
        }else if (ret == AccountManager.ERROR_NAME_PASS_NOTMATCH) {
            return INVALID_PWD;
        }
    	return INVALID_NULL;
    }
	@Override
	protected void onStart() {
		mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		mScheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 10, TimeUnit.SECONDS);
		super.onStart();
	}
    @Override
    protected void onResume() {
        super.onResume();  
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
    	mScheduledExecutorService.shutdown();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
