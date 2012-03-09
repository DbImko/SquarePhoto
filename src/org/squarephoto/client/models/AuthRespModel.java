package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class AuthRespModel {
	@SerializedName("access_token")
	private String accessToken;

	@SerializedName("error")
	private String error;

	@SerializedName("error_type")
	private String errorType;

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@SerializedName("code")
	private int code;

	@SerializedName("error_message")
	private String errorMessage;

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

	public boolean hasError() {
		return error != null || exception != null || errorType != null;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}
}
