package com.nextree.sso.server.service;

import com.nextree.sso.server.domain.AccessToken;

public interface SsoService {
	//
	AccessToken getAccessToken(String token, String clientId);
	
	String logoutAllClients(String clientId, String userName);
}
