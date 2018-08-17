package ThreadPacek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import FormUI.ClientUI;
import Test.Log;
import Utils.MsgUtils;

public class ClientThread extends Thread {

	ClientUI ui;

	public String username;
	public static List<ClientUI> uis = new ArrayList<ClientUI>();
	public List<String> snames = new ArrayList<String>();
	private Socket client;
	private BufferedReader reader;
	private PrintWriter writer;
	private int port = 9654;
	

	public static ClientUI uiss;

	/*
	 * 初始化线程，连接
	 */
	public ClientThread(String username) {
		this.username = username;
		try {
			client = new Socket("127.0.0.1", port);// 这里设置连接服务器端的IP的端口
			println("连接服务器成功:端口" + port);
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			println("连接服务器失败：端口" + port);
			println("请打开服务器");
			JOptionPane.showMessageDialog(null, "可能还未运行服务器", "服务器错误", 0, null);
			System.exit(0);
			println(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 *  接收消息
	 */
	public void run() {
		String msg = "";
		while (true) {
			try {
				msg = reader.readLine();
			} catch (IOException e) {
				println("服务器断开连接");
				break;
			}
			if (msg != null && msg.trim() != "") {
				println(msg);
			}
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 */
	public void sendMsg(String msg) {
		try {
			writer.println(msg);
		} catch (Exception e) {
			println(e.toString());
		}
	}

	/**
	 * 将信息添加到相应的客户端
	 * 
	 * @param s
	 */
	public void println(String s) {
		if (s != null) {
			String uses[] = MsgUtils.splitMsg(s);
			int flag = 0;
			if (uses != null) {
				if (uiss != null && uses[0].equals("chat together")) {
					Log.writeLog("群聊信息：" + uses[2]);
					if (uiss.dname.equals("chat together") && !uses[1].equals(uiss.sname)) {
						uiss.taShow
								.append(uiss.cd.getTime().substring(0, 19) + "\n" + uses[1] + " : " + uses[2] + "\n");
					}
				}
				
				if (uses[0].equals(username)) {
					for (ClientUI clu : ClientThread.uis) {
						if (clu.sname.equals(uses[0]) && clu.dname.equals(uses[1])) {
							flag = 1;
							clu.taShow.append(
									clu.cd.getTime().substring(0, 19) + "\n" + uses[1] + " : " + uses[2] + "\n");
						}
					}
					if (flag == 0 && JOptionPane.showConfirmDialog(null, uses[1] + "想要和你聊天，您是否接受", "温馨提示",
							JOptionPane.YES_NO_OPTION) == 0) {
						ClientUI cui = new ClientUI(uses[0], uses[1], this);
						ClientThread.uis.add(cui);
						cui.taShow.append(cui.cd.getTime().substring(0, 19) + "\n" + uses[1] + ":" + uses[2] + "\n");
					}
				}
			}

			System.out.println(s + "\n");
		}
	}

}