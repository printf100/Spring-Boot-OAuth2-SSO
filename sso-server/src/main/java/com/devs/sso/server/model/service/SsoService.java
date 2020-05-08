package com.devs.sso.server.model.service;

import com.devs.sso.server.model.domain.entity.AccessToken;

public interface SsoService {
	//
	AccessToken getAccessToken(String token, String clientId);
	
	String logoutAllClients(String clientId, String userName);
}
