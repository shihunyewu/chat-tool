package com.sgy.pojo;

public class Chat {
	public String sname;
	public String dname;
	public int id;
	public String message;
	public String date;

	public Chat() {
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Chat [sname=" + sname + ", dname=" + dname + ", id=" + id
				+ ", message=" + message + ", date=" + date + "]";
	}
}
