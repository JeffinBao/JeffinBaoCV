package com.jeffinbaocv.app;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyListView extends ListView {
	
	public MyListView(Context mContext){
		super(mContext);
	}
	
	public MyListView(Context mContext,AttributeSet attrs){
		super(mContext,attrs);
	}
	
	public MyListView(Context mContext,AttributeSet attrs,int defStyle){
		super(mContext,attrs,defStyle);
	}
	
	protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
		int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
		
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
