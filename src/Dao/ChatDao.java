package Dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import Entity.Chat;
import cn.itcast.jdbc.TxQueryRunner;

public class ChatDao {
	private QueryRunner qr = new TxQueryRunner();

	public String getTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		return df.format(new Date());
	}

	public void addChat(Chat chat) {
		String sql = "insert into tbl_chat(dname,sname,message,date)values(?,?,?,?)";
		chat.setDate(getTime());
		Object params[] = { chat.getDname(), chat.getSname(),
				chat.getMessage(), chat.getDate() };
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Chat> findChat(Chat chat) {
		String sql;
		if (chat.getDname().equals("chat together")) {
			sql = "select * from tbl_chat where dname = 'chat together'";
			try {
				return qr.query(sql, new BeanListHandler<Chat>(Chat.class));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			sql = "select * from tbl_chat where dname = ? and sname = ? or dname = ? and sname = ?";
			try {
				return qr.query(sql, new BeanListHandler<Chat>(Chat.class),
						chat.getDname(), chat.getSname(), chat.getSname(),
						chat.getDname());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
