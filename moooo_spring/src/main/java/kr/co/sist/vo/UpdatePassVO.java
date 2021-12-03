package kr.co.sist.vo;

public class UpdatePassVO {
	private String user_id, before_pass, new_pass;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNew_pass() {
		return new_pass;
	}

	public void setNew_pass(String new_pass) {
		this.new_pass = new_pass;
	}

	public String getBefore_pass() {
		return before_pass;
	}

	public void setBefore_pass(String before_pass) {
		this.before_pass = before_pass;
	}
	
}
