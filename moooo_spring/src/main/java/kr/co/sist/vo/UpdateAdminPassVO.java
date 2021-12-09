package kr.co.sist.vo;

public class UpdateAdminPassVO {
	private String admin_id, before_pass, new_pass;

	public String getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
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
