package com.jeffinbaocv.app;

import java.util.List;

import com.jeffinbaocv.app.MainTaskAdapter.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PerformanceAdapter extends ArrayAdapter<String> {
private int resourceId;
	
	public PerformanceAdapter(Context context,int textViewResourceId,List<String> objects){
		super(context,textViewResourceId,objects);
		resourceId=textViewResourceId;
	}
	
	public View getView(int position,View convertView,ViewGroup parent){
		String performance=getItem(position);
		View view;
		ViewHolder viewHolder;
		
		if(convertView==null){
			view=LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder=new ViewHolder();
			viewHolder.textViewPerformance=(TextView)view.findViewById(R.id.textview_performance);
			view.setTag(viewHolder);
		}else{
			view=convertView;
			viewHolder=(ViewHolder)view.getTag();
		}
		
		viewHolder.textViewPerformance.setText(performance);
		return view;
		
	}
	
	class ViewHolder{
		TextView textViewPerformance;
	}
}
