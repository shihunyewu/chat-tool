package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.itcast.jdbc.TxQueryRunner;
import Entity.User;
import Utils.DBUtils;


public class UserDao {

	private String sql = "";
	private Connection conn = null;
	private PreparedStatement pstat = null;
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 用于初始化sql语句
	 * 
	 * @param sql
	 * @return
	 */
	public PreparedStatement init(String sql) {
		conn = DBUtils.getConnection();
		try {
			pstat = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstat;
	}

	/**
	 * 查看该用户名是否已经被用
	 * 如果存在，返回true
	 * 如果不存在，返回false
	 * @param user
	 * @return
	 */
	public boolean findUser(String username) {
		
		String sql = "select * from tbl_user where username = ? ";
		PreparedStatement pstat = init(sql);
		try {
			pstat.setString(1, username);
			ResultSet rs = pstat.executeQuery();
			DBUtils.closePStat(pstat);
			if(rs.next())
				return true;
			else
				return false;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			DBUtils.closePStat(pstat);
		}
		return false;
	}

	public void login(User u){
		String sql = "update tbl_user set isOnline = 1 where username = ?";
		try {
			qr.update(sql,u.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exit(String username){
		String sql = "update tbl_user set isOnline = 0 where username = ?";
		try {
			qr.update(sql,username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> findAllUser() {

		String sql = "select * from tbl_user where isOnline = 1";
		
		List<User> users = new ArrayList<User>();
		try {
			users = qr.query(sql, new BeanListHandler<User>(User.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<String> us = new ArrayList<String>();
		for (User user : users) {
			us.add(user.getUsername());
		}
		return us;
	}
}
