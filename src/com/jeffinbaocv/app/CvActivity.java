package com.jeffinbaocv.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class CvActivity extends Activity {
	
	private TextView textViewName;
	private ImageView imageViewForwardLinhai;
	private ImageView imageViewForwardDalian;
	private ImageView imageViewForwardSZNanshan;
	private ImageView imageViewForwardDelhi;
	private ImageView imageViewForwardKathmandu;
	private ImageView imageViewForwardSZLonghua;
	private Button buttonLinhai;
	private Button buttonDalian;
	private Button buttonSZNanshan;
	private Button buttonDelhi;
	private Button buttonKathmandu;
	private Button buttonSZLonghua;
	
	
	public static void actionStart(Context context,String name){
		Intent intent=new Intent(context,CvActivity.class);
		intent.putExtra("cv_name", name);
		context.startActivity(intent);
	}
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cv);
		
		String cvName=getIntent().getStringExtra("cv_name");
		textViewName=(TextView)findViewById(R.id.textview_name);
		textViewName.setText(cvName+"的足迹");
		
		buttonLinhai=(Button)findViewById(R.id.button_1);
		buttonLinhai.setOnClickListener(mOnClickListener);
		buttonDalian=(Button)findViewById(R.id.button_2);
		buttonDalian.setOnClickListener(mOnClickListener);
		buttonSZNanshan=(Button)findViewById(R.id.button_3);
		buttonSZNanshan.setOnClickListener(mOnClickListener);	
		buttonDelhi=(Button)findViewById(R.id.button_4);
		buttonDelhi.setOnClickListener(mOnClickListener);	
		buttonKathmandu=(Button)findViewById(R.id.button_5);
		buttonKathmandu.setOnClickListener(mOnClickListener);
		buttonSZLonghua=(Button)findViewById(R.id.button_6);
		buttonSZLonghua.setOnClickListener(mOnClickListener);
		
		imageViewForwardLinhai=(ImageView)findViewById(R.id.imagebutton_forward_1);
		imageViewForwardLinhai.setOnClickListener(mOnClickListener);
		imageViewForwardDalian=(ImageView)findViewById(R.id.imagebutton_forward_2);
		imageViewForwardDalian.setOnClickListener(mOnClickListener);	
		imageViewForwardSZNanshan=(ImageView)findViewById(R.id.imagebutton_forward_3);
		imageViewForwardSZNanshan.setOnClickListener(mOnClickListener);		
		imageViewForwardDelhi=(ImageView)findViewById(R.id.imagebutton_forward_4);
		imageViewForwardDelhi.setOnClickListener(mOnClickListener);
		imageViewForwardKathmandu=(ImageView)findViewById(R.id.imagebutton_forward_5);
		imageViewForwardKathmandu.setOnClickListener(mOnClickListener);
		imageViewForwardSZLonghua=(ImageView)findViewById(R.id.imagebutton_forward_6);
		imageViewForwardSZLonghua.setOnClickListener(mOnClickListener);
		
	}
	
	private OnClickListener mOnClickListener=new OnClickListener(){
		public void onClick(View v){
			switch(v.getId()){
			case R.id.button_1:
				CvMapActivity.actionStart(CvActivity.this, 0);
				break;
			case R.id.button_2:
				CvMapActivity.actionStart(CvActivity.this, 1);
				break;
			case R.id.button_3:
				CvMapActivity.actionStart(CvActivity.this, 2);
				break;
			case R.id.button_4:
				CvMapActivity.actionStart(CvActivity.this, 3);
				break;
			case R.id.button_5:
				CvMapActivity.actionStart(CvActivity.this, 4);
				break;
			case R.id.button_6:
				CvMapActivity.actionStart(CvActivity.this, 5);
				break;
				
			case R.id.imagebutton_forward_1:
				CvMapActivity.actionStart(CvActivity.this, 0);
				break;
			case R.id.imagebutton_forward_2:
				CvMapActivity.actionStart(CvActivity.this, 1);
				break;
			case R.id.imagebutton_forward_3:
				CvMapActivity.actionStart(CvActivity.this, 2);
				break;
			case R.id.imagebutton_forward_4:
				CvMapActivity.actionStart(CvActivity.this, 3);
				break;
			case R.id.imagebutton_forward_5:
				CvMapActivity.actionStart(CvActivity.this, 4);
				break;
			case R.id.imagebutton_forward_6:
				CvMapActivity.actionStart(CvActivity.this, 5);
				break;
			default:
				break;
			}
		}
	};

}
