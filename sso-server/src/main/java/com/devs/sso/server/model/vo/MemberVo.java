package com.devs.sso.server.model.vo;

import com.devs.sso.server.model.domain.entity.Member;

public class MemberVo {

	private int membercode;
	private String memberemail;
	private String memberphone;
	private String membername;
	private String memberid;
	private String memberpassword;
	private String snstype;
	private String snsid;

	public int getMembercode() {
		return membercode;
	}

	public void setMembercode(int membercode) {
		this.membercode = membercode;
	}

	public String getMemberemail() {
		return memberemail;
	}

	public void setMemberemail(String memberemail) {
		this.memberemail = memberemail;
	}

	public String getMemberphone() {
		return memberphone;
	}

	public void setMemberphone(String memberphone) {
		this.memberphone = memberphone;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getMemberpassword() {
		return memberpassword;
	}

	public void setMemberpassword(String memberpassword) {
		this.memberpassword = memberpassword;
	}

	public String getSnstype() {
		return snstype;
	}

	public void setSnstype(String snstype) {
		this.snstype = snstype;
	}

	public String getSnsid() {
		return snsid;
	}

	public void setSnsid(String snsid) {
		this.snsid = snsid;
	}

	@Override
	public String toString() {
		return "MemberVo [membercode=" + membercode + ", memberemail=" + memberemail + ", memberphone=" + memberphone
				+ ", membername=" + membername + ", memberid=" + memberid + ", memberpassword=" + memberpassword
				+ ", snstype=" + snstype + ", snsid=" + snsid + "]";
	}

	public Member toEntity() {
		return new Member(memberemail, memberphone, membername, memberid, memberpassword);
	}
	
	public Member toSnsEntity() {
		return new Member(memberemail, memberphone, membername, memberid, memberpassword, snstype, snsid);
	}
}
