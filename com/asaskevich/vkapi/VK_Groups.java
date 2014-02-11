package com.asaskevich.vkapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class VK_Groups {
	private final String defaultURL = "https://m.vk.com/groups";
	private Map<String, String> cookies;
	private List<VK_Group> list;

	/**
	 * Create new instance of class
	 * @param cookies authorization tokens, that was received by {@link VK_Auth.auth}
	 * @throws Exception
	 */
	public VK_Groups(Map<String, String> cookies) throws Exception {
		this.cookies = cookies;
		this.list = new ArrayList<VK_Group>();
	}

	/**
	 * Skip {@code countToSkip} items and load next 20 groups
	 * @param countToSkip
	 * @return count of readed items
	 * @throws Exception
	 */
	private int loadGroupList(int countToSkip) throws Exception {
		Connection.Response connection = null;
		connection = Jsoup.connect(defaultURL + "?offset=" + countToSkip).cookies(cookies).execute();
		Document doc = connection.parse();
		Elements items = doc.select(".simple_fit_item");
		for (Element next : items) {
			String name = next.select(".si_owner").text();
			String logoURL = next.select(".si_img").get(0).attr("src");
			String id = next.attr("href");
			String temp = next.select(".si_slabel").text();
			// TODO deleting spaces from string representation of integer
			temp = temp.substring(0, temp.lastIndexOf(' '));
			temp = temp.replaceAll("\\s", "");
			int memberCount = Integer.valueOf(temp);
			VK_Group group = new VK_Group(id, name, logoURL, memberCount);
			list.add(group);
		}
		return items.size();
	}

	/**
	 * Load all groups from user profile
	 * @throws Exception
	 */
	private void getAllGroups() throws Exception {
		if (list == null) {
			list = new ArrayList<VK_Group>();
		}
		list.clear();
		int offset = 0;
		int step = 20;
		while (loadGroupList(offset) != 0) {
			offset += step;
		}
	}

	/**
	 * Load list of 20 last groups
	 * @return list that contains last groups
	 * @throws Exception
	 */
	public List<VK_Group> getLastGroups() throws Exception {
		list.clear();
		loadGroupList(0);
		return list;
	}

	/**
	 * Load all groups from profile and return it in list
	 * @return
	 * @throws Exception
	 */
	public List<VK_Group> getGroups() throws Exception {
		getAllGroups();
		return list;
	}
}
