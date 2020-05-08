package com.sample.ssoclient.ssohandler.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.ssoclient.ssohandler.domain.entity.Member;

public interface UserRepository extends JpaRepository<Member, String> {

	public Member findByMemberid(String memberid);

}
