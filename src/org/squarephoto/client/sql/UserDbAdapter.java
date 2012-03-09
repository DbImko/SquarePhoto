package org.squarephoto.client.sql;

import org.squarephoto.client.models.UserModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

/**
 * 
 * @author Alexander Katanov
 *
 */
public class UserDbAdapter extends BaseDbAdapter {

	@SuppressWarnings("unused")
	private static final String TAG = UserDbAdapter.class.getName();

	public static final String KEY_ROWID = "_id";
	public static final String KEY_USERNAME = "username";
	public static final String KEY_FULLNAME = "fullname";
	public static final String KEY_PROFILEPIC = "profile_pic";
	public static final String KEY_INTERNALID = "internal_id";

	private static final String DATABASE_TABLE = "user";

	public UserDbAdapter(Context context) {
		super(context);
	}

	@Override
	public UserDbAdapter open() throws SQLException {
		super.open();
		return this;
	}

	public UserModel createUser(UserModel userModel) {
		return insertUser(userModel);
	}

	private UserModel insertUser(UserModel userModel) {
		ContentValues values = new ContentValues();
		values.put(KEY_USERNAME, userModel.getUsername());
//		values.put(KEY_FULLNAME, userModel.getFullname());
//		values.put(KEY_PROFILEPIC, userModel.getProfilePic());
//		values.put(KEY_INTERNALID, userModel.getInternalId());
		long id = mDatabase.insert(DATABASE_TABLE, null, values);
		UserModel useModel1= getUserById(id);
		return useModel1;
	}

	public UserModel getUserById(long id) {
		Cursor c = mDatabase.rawQuery("SELECT *" + " FROM " + DATABASE_TABLE
				+ " WHERE " + KEY_ROWID + " = " + id + "", null);
		UserModel result = null;
		if (c != null && c.getCount() > 0) {
			result = fillModel(c);
		}
		return result;
	}

	private UserModel fillModel(Cursor c) {
		c.moveToFirst();
		UserModel result = new UserModel();
		int idIndex = c.getColumnIndex(KEY_ROWID);
		int usernameIndex = c.getColumnIndex(KEY_USERNAME);
//		int fullnameIndex = c.getColumnIndex(KEY_FULLNAME);
//		int internalIdIndex = c.getColumnIndex(KEY_INTERNALID);
//		int profilePicIndex = c.getColumnIndex(KEY_PROFILEPIC);
		result.setId(c.getInt(idIndex));
		result.setUsername(c.getString(usernameIndex));
//		result.setFullname(c.getString(fullnameIndex));
//		result.setInternalId(c.getString(internalIdIndex));
//		result.setProfilePic(c.getString(profilePicIndex));
		return result;
	}

}
