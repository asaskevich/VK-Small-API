package com.asaskevich.vkapi;

/**
 * Store basic information about dialog
 * @author Alex Saskevich
 */
public class VK_Dialog {
	private int id;
	private String lastMessage;
	private String lastTime;
	private VK_ChatType type;
	private String chatName;
	private String userName;
	private boolean isUnreaded;
	private boolean isInbox;
	private boolean isOutbox;

	/**
	 * Constructor for dialog
	 * @param id dialog ID
	 * @param lastMessage last message that was sended
	 * @param lastTime time, when message was sended
	 * @param userName user, that send message
	 * @param isUnreaded is last message unreaded
	 * @param isInbox is last message inbox
	 * @param isOutbox is last message outbox
	 */
	public VK_Dialog(int id, String lastMessage, String lastTime,
			String userName, boolean isUnreaded, boolean isInbox,
			boolean isOutbox) {
		this.type = VK_ChatType.DIALOG;
		this.id = id;
		this.lastMessage = lastMessage;
		this.lastTime = lastTime;
		this.userName = userName;
		this.isUnreaded = isUnreaded;
		this.isInbox = isInbox;
		this.isOutbox = isOutbox;
	}

	/**
	 * Constructor for chat
	 * @param id dialog ID
	 * @param lastMessage last message that was sended
	 * @param lastTime time, when message was sended
	 * @param chatName name of chat
	 * @param userName user, that send message
	 * @param isUnreaded is last message unreaded
	 * @param isInbox is last message inbox
	 * @param isOutbox is last message outbox
	 */
	public VK_Dialog(int id, String lastMessage, String lastTime,
			String chatName, String userName, boolean isUnreaded,
			boolean isInbox, boolean isOutbox) {
		this(id, lastMessage, lastTime, userName, isUnreaded, isInbox, isOutbox);
		this.type = VK_ChatType.CHAT;
		this.chatName = chatName;
	}
	
	/**
	 * @return id of dialog
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return last sended message
	 */
	public String getLastMessage() {
		return lastMessage;
	}

	/**
	 * @return time when was sended message
	 */
	public String getLastTime() {
		return lastTime;
	}

	/**
	 * @return type - dialog or chat
	 */
	public VK_ChatType getType() {
		return type;
	}

	/**
	 * @return if it chat then chat name, else null 
	 */
	public String getChatName() {
		return chatName;
	}

	/**
	 * @return user name, that send message to this dialog
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return true if last message is unreaded
	 */
	public boolean isUnreaded() {
		return isUnreaded;
	}
	
	/**
	 * @return true if last unreaded message is inbox
	 */
	public boolean isInbox() {
		return isInbox;
	}

	/**
	 * @return true if last unreaded message is outbox
	 */
	public boolean isOutbox() {
		return isOutbox;
	}
}
