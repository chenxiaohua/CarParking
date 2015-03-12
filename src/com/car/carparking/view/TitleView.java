package com.car.carparking.view;

import com.car.carparking.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleView extends RelativeLayout implements View.OnClickListener{
    public static final int TITLEVIEW_ID_UNKNOWN = -1;
    public static final int TITLEVIEW_ID_MENU = 0;
    public static final int TITLEVIEW_ID_CART = 1;
	private Context mContext;
	private View mView;
	private TextView mTitle;
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
