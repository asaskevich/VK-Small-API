package com.asaskevich.vkapi;

/**
 * Class, that store general information about some friend
 * @author Alex Saskevich
 */

public class VK_Friend {
	private int id;
	private String name;
	private String photoURL;
	private VK_Status status;

	/**
	 * Create new instance of class
	 * @param id profile id, can be used for sending private message
	 * @param name e.g. John Smith
	 * @param photoURL URL to profile image - avatar
	 * @param status user status - on-line or off-line
	 */
	public VK_Friend(int id, String name, String photoURL, VK_Status status) {
		this.id = id;
		this.name = name;
		this.photoURL = photoURL;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public VK_Status getStatus() {
		return status;
	}
}
