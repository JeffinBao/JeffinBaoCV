package com.jeffinbaocv.app;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class CvDetailedContentActivity extends Activity{
	
	private TextView textViewMaintask;
	private TextView textViewPerformance;
	private MyListView listViewMaintask;
	private MyListView listViewPerformance;
	private MyDatabaseHelper dbHelper;
	
	public static void actionStart(Context context,int position){
		Intent intent=new Intent(context,CvDetailedContentActivity.class);
		intent.putExtra("position", position);
		context.startActivity(intent);
	}
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detailed_content);
		
		WindowManager wm=getWindowManager();
		Display d=wm.getDefaultDisplay();
		
		LayoutParams lp=getWindow().getAttributes();
		lp.width=(int)(d.getWidth()*0.88);
		lp.height=(int)(d.getHeight()*0.7);
		lp.y=100;
		getWindow().setGravity(Gravity.CENTER);
		getWindow().setAttributes(lp);
		
		dbHelper=new MyDatabaseHelper(this,"FootStep.db",null,1);
		SQLiteDatabase db=dbHelper.getWritableDatabase();
				
		final int currentPage=getIntent().getIntExtra("position", 0);
		
		String mainTask="";
		String performance="";
		Cursor cursorCurrent=db.query("footstep", null, "id="+currentPage, null, null, null, null);
		if(cursorCurrent.moveToFirst()){
			mainTask=cursorCurrent.getString(cursorCurrent.getColumnIndex("maintask"));	
			performance=cursorCurrent.getString(cursorCurrent.getColumnIndex("performance"));
		}
		cursorCurrent.close();
		
		String[] mainTaskArray=mainTask.split("\\|");
		List<String> mainTaskList=new ArrayList<String>();
		for(String maintask:mainTaskArray){
			mainTaskList.add(maintask);
		}
		
		String[] performanceArray=performance.split("\\|");
		List<String> performanceList=new ArrayList<String>();
		for(String perFormance:performanceArray){
			performanceList.add(perFormance);
		}
		
		textViewMaintask=(TextView)findViewById(R.id.textview_title_maintask);
		if(currentPage!=6){
			textViewMaintask.setText("主要任务");
		}else{
			textViewMaintask.setText("主要任务--苹果序列号查询APP");
		}
		
		
		listViewMaintask=(MyListView)findViewById(R.id.listview_maintask);
		MainTaskAdapter mainTaskAdapter=new MainTaskAdapter(CvDetailedContentActivity.this,R.layout.maintask_display,mainTaskList);
		listViewMaintask.setAdapter(mainTaskAdapter);
		
		textViewPerformance=(TextView)findViewById(R.id.textview_titile_performance);
		textViewPerformance.setText("达成情况");
		
		listViewPerformance=(MyListView)findViewById(R.id.listview_performance);
		PerformanceAdapter performanceAdapter=new PerformanceAdapter(CvDetailedContentActivity.this,R.layout.performance_display,performanceList);
		listViewPerformance.setAdapter(performanceAdapter);
		
	}

}
