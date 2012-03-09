package org.squarephoto.client.base;

import org.squarephoto.client.AppPreferences;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {
	private AppPreferences mPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPref = AppPreferences.getPreferences(this);
	}

	protected AppPreferences getAppPreferences() {
		return mPref;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPref = null;
	}
}
