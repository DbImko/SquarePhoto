package org.squarephoto.client.api;

import org.squarephoto.client.http.utils.UrlUtils;
import org.squarephoto.client.models.OAuthConfig;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public class InstagramApi {

	public static final String SERVICE_HOST = "instagram.com";

	public static final String SEARCH_URL = "https://api.instagram.com/v1/media/popular";

	private static final String AUTH_URL_FORMAT = "https://api.instagram.com/oauth/authorize/?client_id=%s"
			+ "&redirect_uri=%s"
			+ "&response_type=code"
			+ "&scope=%s&display=touch";

	public static final String ACCESS_TOKEN_URL = "https://api.instagram.com/oauth/access_token";

	public static String getAuthUrl(OAuthConfig config) {
		return String.format(AUTH_URL_FORMAT, config.getClientId(),
				UrlUtils.encode(config.getCallback()), config.getScope());
	}

}
