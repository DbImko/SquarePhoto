package org.squarephoto.client;

import java.util.Map;

/**
 * 
 * @author Alexander Katanov
 * 
 */
public interface OAuthLoginListener {

	public void onStartLogin();

	public void onStopLoading();

	public void onDone(Map<String, String> values);

	public void onError(Exception exception);
}
