package com.devs.sso.server.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.devs.sso.server.model.vo.MemberVo;

public interface MemberService extends UserDetailsService {

	// 회원가입
	public int join(MemberVo vo);

	// 이메일 유효성 검증
	public Long emailCheck(String memberemail);

	// 아이디 유효성 검증
	public Long idCheck(String memberid);

}
