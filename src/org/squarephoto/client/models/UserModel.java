package org.squarephoto.client.models;

public class UserModel {

	private int id;
	private String username;
//	private String fullname;
//	private String profilePic;
//	private String internalId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

//	public String getFullname() {
//		return fullname;
//	}
//
//	public void setFullname(String fullname) {
//		this.fullname = fullname;
//	}
//
//	public String getProfilePic() {
//		return profilePic;
//	}
//
//	public void setProfilePic(String profilePic) {
//		this.profilePic = profilePic;
//	}
//
//	public String getInternalId() {
//		return internalId;
//	}
//
//	public void setInternalId(String internalId) {
//		this.internalId = internalId;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
//	@Override
//	public String toString() {
//		return username + " " + fullname + " " + id + " " + profilePic;
//	}
}