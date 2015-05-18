package com.jeffinbaocv.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainTaskAdapter extends ArrayAdapter<String> {
	
	private int resourceId;
	
	public MainTaskAdapter(Context context,int textViewResourceId,List<String> objects){
		super(context,textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	
	public View getView(int position,View convertView,ViewGroup parent){
		String maintask=getItem(position);
		View view;
		ViewHolder viewHolder;
		
		if(convertView==null){
			view=LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.textViewMaintask=(TextView)view.findViewById(R.id.textview_maintask);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}
		
		viewHolder.textViewMaintask.setText(maintask);
		return view;
		
	}
	
	class ViewHolder{
		TextView textViewMaintask;
	}
	

}
