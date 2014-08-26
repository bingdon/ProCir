package com.example.projectcircle.db.upgrade;

import com.example.projectcircle.db.ProJectDbHelper;

import android.database.sqlite.SQLiteDatabase;

public class UpgradeUtilty {

	public static void upgrade1to2(SQLiteDatabase db) {
		db.execSQL(ProJectDbHelper.CREATE_NEW_CONSTACT_DB);
	}

}
