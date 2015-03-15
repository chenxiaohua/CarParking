package com.car.carparking.view;

import com.car.carparking.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends RelativeLayout implements View.OnClickListener{
    public static final int TITLEVIEW_ID_UNKNOWN = -1;
    public static final int TITLEVIEW_ID_MENU = 0;
    public static final int TITLEVIEW_ID_CART = 1;
	private Context mContext;
	private View mView;
	private TextView mTitle;
	private ImageView mMenu;
    private OnClickListener mListener;
	
	public TitleView( Context context, AttributeSet attrs )	{
		this( context, attrs, 0 );
	}
	public TitleView(Context context, AttributeSet attrs, int defStyle ){
		super( context, attrs, defStyle );    
		mContext = context;
		mView= this.inflate(context, R.layout.titleview, this);
	}
	@Override
	protected void onFinishInflate(){
		mTitle =(TextView)mView.findViewById(R.id.title);
		mMenu = (ImageView)mView.findViewById(R.id.menu);
	}
	public void setMenuVisible(int visible){
		if(mMenu!=null){
			mMenu.setVisibility(visible);
		}
	}
	public void setMenuBackgroundResource(int resid){
		if(mMenu!=null){
			mMenu.setBackgroundResource(resid);
		}
	}
	public void setMenuBackground(Drawable drawable){
		if(mMenu!=null){
			mMenu.setBackgroundDrawable(drawable);
		}
	}
	public void setMenuBackgroundColor(int color){
		if(mMenu!=null){
			mMenu.setBackgroundColor(color);
		}
	}
	public void setTitle(String title){
		if(mTitle!=null){
			mTitle.setText(title);
		}
	}
	public void setTitle(int title){
		if(mTitle!=null){
			mTitle.setText(mContext.getText(title));
		}
	}
    public void setTitleSize(float size){
        if(mTitle!=null){
            mTitle.setTextSize(size);
        }
    }
	public void setTitleVisible(int visible){
		if(mTitle!=null){
			mTitle.setVisibility(visible);
		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
        if (mListener!=null){
            mListener.onClick(v);
        }
	}  

    public void registerOnClickListener (OnClickListener listener) {
        mListener = listener;
    }

    public int getClickViewType(View v) {
        return TITLEVIEW_ID_UNKNOWN;
    }
}
