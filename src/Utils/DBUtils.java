package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBUtils {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/sgysysdesign?characterEncoding=utf8";
	private static String user = "root";
	private static String password = "1120148431";
	
	
	public static Connection getConnection() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	public static boolean closePStat(PreparedStatement pstat) {
		try {
			if (pstat != null && pstat.isClosed()) {
				Connection conn = pstat.getConnection();
				pstat.close();
				if (conn != null && conn.isClosed())
					conn.close();
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean close(Statement stat) {
		try {
			if (stat != null && stat.isClosed()) {
				Connection conn = stat.getConnection();
				stat.close();
				if (conn != null && conn.isClosed())
					conn.close();
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean close(ResultSet rs) {
		try {
			if (rs != null && rs.isClosed()) {
				Statement stat = rs.getStatement();
				if (stat != null && stat.isClosed()) {
					Connection conn = stat.getConnection();
					rs.close();
					stat.close();
					conn.close();
				}

				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
