package org.squarephoto.client.tasks;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.squarephoto.client.api.InstagramApi;
import org.squarephoto.client.http.utils.HttpUtils;
import org.squarephoto.client.models.CaptionRespModel;
import org.squarephoto.client.models.PopularItemModel;
import org.squarephoto.client.models.PopularItemRespModel;
import org.squarephoto.client.models.PopularRespModel;
import org.squarephoto.client.models.PopularResult;
import org.squarephoto.client.models.UserModel;
import org.squarephoto.client.models.UserRespModel;
import org.squarephoto.client.sql.PopularDbAdapter;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;

/**
 * 
 * @author DbImko
 * 
 */
public abstract class UpdatePopularTask extends
		AsyncTask<String, Void, PopularResult> {

	private Gson mGson;

	private Context mContext;

	private static final int DEFAULT_COUNT = 10;

	public UpdatePopularTask(Context context) {
		mContext = context;
		mGson = new Gson();
	}

	@Override
	protected PopularResult doInBackground(String... params) {
		PopularResult result = new PopularResult();
		HttpClient httpClient = HttpUtils.createHttpClient(10000);
		HttpGet httpGet = new HttpGet(InstagramApi.SEARCH_URL + params[0]);
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);

			String respText = EntityUtils.toString(httpResponse.getEntity());

			PopularRespModel respModel = mGson.fromJson(respText,
					PopularRespModel.class);
			if (respModel.getPopularItems().size() > 0) {
				int i = 0;
				for (PopularItemRespModel model : respModel.getPopularItems()) {
					if (i == DEFAULT_COUNT) {
						break;
					}
					PopularItemModel itemModel = new PopularItemModel();

					UserModel userModel = new UserModel();
					UserRespModel userRespModel = model.getUser();
					userModel.setUsername(userRespModel.getUsername());
					// userModel.setFullname(userRespModel.getFullname());
					// userModel.setInternalId(userRespModel.getId());
					// userModel.setProfilePic(userRespModel.getProfilePic());
					itemModel.setUser(userModel);

					itemModel.setInternalId(model.getId());
					itemModel.setLink(model.getLink());
					CaptionRespModel captionRespModel = model.getCaption();
					if (captionRespModel != null) {
						itemModel.setCaptionText(captionRespModel.getText());
					}
					itemModel.setCommentsCount(model.getComments().getCount());
					itemModel.setLikesCount(model.getLikes().getCount());
					itemModel.setLowResolutionUrl(model.getImages().getLow()
							.getUrl());
					itemModel.setStandardResolutionUrl(model.getImages()
							.getStandard().getUrl());
					itemModel.setThumbnailUrl(model.getImages().getThumbnail()
							.getUrl());
					itemModel.setCreatedTime(model.getCreatedTime());
					result.getItems().add(itemModel);
					i++;
				}
				if (!result.hasError()) {
					PopularDbAdapter db = new PopularDbAdapter(mContext);
					db.open();
					db.clear();
					for (PopularItemModel popularItemModel : result.getItems()) {
						db.createPopularItem(popularItemModel);
					}
					db.close();
				}
			}
		} catch (Exception e) {
			result.setException(e);
		}
		return result;
	}

	@Override
	protected void onPostExecute(PopularResult result) {
		super.onPostExecute(result);
		mContext = null;
		callback(result);
	}

	protected abstract void callback(PopularResult result);
}
