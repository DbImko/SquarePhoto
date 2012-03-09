package org.squarephoto.client.models;

import org.squarephoto.client.sql.PopularDbAdapter;

import android.database.Cursor;

public class PopularItemModel {

	private long id;
	private String link;
	private int commentsCount;
	private int likesCount;
	private String captionText;
	private long createdTime;
	private UserModel user;
	private String thumbnailUrl;
	private String standardResolutionUrl;
	private String lowResolutionUrl;
	private String internalId;

	public PopularItemModel() {
	}

	public PopularItemModel(Cursor c) {
		int idIndex = c.getColumnIndex(PopularDbAdapter.KEY_ROWID);
		int linkIndex = c.getColumnIndex(PopularDbAdapter.KEY_LINK);

		int internalIdIndex = c.getColumnIndex(PopularDbAdapter.KEY_INTERNALID);
		int captionTextIndex = c
				.getColumnIndex(PopularDbAdapter.KEY_CAPTION_TEXT);
		int likesCountIndex = c
				.getColumnIndex(PopularDbAdapter.KEY_LIKES_COUNT);
		int commentsCountIndex = c
				.getColumnIndex(PopularDbAdapter.KEY_COMMENTS_COUNT);
		int createdTimeIndex = c
				.getColumnIndex(PopularDbAdapter.KEY_CREATED_TIME);
		int thumbnailIndex = c.getColumnIndex(PopularDbAdapter.KEY_THUMBNAIL);
		int stdResolutionIndex = c
				.getColumnIndex(PopularDbAdapter.KEY_STANDART_RESOLUTION);
		int lowResolutionIndex = c
				.getColumnIndex(PopularDbAdapter.KEY_LOW_RESOLUTION);
		setId(c.getLong(idIndex));
		setLikesCount(c.getInt(likesCountIndex));
		setInternalId(c.getString(internalIdIndex));
		setLink(c.getString(linkIndex));
		setCaptionText(c.getString(captionTextIndex));
		setCommentsCount(c.getInt(commentsCountIndex));
		setCreatedTime(c.getLong(createdTimeIndex));
		setThumbnailUrl(c.getString(thumbnailIndex));
		setStandardResolutionUrl(c.getString(stdResolutionIndex));
		setLowResolutionUrl(c.getString(lowResolutionIndex));
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(int commentsCount) {
		this.commentsCount = commentsCount;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public String getCaptionText() {
		return captionText;
	}

	public void setCaptionText(String captionText) {
		this.captionText = captionText;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}

	public UserModel getUser() {
		return user;
	}

	public void setUser(UserModel user) {
		this.user = user;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getStandardResolutionUrl() {
		return standardResolutionUrl;
	}

	public void setStandardResolutionUrl(String standardResolutionUrl) {
		this.standardResolutionUrl = standardResolutionUrl;
	}

	public String getLowResolutionUrl() {
		return lowResolutionUrl;
	}

	public void setLowResolutionUrl(String lowResolutionUrl) {
		this.lowResolutionUrl = lowResolutionUrl;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

}
