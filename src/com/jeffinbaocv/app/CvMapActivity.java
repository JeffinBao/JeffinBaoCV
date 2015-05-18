package com.jeffinbaocv.app;

import java.util.ArrayList;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.CancelableCallback;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class CvMapActivity extends Activity {
	
	private MapView mapView;
	private AMap aMap;
	
	private ImageButton imageButtonLeft;
	private ImageButton imageButtonRight;
	private ViewPager viewPager;
	private PageAdapter pageAdapter;
	private Marker marker;
	
	private int currentPageId=0;
	private int selectPage=0;
	private static String location="";
	
	private static MyDatabaseHelper dbHelper;
	
	public static void actionStart(Context context,int position){
		Intent intent=new Intent(context,CvMapActivity.class);
		intent.putExtra("position", position);
		context.startActivity(intent);
	}
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.avtivity_cvmap);
		dbHelper=new MyDatabaseHelper(this,"FootStep.db",null,1);
		
		mapView=(MapView)findViewById(R.id.amap_view);
		mapView.onCreate(savedInstanceState);
		aMap=mapView.getMap();
		
		/**
		final ArrayList<BitmapDescriptor> gifList=new ArrayList<BitmapDescriptor>();
		gifList.add(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_purple));
		gifList.add(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_blue));
		gifList.add(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_orange));
		*/
		
		/**
		 * initialize aMap camera position according to 'selectPage' 
		 */
		selectPage=getIntent().getIntExtra("position", 0);
		final int currentPosition=selectPage+1;
		double lat=0f;
		double lng=0f;
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		Cursor cursorCurrent=db.query("footstep", null, "id="+currentPosition, null, null, null, null);
		if(cursorCurrent.moveToFirst()){
			lat=cursorCurrent.getDouble(cursorCurrent.getColumnIndex("lat"));
			lng=cursorCurrent.getDouble(cursorCurrent.getColumnIndex("lng"));
		}
		cursorCurrent.close();

        marker=aMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
        		.anchor(0.5f, 0.8f));
		aMap.moveCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(new LatLng(34.3492,98.0243), 10f, 0f, 0)));
		aMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(lat,lng),9.9f)),1000,null);
		aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				CvDetailedContentActivity.actionStart(CvMapActivity.this,currentPosition);
				return false;
			}
		});

		/**
		 * viewPager settings,the most important part is setOnPageChangeListener to 
		 * record the page change state in order to update aMap camera latitude and longitude position
		 */
		pageAdapter=new PageAdapter(getFragmentManager());
		viewPager=(ViewPager)findViewById(R.id.view_pager);
		
		imageButtonLeft=(ImageButton)findViewById(R.id.imagebutton_left);
		imageButtonLeft.setOnClickListener(mOnClickListener);
		imageButtonRight=(ImageButton)findViewById(R.id.imagebutton_right);
		imageButtonRight.setOnClickListener(mOnClickListener);
		
		if(selectPage==0){
			imageButtonLeft.setVisibility(View.INVISIBLE);
		}else if(selectPage==5){
			imageButtonRight.setVisibility(View.INVISIBLE);
		}else{
			imageButtonLeft.setVisibility(View.VISIBLE);
			imageButtonRight.setVisibility(View.VISIBLE);
		}
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				currentPageId=arg0;
				if(currentPageId==0){
					imageButtonLeft.setVisibility(View.INVISIBLE);
				}else if(currentPageId==5){
					imageButtonRight.setVisibility(View.INVISIBLE);
				}else{
					imageButtonLeft.setVisibility(View.VISIBLE);
					imageButtonRight.setVisibility(View.VISIBLE);
				}
				
				final int currentPosition=viewPager.getCurrentItem()+1;
				double lat=0f;
	    		double lng=0f;
	    		SQLiteDatabase db=dbHelper.getWritableDatabase();
	    		Cursor cursorCurrent=db.query("footstep", null, "id="+currentPosition, null, null, null, null);
	    		if(cursorCurrent.moveToFirst()){
	    			lat=cursorCurrent.getDouble(cursorCurrent.getColumnIndex("lat"));
	    			lng=cursorCurrent.getDouble(cursorCurrent.getColumnIndex("lng"));
	    		}
	    		cursorCurrent.close();

	    		marker.destroy();
	            marker=aMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng))
	            		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
	            		.anchor(0.5f, 0.8f));

	    		aMap.animateCamera(CameraUpdateFactory
	    				.newLatLngZoom(new LatLng(lat,lng),9.9f),1500,null);
	    		aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
					
					@Override
					public boolean onMarkerClick(Marker marker) {
						// TODO Auto-generated method stub
						CvDetailedContentActivity.actionStart(CvMapActivity.this, currentPosition);
						return false;
					}
				});
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		viewPager.setAdapter(pageAdapter);
		viewPager.setCurrentItem(selectPage);

	}
	
	private OnClickListener mOnClickListener=new OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			
			case R.id.imagebutton_left:
				if(currentPageId!=0){
					currentPageId--;
					viewPager.setCurrentItem(currentPageId,true);
				}
				break;
			case R.id.imagebutton_right:
				if(currentPageId!=5){
					currentPageId++;
					viewPager.setCurrentItem(currentPageId, true);
				}
				break;
				
			}
		}
	};

    public static class PlaceHolderFragment extends Fragment{
    	private static final String EXTRA_POSITION="EXTRA_POSITION";
    	
    	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
    		final int position = getArguments().getInt(EXTRA_POSITION);

    		SQLiteDatabase db=dbHelper.getWritableDatabase();
    		Cursor cursor=db.query("footstep", null, "id="+position, null, null, null, null);
    		if(cursor.moveToFirst()){
    			location=cursor.getString(cursor.getColumnIndex("name"));
    		}
    		cursor.close();
    		
    		final TextView textViewPosition=(TextView)inflater.inflate(R.layout.fragment_cvmap, container, false);
    		textViewPosition.setText(location);
    		textViewPosition.setBackgroundColor(Color.parseColor("#df673ab7"));
    		   		
    		return textViewPosition;
    	}
    }
    
    private static final class PageAdapter extends FragmentStatePagerAdapter{
    	public PageAdapter(FragmentManager fragmentManager){
    		super(fragmentManager);
    	}
    	
    	public Fragment getItem(int position){
    		final Bundle bundle=new Bundle();
    		bundle.putInt(PlaceHolderFragment.EXTRA_POSITION, position+1);
    		
    		final PlaceHolderFragment fragment=new PlaceHolderFragment();
    		fragment.setArguments(bundle);
    		
    		return fragment;	
    	}
    	
    	public int getCount(){
    		return 6;
    	}
    }
    
    protected void onResume(){
    	super.onResume();
    	mapView.onResume();
    }
    
    protected void onPause(){
    	super.onPause();
    	mapView.onPause();
    }
    
    protected void onDestroy(){
    	super.onDestroy();
    	mapView.onDestroy();
    }
    
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

}
