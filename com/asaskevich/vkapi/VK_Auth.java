package com.asaskevich.vkapi;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Authorization class, used for receiving authorization tokens
 * @author Alex Saskevich
 */
public class VK_Auth {
	final static String defaultURL = "https://m.vk.com";

	/**
	 * Send authorization data and retrieves cookies, that can be used for work
	 * with <i>vk.com</i>
	 * @param login your login
	 * @param pass your password
	 * @return map, that contains authorization tokens and cookies
	 * @throws IllegalArgumentException if login or password is incorrect
	 * @throws Exception in another internal exception
	 */
	public static Map<String, String> auth(String login, String pass)
			throws Exception {
		try {
			String url = "";
			Connection.Response connection = null;
			// Get authorization URL
			connection = Jsoup.connect(defaultURL).execute();
			Document doc = connection.parse();
			Element form = doc.getElementsByTag("form").get(0);
			url = form.attr("action");
			// Sending authorization data
			Map<String, String> data = new HashMap<String, String>();
			data.put("email", login);
			data.put("pass", pass);
			connection = Jsoup.connect(url).data(data).execute();
			// Retrieve cookies
			Map<String, String> cookies = connection.cookies();
			if (cookies.containsKey("p"))
				return cookies;
			else
				throw new IllegalArgumentException("Bad login or password");
		} catch (Exception exception) {
			throw exception;
		}
	}
}
