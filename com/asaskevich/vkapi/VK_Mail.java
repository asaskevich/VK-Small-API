package com.asaskevich.vkapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Used for working with private messages
 * @author Alex Saskevich
 */
public class VK_Mail {
	private final String defaultURL = "https://m.vk.com/mail";
	private Map<String, String> cookies;
	@SuppressWarnings("unused")
	private Document loadedDocument;

	/**
	 * Create new instance of class
	 * @param cookies authorization tokens, that was received by
	 *            {@link VK_Auth.auth}
	 */
	public VK_Mail(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	/**
	 * Update loaded information
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void updateData() throws Exception {
		Connection.Response connection = null;
		connection = Jsoup.connect(defaultURL).cookies(cookies).execute();
		loadedDocument = connection.parse();
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
}
