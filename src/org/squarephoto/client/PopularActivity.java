package org.squarephoto.client;

import org.squarephoto.client.adapters.PopularAdapters;
import org.squarephoto.client.base.BaseActivity;
import org.squarephoto.client.models.PopularItemModel;
import org.squarephoto.client.models.PopularResult;
import org.squarephoto.client.sql.PopularDbAdapter;
import org.squarephoto.client.tasks.UpdatePopularTask;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.danikula.aibolit.Aibolit;
import com.danikula.aibolit.annotation.InjectView;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public class PopularActivity extends BaseActivity {

	private static final String TAG = PopularActivity.class.getName();

	private PopularAdapters mThumbnailAdapters;

	private PopularDbAdapter mPopularDbAdapter;

	public static final String EXTRA_FIRST_TIME = "isFirstTime";

	@InjectView(R.id.gridview)
	private GridView mGridView;

	@InjectView(R.id.updateIndicator)
	private ProgressBar mUpdateBar;
	
	private UpdatePopularTask mUpdateTask;

	private String mAccessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Aibolit.setInjectedContentView(this, R.layout.popular_layout);
		boolean isFirstTime = getIntent().getBooleanExtra(EXTRA_FIRST_TIME,
				false);
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				PopularItemModel model = mPopularDbAdapter
						.getPopularItemById(id);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model
						.getLink()));
				startActivity(intent);
			}
		});
		mAccessToken = getAppPreferences().getAccessToken();
		mUpdateBar.setVisibility(View.GONE);
		if (isFirstTime) {
			runUpdate();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mPopularDbAdapter.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mPopularDbAdapter = new PopularDbAdapter(this);
		mPopularDbAdapter.open();
		mThumbnailAdapters = new PopularAdapters(this,
				mPopularDbAdapter.fetchAllPopular());
		mGridView.setAdapter(mThumbnailAdapters);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.popular_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.update_action:
			if (mUpdateTask == null
					|| mUpdateTask.getStatus() == Status.FINISHED) {
				runUpdate();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void runUpdate() {
		mUpdateBar.setVisibility(View.VISIBLE);
		mUpdateTask = new UpdatePopularTask(this) {

			@Override
			protected void callback(PopularResult result) {
				if (result.hasError()) {
					// show dialog with error message
					Log.e(TAG, "", result.getException());
				} else {
					mThumbnailAdapters.getCursor().requery();
					mThumbnailAdapters.notifyDataSetChanged();
				}
				mUpdateBar.setVisibility(View.GONE);
			}
		};

		mUpdateTask.execute("?access_token=" + mAccessToken);
	}
}
