package com.jeffinbaocv.app;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.ArcOptions;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private MapView mapView;
	private static AMap aMap;
	private EditText editTextName;
	private Button buttonSearch;
	private Button buttonStart;
	
	private MyDatabaseHelper dbHelper;
	
	private long exitTime=0;
	
	private void exitApp(){
		if(System.currentTimeMillis()-exitTime>2000){
			Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
			exitTime=System.currentTimeMillis();
		}else{
			finish();
		}
	}
	
	public void onBackPressed(){
		exitApp();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        dbHelper=new MyDatabaseHelper(this,"FootStep.db",null,1);
        dbHelper.getWritableDatabase();
        
        mapView=(MapView)findViewById(R.id.amap_view);
        mapView.onCreate(savedInstanceState);
        aMap=mapView.getMap();

		aMap.moveCamera(CameraUpdateFactory
				.newCameraPosition(new CameraPosition(new LatLng(34.3492,100.0243), 4f, 0f, 0)));
        
        editTextName=(EditText)findViewById(R.id.edittext_name);
        buttonStart=(Button)findViewById(R.id.button_start);
        buttonStart.setVisibility(View.INVISIBLE);
        buttonSearch=(Button)findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String cvName=editTextName.getText().toString();
				if(cvName.equals("包建峰")){
					
					setUpMap();
					
					InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

					buttonStart.setVisibility(View.VISIBLE);
					buttonStart.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							CvActivity.actionStart(MainActivity.this, cvName);
						}
					});
					
					SQLiteDatabase db=dbHelper.getWritableDatabase();
					Cursor cursor=db.query("footstep",null,null,null,null,null,null);
					if(cursor.getCount()==0){
						ContentValues values=new ContentValues();
						//insert first set data
						values.put("name", "中国 · 浙江 · 台州 · 临海");
						values.put("maintask", "上台州最好的初中和高中");
						values.put("performance", "小学：临海市新时代外国语小学|初中：台州市初级中学(最好的初中)|高中：浙江省台州中学(最好的高中)");
						values.put("lat", UtilConstants.LINHAI.latitude);
						values.put("lng", UtilConstants.LINHAI.longitude);
						db.insert("footstep", null, values);
						values.clear();
						//insert second set data
						values.put("name", "中国 · 辽宁 · 大连");
						values.put("maintask", "以尽量优异的成绩毕业，锻炼个人学习的能力；并尽量多拿奖学金，改善穷学生的日常生活.|提高英语水平，以便了解外面的世界与接触另外一种可能性.|社会实习，提早感受工作氛围，并能自己赚钱出去旅游.");
						values.put("performance", "学位绩点:3.92/5；专业排名:13/128；二等奖学金:2次；三等奖学金:1次，奖学金总额5000元.|雅思(IELTS):6.5(听力:8.5,阅读:7)；英语六级(CET-6):581(阅读单项:满分)；BEC商务英语:中级.|IBM大连：商务支持实习生。主要工作内容：在SAP系统环境下将IBM销售部门的各种订单制作成不同的正式单据并保证高准确率.完成IBM实习后，手握2000元实习工资完成了第一次长途旅行，线路：大连--天津--沈阳--哈尔滨--漠河，坐着绿皮火车一路向北的体验希望以后还能再经历一次.");
						values.put("lat", UtilConstants.DALIAN.latitude);
						values.put("lng", UtilConstants.DALIAN.longitude);
						db.insert("footstep", null, values);
						values.clear();
						//insert third set data
						values.put("name", "中国 · 广东 · 深圳 · 南山");
						values.put("maintask", "实习期深圳地区直销工作1个月，海能达龙岗工厂产线实习1个月.|印度区域技术支持工程师，负责渠道经销商日常技术支持并且提供具体项目的方案报价.");
						values.put("performance","直销工作中陌生拜访客户150家以上，售出4台对讲机，总金额2479元。体验了销售工作的艰辛，也感受到通过和队友合作卖出东西的喜悦.|龙岗工厂实习中体验了电子产品制造的各个环节，从SMT贴片加工到整机组装再到包装出货.|解决4个主要经销商的日常技术问题，主要涉及对讲机终端、基站、系统软件的售前介绍以及售后维护.");
						values.put("lat", UtilConstants.SHENZHENNANSHAN.latitude);
						values.put("lng", UtilConstants.SHENZHENNANSHAN.longitude);
						db.insert("footstep", null, values);
						values.clear();
						//insert fourth set data
						values.put("name", "印度 · 斋浦尔-德里等");
						values.put("maintask", "印度斋浦尔地铁项目经理，负责海能达无线系统部分的项目交付.|印度地铁解决方案经理，负责地铁无线系统投标方案报价制作以及技术支持；分析印度地铁行业情况.");
						values.put("performance", "与海能达研发同事一起，经过总共3个月的现场调试，完成无线系统部分：车载台、列车调度系统、录音系统和网管系统的开发调试.|无线系统作为其它各个系统的传输通路，在交付过程中协调了信号与系统(Signaling)、车辆(Rolling Stock)、通信系统(Telecommunication)、广播系统(Public Address,Passenger Information System)与海能达无线系统进行系统之间的对接与测试工作.|熟悉了地铁运行过程中各个系统配合工作的流程，锻炼了与国际团队合作完成系统联调的能力，交付过程中从同事身上感受到软件开发给他们带来的乐趣，并最终影响我作出转行做软件工程师的决定.|完成3个地铁项目投标技术方案制作，组织研发同事确认项目技术细节，提供设备清单(BOM)并报价.|完成1份印度地铁行业调查报告，整合已建成项目的设备提供总包商、投资方情况；汇总了未来印度各个城市地铁的项目机会；分析了印度地铁项目的运作特点.");
						values.put("lat", UtilConstants.DELHI.latitude);
						values.put("lng", UtilConstants.DELHI.longitude);
						db.insert("footstep", null, values);
						values.clear();
						//insert fifth set data
						values.put("name", "尼泊尔 · 加德满都");
						values.put("maintask", "尼泊尔区域技术支持工程师，参与尼泊尔大型用户会组织.");
						values.put("performance", "解决尼泊尔总代的日常技术问题，主要涉及项目扩容的基站配置问题，协调售后部门制定项目交付方案.|作为团队技术支持角色，参与尼泊尔大型用户会的筹备");
						values.put("lat", UtilConstants.KATHMANDU.latitude);
						values.put("lng", UtilConstants.KATHMANDU.longitude);
						db.insert("footstep", null, values);
						values.clear();
						//insert sixth set data
						values.put("name", "中国 · 广东 · 深圳 · 龙华");
						values.put("maintask", "使用聚合数据的API，在主界面输入苹果设备的序列号，点击查询请求数据，最后将返回的数据解析出来呈现在结果界面里.|学习EditText,ListView,TextView等控件的基本用法.|学习布局文件的基本写法.|运用数据库知识，存储查询过的序列号和最近一次查询时间.|运用解析数据知识，完成对查询后返回的JSON格式数据进行解析.|运用一个开源库，实现ListView单个Item的删除菜单呼出，并了解如何使用GitHub上的开源库.");
						values.put("performance", "申请到聚合数据苹果序列号查询的API，运用聚合数据提供的SDK进行数据请求并成功返回JSON数据.|在APP里面运用了EditText控件，并控制字符输入只能是字母和数字；运用ListView控件，呈现查询结果和查询历史序列号，并且可以直接点击查询历史ListView的Item进行新的查询；运用Button控件，并运用shape,selector调整Button的UI样式.|采用LinearLayout，对每个Activity进行内容的布局，并且运用多层嵌套LinearLayout把横向与竖向布局的内容区分开.|在使用数据库存储时，判断某个序列号是否已经在数据库里面：如果没有，则把数据增加(insert)到数据库里；如果有，则获取最新系统时间，并更新(update)该序列号的最新一次查询时间.有单个删除(delete)某个数据的功能、删除(delete)全部数据的功能、只将有查询结果的序列号信息写入数据库的功能.|由于返回的JSON数据有两层结构，解析的时候运用了两次JSONObject类.|运用GitHub项目SwipeMenuListView，实现滑动呼出单个Item的删除菜单.|2015年4月14日上线，截止2015年5月16日已经有接近700的下载量.");
						values.put("lat", UtilConstants.SHENZHENLONGHUA.latitude);
						values.put("lng", UtilConstants.SHENZHENLONGHUA.longitude);
						db.insert("footstep", null, values);
						values.clear();
					}
				}else{
					Toast.makeText(getApplicationContext(), "请输入正确姓名进行搜索", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
    }
    
	private static void setUpMap(){
		
		aMap.animateCamera(CameraUpdateFactory
				.newLatLngZoom(new LatLng(34.3492,100.0243), 4.1f), 2000, null);
		
		aMap.addArc((new ArcOptions())
				.point(UtilConstants.LINHAI,new LatLng(34.3981,122.6302),UtilConstants.DALIAN)
				.strokeWidth(20).strokeColor(Color.parseColor("#448aff")));
        Marker markerLinhai=aMap.addMarker(new MarkerOptions().position(UtilConstants.LINHAI)
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
        		.anchor(0.5f, 0.8f));
        markerLinhai.setRotateAngle(270);
		
		aMap.addArc((new ArcOptions())
				.point(UtilConstants.DALIAN,new LatLng(32.3033,113.6975),UtilConstants.SHENZHENNANSHAN)
				.strokeWidth(20).strokeColor(Color.parseColor("#448aff")));
        Marker markerDalian=aMap.addMarker(new MarkerOptions().position(UtilConstants.DALIAN)
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
        		.anchor(0.5f, 0.8f));
        markerDalian.setRotateAngle(0);
		
		aMap.addArc((new ArcOptions())
				.point(UtilConstants.SHENZHENNANSHAN,new LatLng(30.9037,96.2385),UtilConstants.DELHI)
				.strokeWidth(20).strokeColor(Color.parseColor("#448aff")));
        Marker markerSZNanshan=aMap.addMarker(new MarkerOptions().position(UtilConstants.SHENZHENNANSHAN)
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
        		.anchor(0.5f, 0.8f));
        markerSZNanshan.setRotateAngle(135);
		
		aMap.addArc((new ArcOptions())
				.point(UtilConstants.DELHI,new LatLng(26.3242,80.7809),UtilConstants.KATHMANDU)
				.strokeWidth(20).strokeColor(Color.parseColor("#448aff")));
        Marker markerDelhi=aMap.addMarker(new MarkerOptions().position(UtilConstants.DELHI)
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
        		.anchor(0.5f, 0.8f));
        markerDelhi.setRotateAngle(0);
		
        Marker markerKathmandu=aMap.addMarker(new MarkerOptions().position(UtilConstants.KATHMANDU)
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
        		.anchor(0.5f, 0.8f));
        markerKathmandu.setRotateAngle(0);
		aMap.addArc((new ArcOptions())
				.point(UtilConstants.KATHMANDU,new LatLng(22.9134,97.9564),UtilConstants.SHENZHENLONGHUA)
				.strokeWidth(20).strokeColor(Color.parseColor("#448aff")));
        Marker markerSZLonghua=aMap.addMarker(new MarkerOptions().position(UtilConstants.SHENZHENLONGHUA)
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place_red))
        		.anchor(0.5f, 0.8f));
        markerSZLonghua.setRotateAngle(315);
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

}
