package com.devs.sso.server.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devs.sso.server.model.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// 로그인
	public Member findByMemberemailAndMemberpassword(String memberemail, String memberpassword);

	public Member findByMemberphoneAndMemberpassword(String memberphone, String memberpassword);

	public Member findByMemberidAndMemberpassword(String memberid, String memberpassword);

	public Member findByMemberid(String memberid);

}
