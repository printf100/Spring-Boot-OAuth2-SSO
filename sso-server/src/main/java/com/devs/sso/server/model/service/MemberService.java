package com.devs.sso.server.model.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.devs.sso.server.model.domain.entity.Member;
import com.devs.sso.server.model.vo.MemberVo;

public interface MemberService extends UserDetailsService {

	// 로그인
	public Map<String, Object> login(Member vo);

	// 회원가입
	public int join(MemberVo vo);

	// 이메일 유효성 검증
	public int emailCheck(Member vo);

	// 아이디 유효성 검증
	public int idCheck(Member vo);

	// sns로그인
	public Member snsLogin(Member vo);

}
