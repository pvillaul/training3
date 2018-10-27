package com.example.training2.Models;

public class Error {
	private String msg;
	private String msg2;
	private String path;
	private String pathtext;
	public Error(String msg, String msg2, String path, String pathtext) {
		super();
		this.msg = msg;
		this.msg2 = msg2;
		this.path = path;
		this.pathtext = pathtext;
	}
	public Error() {
		super();
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsg2() {
		return msg2;
	}
	public void setMsg2(String msg2) {
		this.msg2 = msg2;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPathtext() {
		return pathtext;
	}
	public void setPathtext(String pathtext) {
		this.pathtext = pathtext;
	}
}