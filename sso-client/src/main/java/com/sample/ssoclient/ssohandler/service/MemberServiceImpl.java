package com.sample.ssoclient.ssohandler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.ssoclient.ssohandler.domain.entity.Member;
import com.sample.ssoclient.ssohandler.domain.repository.UserRepository;

@Service
public class MemberServiceImpl implements MemberService {
	//
	@Autowired
	private UserRepository repository;

	@Override
	public Member getUser(String userName) {
		//
		return repository.findByMemberid(userName);
	}

	@Override
	public boolean updateTokenId(String userName, String tokenId) {
		//
		Member member = repository.findByMemberid(userName);
		member.setTokenId(tokenId);

		repository.save(member);
		return true;
	}
}
