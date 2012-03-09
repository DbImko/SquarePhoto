package org.squarephoto.client.models;

import com.google.gson.annotations.SerializedName;

public class UserRespModel {
	@SerializedName("username")
	private String username;
	
	@SerializedName("full_name")
	private String fullname;
	
	@SerializedName("id")
	private String id;
	
	@SerializedName("profile_picture")
	private String profilePic;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
}
