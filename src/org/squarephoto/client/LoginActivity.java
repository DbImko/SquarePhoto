package org.squarephoto.client;

import java.util.Map;

import org.squarephoto.client.api.InstagramApi;
import org.squarephoto.client.base.BaseActivity;
import org.squarephoto.client.models.AuthRespModel;
import org.squarephoto.client.models.OAuthConfig;
import org.squarephoto.client.oauth.OAuthConstant;
import org.squarephoto.client.tasks.OAuthRequesterForAccessTokenTask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;

import com.danikula.aibolit.Aibolit;
import com.danikula.aibolit.annotation.InjectView;

/**
 * Sing in activity
 * 
 * @author Alexander Katanov
 * 
 */
public class LoginActivity extends BaseActivity {

	private static final String TAG = LoginActivity.class.getName();

	private static final String ERROR_KEY = "error";

	private ProgressDialog mLoadingDialog;

	@InjectView(R.id.webView)
	private WebView mWebView;

	private OAuthConfig mConfig;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Aibolit.setInjectedContentView(this, R.layout.login);

		mConfig = OAuthConfig.getConfig(this);

		if (getAppPreferences().hasAcceessToken()) {
			Intent intent = new Intent(this, PopularActivity.class);
			startActivity(intent);
		} else {
			initWebView();
			mWebView.loadUrl(InstagramApi.getAuthUrl(mConfig));
		}
	}

	/**
	 * Create and initialize content view for activity
	 */
	private void initWebView() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setVerticalScrollBarEnabled(false);
		mWebView.setHorizontalScrollBarEnabled(false);
		OAuthWebViewClient webClient = new OAuthWebViewClient(mConfig);
		webClient.setOAuthLoginListener(new OAuthLoginListener() {

			@Override
			public void onStopLoading() {
				dismissLoading();
			}

			@Override
			public void onStartLogin() {
				showLoading();
			}

			@Override
			public void onError(Exception exception) {
				Log.e(TAG, "", exception);
				throw new RuntimeException(exception);
			}

			@Override
			public void onDone(Map<String, String> values) {
				authHandler(values);
			}
		});
		mWebView.setWebViewClient(webClient);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSavePassword(false);
	}

	private void authHandler(Map<String, String> values) {
		String error = checkError(values);
		if (error == null) {
			loadHtml(R.string.redirecting_html);
			createTask().execute(values.get(OAuthConstant.CODE));
			showLoading();
		} else if (error.equals("access_denied")) {
			// handling user access denied
			loadHtml(R.string.access_denie_html);
		} else {
			// handling other situation
			loadHtml(R.string.unknown_error_html);
		}
	}

	private String checkError(Map<String, String> values) {
		String error = values.containsKey(ERROR_KEY) ? values.get(ERROR_KEY)
				: null;
		return error;
	}

	private OAuthRequesterForAccessTokenTask createTask() {
		return new OAuthRequesterForAccessTokenTask(mConfig) {

			@Override
			protected void callback(AuthRespModel result) {
				if (!result.hasError()) {
					getAppPreferences().setAccessToken(result.getAccessToken());
					Intent intent = new Intent(LoginActivity.this,
							PopularActivity.class);
					intent.putExtra(PopularActivity.EXTRA_FIRST_TIME, true);
					startActivity(intent);
				} else {
					loadHtml(R.string.unknown_error_html);
				}
				dismissLoading();
			}
		};
	}

	private void loadHtml(int resource) {
		loadHtml(getString(resource));
	}

	private void loadHtml(String html) {
		mWebView.loadDataWithBaseURL(InstagramApi.SERVICE_HOST, html,
				"text/html", "UTF-8", null);
	}

	private void showLoading() {
		if (mLoadingDialog == null) {
			mLoadingDialog = ProgressDialog.show(this, null,
					getString(R.string.loading_label));
		}
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.show();
		}
	}

	private void dismissLoading() {
		if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
	}
}
