package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class CaptionRespModel {
	@SerializedName("text")
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
