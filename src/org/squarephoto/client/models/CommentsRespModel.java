package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class CommentsRespModel {

	@SerializedName("count")
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
