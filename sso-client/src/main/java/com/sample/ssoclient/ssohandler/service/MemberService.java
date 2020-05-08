package com.sample.ssoclient.ssohandler.service;

import com.sample.ssoclient.ssohandler.domain.entity.Member;

public interface MemberService {
	//
	Member getUser(String userName);

	boolean updateTokenId(String userName, String token);

}