package Utils;

import java.util.ArrayList;
import java.util.List;

import Dao.ChatDao;
import Entity.Chat;

public class MsgUtils {
	public static String[] splitMsg(String msg){
		String s[] = null;
		if(msg.contains("гд"))
		 s= msg.split("гд",3);
		return s;
	}
	public static String getChats(Chat chat){
		ChatDao cd = new ChatDao();
		List<Chat> cts = new ArrayList<Chat>();
		String str = "";
		cts = cd.findChat(chat);
		for (Chat ct : cts) {
			str =str+ct.date.substring(0, 19)+"\n"+ ct.sname+" : "+ct.message+"\n";
		}
		return str;
	}
}
