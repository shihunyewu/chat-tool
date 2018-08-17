package FormUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Entity.Chat;
import Dao.ChatDao;
import Test.Log;
import ThreadPacek.ClientThread;
import Utils.MsgUtils;

public class ClientUI extends JFrame {
	private static final long serialVersionUID = 1L;
	public JButton btSend;
	public JButton btFind;
	public JTextField tfSend;
	public JTextField tfIP;
	public JTextField tfPost;
	public JTextArea taShow;
	public ClientThread server;
	public String sname;
	public String dname;
	public ChatDao cd = new ChatDao();

	public ClientUI(String sname, String dname, ClientThread server) {

		this.server = server;
		this.sname = sname;
		this.dname = dname;

		btSend = new JButton("发送信息");
		btFind = new JButton("查看聊天历史");
		tfSend = new JTextField(20);
		tfIP = new JTextField(10);
		tfPost = new JTextField(5);
		taShow = new JTextArea();

		btSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 调用 用户线程中的 发送信息线程
				if (!tfSend.getText().equals("")) {
					server.sendMsg(dname + "￥" + sname + "￥" + tfSend.getText());
					taShow.append(cd.getTime().substring(0, 19) + "\n" + sname + " : " + tfSend.getText() + "\n");
					Chat chat = new Chat();
					chat.setDname(dname);
					chat.setSname(sname);
					chat.setMessage(tfSend.getText());
					cd.addChat(chat);
					tfSend.setText("");
				} else
					JOptionPane.showMessageDialog(null, "发送的消息不能为空");

			}
		});

		tfSend.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();

				if (code == 10) {
					btSend.doClick();
				}
			}
		});
		btFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Chat chat = new Chat();
				chat.setDname(dname);
				chat.setSname(sname);
				taShow.setText(MsgUtils.getChats(chat));

			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示", JOptionPane.YES_NO_OPTION);
				if (a == 0) {
					if (dname.equals("chat together")) {
						ClientThread.uiss = null;
					} else
						try {
							Log.writeLog("源用户：" + sname + "; 目标用户：" + dname);
							if (ClientThread.uis.size() > 0)
								for (ClientUI cui : ClientThread.uis) {
									if (sname.equals(cui.sname) && dname.equals(cui.dname)) {
										Log.writeLog(ClientThread.uis.remove(cui) + "");
									}
								}
						} catch (java.util.ConcurrentModificationException e1) {

						} finally {
							dispose();
						}
				}

			}
		});
		JPanel top = new JPanel(new FlowLayout());
		top.add(tfSend);
		top.add(btSend);
		top.add(btFind);
		this.add(top, BorderLayout.SOUTH);
		final JScrollPane sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(this.taShow);
		this.taShow.setEditable(false);
		this.add(sp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(480, 360);
		this.setLocation(600, 200);
		this.setVisible(true);
		if (dname.equals("chat together")) {
			this.setTitle("群聊");
			server.sendMsg(dname + "￥" + " " + "￥" + sname + "加入群聊");
		} else
			this.setTitle(sname + "正在和" + dname + "聊天");
	}

}
