package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class ResultRespModel {

	@SerializedName("data")
	private PopularRespModel data;

	public PopularRespModel getData() {
		return data;
	}

	public void setData(PopularRespModel data) {
		this.data = data;
	}
	
}
