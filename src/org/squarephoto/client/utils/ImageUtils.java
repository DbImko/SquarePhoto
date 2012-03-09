package org.squarephoto.client.utils;

import java.io.BufferedInputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.squarephoto.client.http.utils.HttpUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Image helper
 * 
 * @author Alexande Katanov
 * 
 */
public class ImageUtils {

	/**
	 * Obtain image from server
	 * 
	 * @param url
	 *            Image destination
	 * @return
	 * @throws Exception
	 */
	public static Bitmap readBitmapFromNetwork(String url) throws Exception {
		BufferedInputStream bis = null;
		Bitmap bmp = null;
		HttpClient client = HttpUtils.createHttpClient(30000);
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = client.execute(httpGet);
			if (response != null) {
				bis = new BufferedInputStream(response.getEntity().getContent());
				bmp = BitmapFactory.decodeStream(bis);
			}
		} finally {
			bis.close();
		}
		return bmp;
	}

}
