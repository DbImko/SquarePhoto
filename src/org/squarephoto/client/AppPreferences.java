package org.squarephoto.client;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Helper for getting and storing 'access token'. <br />
 * 
 * @author Alexander Katanov
 * 
 */
public class AppPreferences {

	private static final String ACCESS_TOKEN = "access_token";
	private static final String SHARED_PREF_NAME = "squarephoto";
	private static final int DEFAULT_PREF_MODE = 0;

	private SharedPreferences mPreferences;

	private AppPreferences(SharedPreferences sharedPreferences) {
		mPreferences = sharedPreferences;
	}

	public static AppPreferences getPreferences(Context context) {
		return new AppPreferences(context.getSharedPreferences(
				SHARED_PREF_NAME, DEFAULT_PREF_MODE));
	}

	public String getAccessToken() {
		return mPreferences.getString(ACCESS_TOKEN, null);
	}

	public void setAccessToken(String token) {
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putString(ACCESS_TOKEN, token);
		editor.commit();
	}

	public boolean hasAcceessToken() {
		return mPreferences.contains(ACCESS_TOKEN);
	}
}
