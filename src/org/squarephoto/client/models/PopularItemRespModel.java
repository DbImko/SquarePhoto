package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class PopularItemRespModel {
	@SerializedName("type")
	private String type;
	
	@SerializedName("comments")
	private CommentsRespModel comments;
	
	@SerializedName("likes")
	private LikesRespModel likes;
	
	@SerializedName("created_time")
	private long createdTime;
	
	@SerializedName("link")
	private String link;
	
	@SerializedName("images")
	private ImagesRespModel images;
	
	@SerializedName("caption")
	private CaptionRespModel caption;
	
	@SerializedName("id")
	private String id;

	@SerializedName("user")
	private UserRespModel user;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public CommentsRespModel getComments() {
		return comments;
	}

	public void setComments(CommentsRespModel comments) {
		this.comments = comments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LikesRespModel getLikes() {
		return likes;
	}

	public void setLikes(LikesRespModel likes) {
		this.likes = likes;
	}

	public ImagesRespModel getImages() {
		return images;
	}

	public void setImages(ImagesRespModel images) {
		this.images = images;
	}

	public CaptionRespModel getCaption() {
		return caption;
	}

	public void setCaption(CaptionRespModel caption) {
		this.caption = caption;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRespModel getUser() {
		return user;
	}

	public void setUser(UserRespModel user) {
		this.user = user;
	}
	
	
}
