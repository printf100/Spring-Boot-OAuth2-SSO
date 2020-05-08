package com.sample.ssoclient.ssohandler.service;

import javax.servlet.http.HttpServletRequest;

import com.sample.ssoclient.ssohandler.domain.vo.Response;
import com.sample.ssoclient.ssohandler.domain.vo.TokenRequestResult;

public interface OAuthService {
	//
	TokenRequestResult requestAccessTokenToAuthServer(String code, HttpServletRequest request);

	Response logout(String tokenId, String userName);

}