package org.squarephoto.client.models;

import java.io.Serializable;

import org.squarephoto.client.R;

import android.content.Context;

/**
 * 
 * 
 * @author Alexander Katanov
 * 
 */
public class OAuthConfig implements Serializable {

	private static final long serialVersionUID = -907590015924407954L;
	private final String clientId;
	private final String clientSecret;
	private final String callback;
	private final String scope;

	public OAuthConfig(String id, String secret, String callback, String scope) {
		this.clientId = id;
		this.clientSecret = secret;
		this.callback = callback;
		this.scope = scope;
	}

	public static OAuthConfig getConfig(Context context) {
		return new OAuthConfig(context.getString(R.string.client_id),
				context.getString(R.string.client_secret),
				context.getString(R.string.callback),
				context.getString(R.string.scope));
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getCallback() {
		return callback;
	}

	public String getScope() {
		return scope;
	}

	public boolean hasScope() {
		return scope != null && !scope.isEmpty();
	}
}
