package Test;

import java.sql.SQLException;

import javax.swing.JFrame;

import FormUI.LoginUI;
import FormUI.ServerUI;

public class Test {

	public static void main(String[] args) throws SQLException {
		ServerUI sui = new ServerUI();
		sui.setVisible(true);
		sui.btStart.doClick();
		sui.setExtendedState(JFrame.ICONIFIED);
		for(int i =0;i<3;i++){
			LoginUI li = new LoginUI();
			li.start();
		}
	}

}
