package com.car.carparking.view;

import java.util.ArrayList;
import java.util.Map;

import com.car.carparking.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter {
	CarListView mContext;
	ArrayList<Map<String,Object>> mList;
	int layout;
	
	public ItemListAdapter(CarListView context, ArrayList<Map<String,Object>> list ,int layout){
		super();
		this.mContext = context;
		this.mList = list;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
	@Override
	public View getView(final int index, View view, ViewGroup group) {
		View v=LayoutInflater.from(mContext).inflate(layout, null);
		TextView name=(TextView) v.findViewById(R.id.item_name);
		name.setText(mList.get(index).get("name").toString());
		TextView datetime=(TextView) v.findViewById(R.id.item_datetime);
		datetime.setText(mList.get(index).get("datetime").toString());
		return v;
	}

}