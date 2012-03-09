package org.squarephoto.client.sql;

import org.squarephoto.client.models.PopularItemModel;
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
public class PopularDbAdapter extends BaseDbAdapter {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_LINK = "link";
	public static final String KEY_COMMENTS_COUNT = "comments_count";
	public static final String KEY_LIKES_COUNT = "likes_count";
	public static final String KEY_CAPTION_TEXT = "caption_text";
	public static final String KEY_CREATED_TIME = "created_time";
	public static final String KEY_USER = "user";
	public static final String KEY_THUMBNAIL = "thumbnail_url";
	public static final String KEY_STANDART_RESOLUTION = "standard_resolution_url";
	public static final String KEY_LOW_RESOLUTION = "low_resolution_url";
	public static final String KEY_INTERNALID = "internal_id";

	private static final String DATABASE_TABLE = "popular";

	private UserDbAdapter mUserDbAdapter;

	public PopularDbAdapter(Context context) {
		super(context);
	}

	@Override
	public PopularDbAdapter open() throws SQLException {
		mUserDbAdapter = new UserDbAdapter(mContext);
		mUserDbAdapter.open();
		super.open();
		return this;
	}

	@Override
	public void close() {
		super.close();
		mUserDbAdapter.close();
		mUserDbAdapter = null;
	}

	public long createPopularItem(PopularItemModel model) {
		Cursor c = mDatabase.rawQuery("SELECT " + KEY_ROWID + ", "
				+ KEY_INTERNALID + " FROM " + DATABASE_TABLE + " WHERE "
				+ KEY_INTERNALID + " = '" + model.getInternalId() + "'", null);
		if (c != null && c.getCount() == 0) {
			UserModel userModel = mUserDbAdapter.createUser(model.getUser());
			model.setUser(userModel);
			return insertPopularItem(model);
		}
		return 0;
	}

	private long insertPopularItem(PopularItemModel model) {
		ContentValues values = new ContentValues();
		values.put(KEY_LINK, model.getLink());
		values.put(KEY_USER, model.getUser().getId());
		values.put(KEY_CAPTION_TEXT, model.getCaptionText());
		values.put(KEY_INTERNALID, model.getInternalId());
		values.put(KEY_COMMENTS_COUNT, model.getCommentsCount());
		values.put(KEY_CREATED_TIME, model.getCreatedTime());
		values.put(KEY_LIKES_COUNT, model.getLikesCount());
		values.put(KEY_THUMBNAIL, model.getThumbnailUrl());
		values.put(KEY_STANDART_RESOLUTION, model.getStandardResolutionUrl());
		values.put(KEY_LOW_RESOLUTION, model.getLowResolutionUrl());
		return mDatabase.insert(DATABASE_TABLE, null, values);
	}

	public PopularItemModel getPopularItemById(Long id) {
		Cursor c = mDatabase.rawQuery("SELECT *" + " FROM " + DATABASE_TABLE
				+ " WHERE " + KEY_ROWID + " = " + id + "", null);
		PopularItemModel result = null;
		if (c != null && c.getCount() > 0) {
			result = fillModel(c);
		}
		return result;
	}

	private PopularItemModel fillModel(Cursor c) {
		c.moveToFirst();
		PopularItemModel result = new PopularItemModel(c);
		int userIndex = c.getColumnIndex(KEY_USER);
		result.setUser(mUserDbAdapter.getUserById(c.getLong(userIndex)));
		return result;
	}

	public Cursor fetchAllPopular() {
		return mDatabase.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_THUMBNAIL }, null, null, null, null, KEY_CREATED_TIME
				+ " DESC");
	}

	public void clear() {
		mDatabase.delete(DATABASE_TABLE, null, null);
	}
}
