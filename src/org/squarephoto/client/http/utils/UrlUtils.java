package org.squarephoto.client.http.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.protocol.HTTP;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public class UrlUtils {

	/**
	 * Translates string s into application/x-www-form-urlencoded format
	 * 
	 * @param plain
	 * @return form-url encoded string
	 */
	public static String encode(String plain) {
		try {
			return URLEncoder.encode(plain, HTTP.UTF_8);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Split url on key-value
	 * 
	 * @param url
	 *            url with parameters
	 * @return key-value
	 */
	public static Map<String, String> decodeUrl(String url) {
		Map<String, String> params = new HashMap<String, String>();
		if (url != null) {
			String array[] = url.split("&");
			for (String parameter : array) {
				String v[] = parameter.split("=");
				if (v.length == 2) {
					params.put(URLDecoder.decode(v[0]), URLDecoder.decode(v[1]));
				} else {
					params.put(URLDecoder.decode(v[0]), " ");
				}
			}
		}
		return params;
	}
}
