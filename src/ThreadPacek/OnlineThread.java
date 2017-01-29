package ThreadPacek;

import java.util.ArrayList;
import java.util.List;

import Dao.UserDao;
import Entity.User;
import FormUI.MainUI;

public class OnlineThread extends Thread {

	MainUI mui = null;
	public UserDao ud = new UserDao();
	public ArrayList<String> unames = new ArrayList<String>();

	public OnlineThread(MainUI mui) {
		// TODO Auto-generated constructor stub
		this.mui = mui;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		subrun();
	}
	public int findsub(String username){
		for(int i =0;i<mui.list.getItemCount();i++){
			if(username.equals(mui.list.getItem(i)))
				return i;
		}
		return -1;
	}
	public void subrun(){
		while (true) {
				//mui.list.removeAll();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unames = (ArrayList<String>) ud.findAllUser();
				for (String str : unames) {
					if(str.equals(mui.username)){
						if(findsub(mui.username)!=-1)
						mui.list.delItem(findsub(mui.username));
						if(findsub(mui.username+"(Œ“)")==-1)
						mui.list.add(mui.username+"(Œ“)");
					}else
					{
						if(findsub(str)==-1)
						mui.list.add(str);
					}
				}
				for(int i =0;i<mui.list.getItemCount();i++){
					
					if(!mui.list.getItem(i).equals(mui.username+"(Œ“)")&&!unames.contains(mui.list.getItem(i)))
						mui.list.delItem(i);
				}
				
		}
	}
}
