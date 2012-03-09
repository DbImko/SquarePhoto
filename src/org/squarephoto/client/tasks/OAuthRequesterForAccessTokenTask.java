package org.squarephoto.client.tasks;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.squarephoto.client.api.InstagramApi;
import org.squarephoto.client.http.utils.HttpUtils;
import org.squarephoto.client.models.AuthRespModel;
import org.squarephoto.client.models.OAuthConfig;
import org.squarephoto.client.oauth.OAuthConstant;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public abstract class OAuthRequesterForAccessTokenTask extends
		AsyncTask<String, Void, AuthRespModel> {

	private static final String TAG = OAuthRequesterForAccessTokenTask.class.getName();

	private static final int DEFAULT_VALUE = 0;

	private OAuthConfig mOAuthConfig;
	private Gson mGson;

	public OAuthRequesterForAccessTokenTask(OAuthConfig config) {
		mOAuthConfig = config;
		mGson = new Gson();
	}

	@Override
	protected AuthRespModel doInBackground(String... params) {
		AuthRespModel result = new AuthRespModel();
		String code = params[DEFAULT_VALUE];

		Map<String, String> httpParams = new HashMap<String, String>();
		httpParams.put(OAuthConstant.CLIENT_ID, mOAuthConfig.getClientId());
		httpParams.put(OAuthConstant.CLIENT_SECRET,
				mOAuthConfig.getClientSecret());
		httpParams.put(OAuthConstant.GRANT_TYPE, OAuthConstant.AUTH_CODE);
		httpParams.put(OAuthConstant.CALLBACK_URL, mOAuthConfig.getCallback());
		httpParams.put(OAuthConstant.CODE, code);
		try {
			String accessToken = sendGetRequest(httpParams);
			Log.e(TAG, "TOKEN " + accessToken);
			result = mGson.fromJson(accessToken, AuthRespModel.class);
		} catch (Exception e) {
			Log.e(TAG, "", e);
			result.setException(e);
		}
		return result;
	}

	private String sendGetRequest(Map<String, String> params) throws Exception {
		HttpParams myParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(myParams, 20000);
		HttpConnectionParams.setSoTimeout(myParams, 20000);
		HttpClient httpClient = new DefaultHttpClient(myParams);
		String getUrl = InstagramApi.ACCESS_TOKEN_URL;
		String ret = "";
		HttpEntity httpEntity = null;
		httpEntity = new UrlEncodedFormEntity(HttpUtils.getPostParams(params),
				HTTP.UTF_8);

		HttpPost httpPost = new HttpPost(getUrl);
		httpPost.setEntity(httpEntity);
		Log.w(TAG, "WebGetURL: " + getUrl);

		HttpResponse response = httpClient.execute(httpPost);
		ret = EntityUtils.toString(response.getEntity());

		return ret;
	}

	@Override
	protected void onPostExecute(AuthRespModel result) {
		super.onPostExecute(result);
		callback(result);
	}

	protected abstract void callback(AuthRespModel result);
}
