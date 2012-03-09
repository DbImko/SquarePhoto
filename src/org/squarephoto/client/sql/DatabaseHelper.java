package org.squarephoto.client.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author Alexander Katanov
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getName();

	private static final String DATABASE_NAME = "squarephoto";

	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_POPULAR_CREATE = "create table popular "
			+ "(_id integer primary key autoincrement, "
			+ "link text not null,"
			+ "comments_count integer not null,"
			+ "likes_count integer not null,"
			+ "caption_text text,"
			+ "created_time long not null,"
			+ "user integer not null,"
			+ "thumbnail_url text not null,"
			+ "standard_resolution_url text not null,"
			+ "low_resolution_url text not null,"
			+ "internal_id text not null,"
			+ "FOREIGN KEY (user) REFERENCES user(_id) );";

	private static final String DATABASE_USER_CREATE = "create table user "
			+ "(_id integer primary key autoincrement,"
			+ "username text not null" + ");";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.w(TAG, "Database Create");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(DATABASE_USER_CREATE);
			db.execSQL(DATABASE_POPULAR_CREATE);
		} catch (Exception e) {
			Log.e(TAG, "", e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table user");
		db.execSQL("drop table popular");
		onCreate(db);
	}

}
