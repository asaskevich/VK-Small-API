package com.asaskevich.vkapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Used for working with private messages
 * @author Alex Saskevich
 */
public class VK_Mail {
	private final String defaultURL = "https://m.vk.com/mail";
	private final int extraID = 2000000000;
	private Map<String, String> cookies;
	private List<VK_Dialog> list;

	/**
	 * Create new instance of class
	 * @param cookies authorization tokens, that was received by
	 *            {@link VK_Auth.auth}
	 */
	public VK_Mail(Map<String, String> cookies) {
		this.cookies = cookies;
		this.list = new ArrayList<VK_Dialog>();
	}

	/**
	 * Send private message to specified dialog
	 * @param dialogId ID of chat or ID of user
	 * @param message text of message, can be truncated by server
	 * @throws IOException
	 */
	public void sendMessage(int dialogId, String message) throws IOException {
		Connection.Response connection = null;
		connection = Jsoup.connect("https://m.vk.com/write" + dialogId)
				.cookies(cookies).execute();
		Document document = connection.parse();
		Element form = document.select("#write_form").get(0);
		String url = form.attr("action");
		Map<String, String> data = new HashMap<String, String>();
		data.put("message", message);
		connection = Jsoup.connect("https://m.vk.com" + url).data(data)
				.cookies(cookies).execute();
	}

	/**
	 * Load next 20 dialogs at custom offset
	 * @param offset count of skipped dialogs before reading
	 * @return count of readed dialogs, equal or less than 20
	 * @throws Exception
	 */
	private int getDialogs(int offset) throws Exception {
		Connection.Response connection = null;
		connection = Jsoup.connect(defaultURL + "?offset=" + offset)
				.cookies(cookies).execute();
		Document document = connection.parse();
		Elements items = document.select(".dialog_item");
		if (items.size() == 0) {
			return 0;
		}
		int size = items.size();
		for (Element next : items) {
			boolean isUnreaded = next.attr("class").contains("di_unread");
			boolean isInbox = next.attr("class").contains("inbox");
			boolean isOutbox = next.attr("class").contains("outbox");
			Element date = next.select(".di_date").get(0);
			Element message = next.select(".di_text").get(0);
			Element author = next.select(".mi_author").get(0);
			VK_ChatType type = next.attr("href").contains("chat") ? VK_ChatType.CHAT
					: VK_ChatType.DIALOG;
			String lastTime = date.text();
			String url = next.attr("href");
			String lastMessage = message.text();
			if (type == VK_ChatType.CHAT) {
				String chat_name = author.text();
				String strId = url.substring(url.indexOf("chat=") + 5);
				int id = extraID + Integer.valueOf(strId);
				Element chat_user = next.select(".di_chat_user").get(0);
				String user_name = chat_user.text();
				VK_Dialog dialog = new VK_Dialog(id, lastMessage, lastTime,
						chat_name, user_name, isUnreaded, isInbox, isOutbox);
				list.add(dialog);
			} else {
				String strId = url.substring(url.indexOf("peer=") + 5);
				int id = Integer.valueOf(strId);
				String user_name = author.text();
				VK_Dialog dialog = new VK_Dialog(id, lastMessage, lastTime,
						user_name, isUnreaded, isInbox, isOutbox);
				list.add(dialog);
			}
		}
		return size;
	}

	/**
	 * Load all dialogs from profile
	 * @throws Exception
	 */
	private void getAllDialogs() throws Exception {
		if (list == null) {
			list = new ArrayList<VK_Dialog>();
		}
		list.clear();
		int offset = 0;
		int step = 20;
		while (getDialogs(offset) != 0) {
			offset += step;
		}
	}

	/**
	 * Load list of 20 last dialogs
	 * @return list that contains last dialogs
	 * @throws Exception
	 */
	public List<VK_Dialog> getLastDialogs() throws Exception {
		list.clear();
		getDialogs(0);
		return list;
	}

	/**
	 * Load all dialogs from profile and return it in list
	 * @return
	 * @throws Exception
	 */
	public List<VK_Dialog> getDialogs() throws Exception {
		if (list == null || list.size() == 0) {
			getAllDialogs();
		}
		return list;
	}
}
