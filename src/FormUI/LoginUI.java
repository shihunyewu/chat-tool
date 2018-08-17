package FormUI;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Dao.UserDao;

import Entity.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginUI extends Thread {

	public JFrame frame;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	public boolean islog = false;
	private UserDao ud = new UserDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void run() {
		try {
			LoginUI window = new LoginUI();
			window.frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 372, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("登录");

		textField = new JTextField();
		textField.setBounds(128, 107, 93, 21);
		textField.setColumns(10);
		lblNewLabel = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblNewLabel.setBounds(64, 110, 54, 15);
		btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ud.findUser(textField.getText())) {
					User u = new User();
					u.setUsername(textField.getText());
					ud.login(u);
					islog = true;
					MainUI mui = new MainUI(textField.getText());
					mui.frame.setVisible(true);
					frame.dispose();
				} else {
					JOptionPane.showMessageDialog(null, "该用户还未注册");
					textField.setText("");
				}
			}
		});
		
		/**
		 *  监听按回车事件
		 */
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == 10) {
					btnNewButton.doClick();
				}
			}
		});
		
		
		btnNewButton.setBounds(128, 148, 93, 23);
		this.frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// 确认提示框
				int a = JOptionPane.showConfirmDialog(null, "确定关闭吗？", "温馨提示", JOptionPane.YES_NO_OPTION);
				if (a == 0) {
					System.exit(0);
				}
			}
		});
		frame.getContentPane().add(textField);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(btnNewButton);
	}
}
