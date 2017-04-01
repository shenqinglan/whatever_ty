package com.whty.rsp_lpa_app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

	private final static String name = "lpa.db";
	private final static int version = 1;
	
	public DatabaseHelper(Context context) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		StringBuffer sbPPR = new StringBuffer();
		sbPPR.append("CREATE TABLE IF NOT EXISTS PPR (")
		.append("_id integer primary key autoincrement, ")
		.append("pprid varchar(20), ")
		.append("description varchar(100));");
		db.execSQL(sbPPR.toString());
		
		StringBuffer sbRAT = new StringBuffer();
		sbRAT.append("CREATE TABLE IF NOT EXISTS RAT (")
		.append("_id integer primary key autoincrement, ")
		.append("pprid varchar(20), ")
		.append("operators varchar(20), ")
		.append("required varchar(1));");
		db.execSQL(sbRAT.toString());
		
		StringBuffer sbProfile = new StringBuffer();
		sbProfile.append("CREATE TABLE IF NOT EXISTS Profile (")
		.append("iccid varchar(32) primary key, ")
		.append("isd_p_aid varchar(32), ")
		.append("mno_id varchar(32), ")
		.append("fallback_attribute varchar(2), ")
		.append("subscrition_address varchar(32), ")
		.append("state varchar(2), ")
		.append("smdp_id varchar(32), ")
		.append("profile_type varchar(2), ")
		.append("allocated_memory varchar(10), ")
		.append("free_memory varchar(10), ")
		.append("pol2_id varchar(32), ")
		.append("eid varchar(32);");
		db.execSQL(sbProfile.toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
