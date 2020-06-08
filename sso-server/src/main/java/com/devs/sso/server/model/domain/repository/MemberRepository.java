package com.devs.sso.server.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devs.sso.server.model.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

	// 로그인
	public Member findByMemberid(String memberid);

	public Member findByMemberemail(String memberemail);

	public Member findByMemberphone(String memberphone);
	
	public Member findBySnsid(String snsid);

	public Long countByMemberemail(String memberemail);

	public Long countByMemberid(String memberid);

}
