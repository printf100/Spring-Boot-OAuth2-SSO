package com.devs.sso.server.model.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.devs.sso.server.model.domain.entity.Member;
import com.devs.sso.server.model.vo.MemberVo;

public interface MemberService extends UserDetailsService {

	// 회원가입
	public int join(MemberVo vo);

	// 이메일 유효성 검증
	public Long emailCheck(String memberemail);

	// 아이디 유효성 검증
	public Long idCheck(String memberid);

	/*
	 * sns
	 */
	// 각 sns 별로 sns_id 를 memberpassword , snsid로 사용함. 이를 이용해 sns 회원가입이 되어 있는지 체크
	public Member snsMemberCheck(String snsid);

	// sns 회원가입
	public int snsJoin(MemberVo vo);

}
