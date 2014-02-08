package com.asaskevich.vkapi;

public class VK_Message {
	private String time;
	private String message;
	private String user;
	private int userID;
	private boolean isUnread;

	public VK_Message(String message, String user, int userID, String time, boolean isUnread) {
		this.message = message;
		this.user = user;
		this.userID = userID;
		this.time = time;
		this.isUnread = isUnread;
	}

	public String getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public String getUser() {
		return user;
	}

	public int getUserID() {
		return userID;
	}

	public boolean isUnread() {
		return isUnread;
	}
}
