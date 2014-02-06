package com.asaskevich.vkapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class that work with list of friends
 * @author Alex Saskevich
 */
public class VK_Friends {
	private final String defaultURL = "https://m.vk.com/friends";
	private Map<String, String> cookies;
	private Document loadedDocument;
	private int friendsCount;
	private int onlineFriendsCount;
	private List<VK_Friend> friends;

	/**
	 * Create new instance of class
	 * @param cookies authorization tokens, that was received by
	 *            {@link VK_Auth.auth}
	 */
	public VK_Friends(Map<String, String> cookies) throws Exception {
		this.cookies = cookies;
		this.friends = new ArrayList<VK_Friend>();
		updateData();
		getTabCounters();
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
	 * Get tab counters - count of friends and count of online friends
	 */
	private void getTabCounters() {
		Elements tabs = loadedDocument.select(".tab_item .tab_counter");
		friendsCount = tabs.size() > 0 ? Integer.valueOf(tabs.get(0).text())
				: 0;
		onlineFriendsCount = tabs.size() > 1 ? Integer.valueOf(tabs.get(1)
				.text()) : 0;
	}

	/**
	 * Load list of friends from {@link https://m.vk.com/friends?offset=N} and
	 * parse it
	 * @param offset
	 * @throws Exception
	 */
	private void getFriendList(int offset) throws Exception {
		// Load page with custom offset, usually retrieve next 20 friends
		Connection.Response connection = null;
		connection = Jsoup.connect(defaultURL + "?offset=" + offset)
				.cookies(cookies).execute();
		Document document = connection.parse();
		Elements list = document.select(".simple_fit_item");
		// User has no friends :(
		if (list.size() == 0) {
			return;
		}
		// Retrieve list of friends for loaded page
		for (Element next : list) {
			Element photo = next.select(".si_img").get(0);
			Element owner = next.select(".si_owner").get(0);
			Element id = next.select(".si_links a[href]").get(0);
			Elements status = next.select("b");
			// Extract data
			String photoURL = photo.attr("src");
			String name = owner.text();
			int idUser = Integer.valueOf(id.attr("href").substring(6));
			// TODO edit this
			// User status - on-line or off-line
			VK_Status statusUser = status.size() == 0 ? VK_Status.OFFLINE
					: (status.get(0).attr("class").contains("mlvi") ? VK_Status.ONLINE_MOBILE
							: VK_Status.ONLINE);
			// Creating new object and adding it on list
			VK_Friend nextFriend = new VK_Friend(idUser, name, photoURL,
					statusUser);
			friends.add(nextFriend);
		}
	}

	/**
	 * Return count of friends
	 * @return integer value of count
	 */
	public int getFriendsCount() {
		return friendsCount;
	}

	/**
	 * Return count of online friends
	 * @return integer value of count
	 */
	public int getOnlineFriendsCount() {
		return onlineFriendsCount;
	}

	/**
	 * Update information about friends
	 * @throws Exception
	 */
	private void getAllFriends() throws Exception {
		if (friends == null) {
			friends = new ArrayList<VK_Friend>();
		}
		friends.clear();
		// We need last information about count of friends
		updateData();
		getTabCounters();
		// In every full page showed only 20 friends
		int step = 20;
		for (int offset = 0; offset <= friendsCount; offset += step) {
			getFriendList(offset);
		}
	}

	/**
	 * Get list of your friends
	 * @return
	 * @throws Exception
	 */
	public List<VK_Friend> getFriendList() throws Exception {
		// List empty or not initialized
		if (friends == null || friends.size() == 0) {
			getAllFriends();
		}
		return friends;
	}
}
