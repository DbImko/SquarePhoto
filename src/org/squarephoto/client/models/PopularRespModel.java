package org.squarephoto.client.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PopularRespModel {
	
	@SerializedName("data")
	private List<PopularItemRespModel> popularItems;

	public PopularRespModel() {
		popularItems = new ArrayList<PopularItemRespModel>();
	}
	
	public List<PopularItemRespModel> getPopularItems() {
		return popularItems;
	}

	public void setPopularItems(List<PopularItemRespModel> popularItems) {
		this.popularItems = popularItems;
	}
}
