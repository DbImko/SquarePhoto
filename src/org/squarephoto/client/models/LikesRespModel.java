package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class LikesRespModel {
	@SerializedName("count")
	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
