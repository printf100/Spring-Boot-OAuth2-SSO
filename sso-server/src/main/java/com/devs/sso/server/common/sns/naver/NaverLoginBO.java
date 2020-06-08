package com.devs.sso.server.common.sns.naver;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;

@Component
public class NaverLoginBO {

	@Value("${ssoDomain}")
	private String ssoDomain;

	@Value("${server.port}")
	private int serverPort;

	@Value("${social-link.naver-rest-api-key}")
	private String CLIENT_ID;

	@Value("${social-link.naver-rest-api-secret}")
	private String CLIENT_SECRET;

	@Value("${social-link.naver-oauth-state}")
	private String session_state;

	private static String REDIRECT_URI;
	private static String PROFILE_API_URL;

	public NaverLoginBO() {
	}

	/* 네이버아이디로 Callback 처리 및 AccessToken 획득 Method */
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state) throws IOException {
		REDIRECT_URI = "http://" + ssoDomain + ":" + serverPort + "/naverOauth";
		PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
		
		System.out.println("ssoDomain : " + ssoDomain);
		System.out.println("serverPort : " + serverPort);
		System.out.println("CLIENT_ID : " + CLIENT_ID);
		System.out.println("CLIENT_SECRET : " + CLIENT_SECRET);
		System.out.println("session_state : " + session_state + " == " + "state : " + state);
		System.out.println("REDIRECT_URI : " + REDIRECT_URI);
		System.out.println("PROFILE_API_URL : " + PROFILE_API_URL);

		/* Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인 */
		if (StringUtils.pathEquals(session_state, state)) {

			OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
					.callback(REDIRECT_URI).state(state).build(NaverLoginApi.instance());

			/* Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득 */
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code);
			return accessToken;
		}
		return null;
	}

	/* Access Token을 이용하여 네이버 사용자 프로필 API를 호출 */
	public String getUserProfile(OAuth2AccessToken oauthToken) throws IOException {
		REDIRECT_URI = "http://" + ssoDomain + ":" + serverPort + "/naverOauth";
		PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
		
		OAuth20Service oauthService = new ServiceBuilder().apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET)
				.callback(REDIRECT_URI).build(NaverLoginApi.instance());

		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);
		oauthService.signRequest(oauthToken, request);
		Response response = request.send();
		return response.getBody();
	}

}
