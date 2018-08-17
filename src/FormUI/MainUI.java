package FormUI;

import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ThreadPacek.ClientThread;
import ThreadPacek.OnlineThread;

public class MainUI {

	public JFrame frame;
	public String username;
	public List list = new List();
	public JLabel lblNewLabel = new JLabel("\u5728\u7EBF\u7528\u6237");
	public JButton btnNewButton = new JButton("\u5F00\u59CB\u804A\u5929");

	private OnlineThread otd;
	private ClientThread server;

	public MainUI(String username) {
		this.username = username;
		otd = new OnlineThread(this);
		// 实时刷新列表
		otd.start();
		server = new ClientThread(username);
		server.start();
		server.sendMsg(username + "已经成功连接");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 217, 392);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle(username);

		list.setBounds(10, 41, 181, 250);
		String s = "chat together";
		list.add(s);
		list.add(username);
		list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String dname = list.getSelectedItem();
				if (dname != null) {
					if (dname.equals(username + "(我)"))
						JOptionPane.showMessageDialog(null, "您不可以和自己聊天");
					else {
						if (dname.equals("chat together")) {
							if (server.uiss == null) {
								ClientUI ui = new ClientUI(username, dname, server);
								server.uiss = ui;
							}
						} else {
							boolean flag = true;
							for (ClientUI ui : ClientThread.uis) {
								if (ui.dname.equals(dname) && ui.sname.equals(username)) {
									flag = false;
									ui.setVisible(true);
								}
							}
							if (flag) {
								ClientUI ui = new ClientUI(username, list.getSelectedItem(), server);
								ClientThread.uis.add(ui);
								ui.setVisible(true);
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "您还没有选中用户");
				}
			}
		});
		lblNewLabel.setBounds(58, 20, 54, 15);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String dname = list.getSelectedItem();
				if (dname != null) {
					if (dname.equals(username + "(我)"))
						JOptionPane.showMessageDialog(null, "您不可以和自己聊天");
					else {
						if (dname.equals("chat together")) {
							if (server.uiss == null) {
								ClientUI ui = new ClientUI(username, dname, server);
								server.uiss = ui;
							}
						} else {
							boolean flag = true;
							for (ClientUI ui : ClientThread.uis) {
								if (ui.dname.equals(dname) && ui.sname.equals(username)) {
									flag = false;
									ui.setVisible(true);
								}
							}
							if (flag) {
								ClientUI ui = new ClientUI(username, list.getSelectedItem(), server);
								ClientThread.uis.add(ui);
								ui.setVisible(true);
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "您还没有选中用户");
				}
			}
		});
		btnNewButton.setBounds(10, 305, 181, 23);

		this.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示", JOptionPane.YES_NO_OPTION);
				if (a == 0) {

					otd.ud.exit(username);
					frame.dispose();
				} else {

				}
			}
		});

		frame.getContentPane().add(list);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(btnNewButton);
	}
}
