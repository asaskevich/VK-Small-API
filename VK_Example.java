import java.util.List;
import java.util.Map;

import com.asaskevich.vkapi.VK_Auth;
import com.asaskevich.vkapi.VK_Friend;
import com.asaskevich.vkapi.VK_Friends;
import com.asaskevich.vkapi.VK_Mail;

public class VK_Example {
	public static void main(String[] args) {
		try {
			// Authorization
			Map<String, String> cookies;
			cookies = VK_Auth.auth(args[0], args[1]);
			// Retrieve friend list
			VK_Friends friends = new VK_Friends(cookies);
			List<VK_Friend> list = friends.getFriendList();
			// Get first friend
			VK_Friend first = list.get(0);
			// Get him ID and name
			int id = first.getId();
			String name = first.getName();
			VK_Mail mail = new VK_Mail(cookies);
			// Send message
			// Hello, John Smith!
			mail.sendMessage(id, "Hello, " + name + "!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
