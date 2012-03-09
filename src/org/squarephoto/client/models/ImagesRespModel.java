package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class ImagesRespModel {
	
	@SerializedName("thumbnail")
	private ImageItemRespModel thumbnail;
	
	@SerializedName("low_resolution")
	private ImageItemRespModel low;
	
	@SerializedName("standard_resolution")
	private ImageItemRespModel standard;

	public ImageItemRespModel getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(ImageItemRespModel thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ImageItemRespModel getLow() {
		return low;
	}

	public void setLow(ImageItemRespModel low) {
		this.low = low;
	}

	public ImageItemRespModel getStandard() {
		return standard;
	}

	public void setStandard(ImageItemRespModel standard) {
		this.standard = standard;
	}
}
