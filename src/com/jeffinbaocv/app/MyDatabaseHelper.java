package com.jeffinbaocv.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	public static final String CREATE_FOOTSTEP="create table footstep ("
			+"id integer primary key autoincrement, "
			+"name text, "
			+"maintask text, "
			+"performance text, "
			+"lat real, "
			+"lng real)";
	
	private Context mContext;
	
	public MyDatabaseHelper(Context context,String name,CursorFactory factory,int version){
		super(context,name,factory,version);
		mContext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_FOOTSTEP);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
