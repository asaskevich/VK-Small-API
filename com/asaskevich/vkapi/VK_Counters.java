package com.asaskevich.vkapi;

import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Counters for new messages, friend requests, answers and invitations
 * @author Alex Saskevich
 */
public class VK_Counters {
	private final String defaultURL = "https://m.vk.com";
	private Map<String, String> cookies;
	private Document loadedDocument;

	/**
	 * Create new instance of class
	 * @param cookies authorization tokens, that was received by
	 *            {@link VK_Auth.auth}
	 */
	public VK_Counters(Map<String, String> cookies) {
		this.cookies = cookies;
	}

	/**
	 * Update loaded information
	 * @throws Exception
	 */
	private void updateData() throws Exception {
		Connection.Response connection = null;
		connection = Jsoup.connect(defaultURL).cookies(cookies).execute();
		loadedDocument = connection.parse();
	}

	/**
	 * Load specific counter by CSS query and return integer value of counter
	 * @param cssQuery
	 * @return
	 * @throws Exception
	 */
	private int getSpecificCount(String cssQuery) throws Exception {
		updateData();
		Elements counter = loadedDocument.select(cssQuery);
		if (counter.size() == 0) {
			return 0;
		}
		String innerText = counter.get(0).text();
		if (innerText != null) {
			return Integer.valueOf(innerText);
		} else {
			return 0;
		}
	}

	/**
	 * @return count of new messages
	 * @throws Exception
	 */
	public int getMailCount() throws Exception {
		return getSpecificCount(".mmi_mail .mm_counter");
	}

	/**
	 * @return count of new answers
	 * @throws Exception
	 */
	public int getAnswersCount() throws Exception {
		return getSpecificCount(".mmi_answers .mm_counter");
	}

	/**
	 * @return count of new friend requests
	 * @throws Exception
	 */
	public int getFriendsCount() throws Exception {
		return getSpecificCount(".mmi_friends .mm_counter");
	}

	/**
	 * @return count of new group invitations
	 * @throws Exception
	 */
	public int getGroupsCount() throws Exception {
		return getSpecificCount(".mmi_groups .mm_counter");
	}

	/**
	 * @return count of new photos
	 * @throws Exception
	 */
	public int getPhotosCount() throws Exception {
		return getSpecificCount(".mmi_photos .mm_counter");
	}

}
