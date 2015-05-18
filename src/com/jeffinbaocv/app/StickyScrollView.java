package com.jeffinbaocv.app;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class StickyScrollView extends ScrollView {
	private static final String STICKY="sticky";
	private View mCurrentStickyView;
	private List<View> mStickyViews;
	private int mStickyViewTopOffset;
	private boolean redirectTouchToStickyView;
	
	private Runnable mInvalidateRunnable=new Runnable(){
		public void run(){
			if(mCurrentStickyView!=null){
				int left=mCurrentStickyView.getLeft();
				int top=mCurrentStickyView.getTop();
				int right=mCurrentStickyView.getRight();
				int bottom=getScrollY()+(mCurrentStickyView.getHeight()+mStickyViewTopOffset);
				
				invalidate(left,top,right,bottom);
			}
			postDelayed(this,16);
		}
	};
	
	
	public StickyScrollView(Context context,AttributeSet attrs){
		this(context,attrs,0);
	}
	
	public StickyScrollView(Context context,AttributeSet attrs,int defStyle){
		super(context,attrs,defStyle);
		mStickyViews=new LinkedList<View>();
	}
	
	
	/**
	 * @param viewGroup
	 * find all views contain Tag "sticky"
	 */
	private void findViewByStickyTag(ViewGroup viewGroup){
		int childCount=((ViewGroup)viewGroup).getChildCount();
		for(int i=0;i<childCount;i++){
			View child=viewGroup.getChildAt(i);
			
			if(getStringTagForView(child).contains(STICKY)){
				mStickyViews.add(child);
			}
			
			if(child instanceof ViewGroup){
				findViewByStickyTag((ViewGroup)child);
			}
					
		}
	}
	
	private String getStringTagForView(View v){
		Object tag=v.getTag();
		return String.valueOf(tag);
	}
	
	protected void onLayout(boolean changed,int l,int t,int r,int b){
		super.onLayout(changed, l, t, r, b);
		if(changed){
			findViewByStickyTag((ViewGroup)getChildAt(0));
		}
		showStickyView();
	}
	
	protected void onScrollChanged(int l,int t,int oldl,int oldt){
		super.onScrollChanged(l, t, oldl, oldt);
		showStickyView();
	}
	
	private void showStickyView(){
		View currentStickyView=null;
		View nextStickyView=null;
		
		for(View v:mStickyViews){
			int topOffset=v.getTop()-getScrollY();
			
			if(topOffset<=0){
				if(currentStickyView==null || topOffset>currentStickyView.getTop()-getScrollY()){
					currentStickyView=v;
				}
			}else{
				if(nextStickyView==null || topOffset<nextStickyView.getTop()-getScrollY()){
					nextStickyView=v;
				}
			}
		}
		
		if(currentStickyView!=null){
			mStickyViewTopOffset= nextStickyView==null ? 0:Math.min(0,nextStickyView.getTop()-getScrollY()-currentStickyView.getHeight());
			mCurrentStickyView=currentStickyView;
			post(mInvalidateRunnable);
		}else{
			mCurrentStickyView=null;
			removeCallbacks(mInvalidateRunnable);
		}
	}
	
	protected void dispatchDraw(Canvas canvas){
		super.dispatchDraw(canvas);
		
		if(mCurrentStickyView!=null){
			canvas.save();
			canvas.translate(0,getScrollY()+mStickyViewTopOffset);
			
			canvas.clipRect(0, mStickyViewTopOffset, mCurrentStickyView.getWidth(), mCurrentStickyView.getHeight());
			mCurrentStickyView.draw(canvas);
			
			canvas.restore();
		}		
	}
	
	public boolean dispatchTouchEvent(MotionEvent ev){
		if(ev.getAction()==MotionEvent.ACTION_DOWN){
			redirectTouchToStickyView=true;
		}
		
		if(redirectTouchToStickyView){
			redirectTouchToStickyView=mCurrentStickyView!=null;
			
			if(redirectTouchToStickyView){
				redirectTouchToStickyView=
						ev.getY()<=(mCurrentStickyView.getHeight()+mStickyViewTopOffset)
					  &&ev.getX()>=mCurrentStickyView.getLeft()
					  &&ev.getX()<=mCurrentStickyView.getRight();
			}
		}
		
		if (redirectTouchToStickyView) {
			ev.offsetLocation(0, -1 * ((getScrollY() + mStickyViewTopOffset) - mCurrentStickyView.getTop()));
		}
		return super.dispatchTouchEvent(ev);
		
	}
	
	private boolean hasNotDoneActionDown=true;
	
	public boolean onTouchEvent(MotionEvent ev){
		if(redirectTouchToStickyView){
			ev.offsetLocation(0, (getScrollY()+mStickyViewTopOffset)-mCurrentStickyView.getTop());
		}
		
		if(ev.getAction()==MotionEvent.ACTION_DOWN){
			hasNotDoneActionDown=false;
		}
		
		if(hasNotDoneActionDown){
			MotionEvent down=MotionEvent.obtain(ev);
			down.setAction(MotionEvent.ACTION_DOWN);
			super.onTouchEvent(ev);
			hasNotDoneActionDown=false;
		}
		
		if(ev.getAction()==MotionEvent.ACTION_UP || ev.getAction()==MotionEvent.ACTION_CANCEL){
			hasNotDoneActionDown=true;
		}
		
		return super.onTouchEvent(ev);
		
	}
	
	
	
}
