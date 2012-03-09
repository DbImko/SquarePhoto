package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class AuthRespModel {
	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("error")
	private String error;
	
	private Exception exception;
	
	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	public boolean hasError () {
		return error != null || exception != null;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
}
