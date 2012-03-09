package org.squarephoto.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.squarephoto.client.api.InstagramApi;
import org.squarephoto.client.exceptions.UnknownLoginException;
import org.squarephoto.client.http.utils.UrlUtils;
import org.squarephoto.client.models.OAuthConfig;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Web client for handling states of WebView
 * 
 * @author Alexander Katanov
 * 
 */
public class OAuthWebViewClient extends WebViewClient {

	private static final String TAG = OAuthWebViewClient.class.getName();

	private OAuthConfig mOAuthConfig;

	private OAuthLoginListener mOAuthLoginListener;

	public OAuthWebViewClient(OAuthConfig config) {
		mOAuthConfig = config;
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		if (!failingUrl.startsWith(mOAuthConfig.getCallback())) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.e(TAG, "ON ERROR" + errorCode + " " + description);
		}
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		Log.w(TAG, " onPageStarted loading URL: " + url);
		super.onPageStarted(view, url, favicon);
		mOAuthLoginListener.onStartLogin();
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		Log.w(TAG, "onPageFinished URL: " + url);
		super.onPageFinished(view, url);

		mOAuthLoginListener.onStopLoading();

		if (url.startsWith(mOAuthConfig.getCallback())) {
			Map<String, String> values = null;
			try {
				URL u = new URL(url);
				Map<String, String> b = UrlUtils.decodeUrl(u.getQuery());
				b.putAll(UrlUtils.decodeUrl(u.getRef()));
				values = b;
			} catch (MalformedURLException e) {
				values = new HashMap<String, String>();
			}
			mOAuthLoginListener.onDone(values);
		} else {
			if (!url.contains(InstagramApi.SERVICE_HOST)) {
				Log.e(TAG, "URL " + url);
				mOAuthLoginListener.onError(new UnknownLoginException());
			} else {
				// do nothing, normal situation
			}
		}
	}

	/**
	 * Register callback
	 * 
	 * @param mOAuthLoginListener
	 *            Callback will run
	 */
	public void setOAuthLoginListener(OAuthLoginListener mOAuthLoginListener) {
		this.mOAuthLoginListener = mOAuthLoginListener;
	}

	public void removeOAuthLoginListener() {
		mOAuthLoginListener = null;
	}
}
