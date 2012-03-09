package org.squarephoto.client.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Alexander Katanov
 *
 */
public class BaseDbAdapter {

	protected Context mContext;
	protected SQLiteDatabase mDatabase;
	private DatabaseHelper mDbHelper;
	
	public BaseDbAdapter(Context context) {
		mContext = context;
	}
	
	public BaseDbAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext);
		mDatabase = mDbHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		mDatabase.close();
		mDbHelper.close();
		mContext = null;
		mDbHelper = null;
		mDatabase = null;
	}
	
}
