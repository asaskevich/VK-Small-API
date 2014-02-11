package com.asaskevich.vkapi;

public class VK_Group {
	private String name;
	private String logoURL;
	private String id;
	private int memberCount;

	public VK_Group(String id, String name, String logoURL, int memberCount) {
		this.id = id;
		this.name = name;
		this.logoURL = logoURL;
		this.memberCount = memberCount;
	}

	public String getName() {
		return name;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public String getId() {
		return id;
	}

	public int getMemberCount() {
		return memberCount;
	}

}
