package com.devs.sso.server.model.vo;

public class MemberProfileVo {

	private int member_code;

	private String member_img_original_name;
	private String member_img_server_name;
	private String member_img_path;

	private String member_website;
	private String member_introduce;
	private String member_gender;

	public MemberProfileVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberProfileVo(int member_code, String member_img_original_name, String member_img_server_name,
			String member_img_path, String member_website, String member_introduce, String member_gender) {
		super();
		this.member_code = member_code;
		this.member_img_original_name = member_img_original_name;
		this.member_img_server_name = member_img_server_name;
		this.member_img_path = member_img_path;
		this.member_website = member_website;
		this.member_introduce = member_introduce;
		this.member_gender = member_gender;
	}

	public int getMember_code() {
		return member_code;
	}

	public void setMember_code(int member_code) {
		this.member_code = member_code;
	}

	public String getMember_img_original_name() {
		return member_img_original_name;
	}

	public void setMember_img_original_name(String member_img_original_name) {
		this.member_img_original_name = member_img_original_name;
	}

	public String getMember_img_server_name() {
		return member_img_server_name;
	}

	public void setMember_img_server_name(String member_img_server_name) {
		this.member_img_server_name = member_img_server_name;
	}

	public String getMember_img_path() {
		return member_img_path;
	}

	public void setMember_img_path(String member_img_path) {
		this.member_img_path = member_img_path;
	}

	public String getMember_website() {
		return member_website;
	}

	public void setMember_website(String member_website) {
		this.member_website = member_website;
	}

	public String getMember_introduce() {
		return member_introduce;
	}

	public void setMember_introduce(String member_introduce) {
		this.member_introduce = member_introduce;
	}

	public String getMember_gender() {
		return member_gender;
	}

	public void setMember_gender(String member_gender) {
		this.member_gender = member_gender;
	}

	@Override
	public String toString() {
		return "MemberProfileVo [member_code=" + member_code + ", member_img_original_name=" + member_img_original_name
				+ ", member_img_server_name=" + member_img_server_name + ", member_img_path=" + member_img_path
				+ ", member_website=" + member_website + ", member_introduce=" + member_introduce + ", member_gender="
				+ member_gender + "]";
	}

}
