package com.sample.ssoclient.ssohandler.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.ssoclient.ssohandler.domain.entity.Member;
import com.sample.ssoclient.ssohandler.domain.vo.Response;
import com.sample.ssoclient.ssohandler.domain.vo.TokenRequestResult;
import com.sample.ssoclient.ssohandler.service.OAuthService;

@Controller
public class SsoController {
	//
	private static final Logger log = LoggerFactory.getLogger(SsoController.class);

	@Value("${systemName}")
	private String systemName;

	@Value("${ssoServerPort}")
	private String ssoServerPort;

	@Value("${server.port}")
	private int serverPort;

	@Autowired
	private OAuthService oauthService;

	private String getOAuthClientId() {
		//
		return systemName + "_id";
	}

	private String getOAuthRedirectUri() {
		//
//		redirect uri 체크
//		return String.format("http://localhost:%d/oauthCall", serverPort);
		return String.format("http://localhost:%d/oauthCallback", serverPort);
	}

	@RequestMapping(value = "/oauthCallback", method = RequestMethod.GET)
	public String oauthCallback(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state,
			HttpServletRequest request, ModelMap map) {
		//
//		state 체크
//		String oauthState = "fdsfs";
		String oauthState = (String) request.getSession().getAttribute("oauthState");
		request.getSession().removeAttribute("oauthState");
		log.debug("\n## code, oauthState, state : {}, {}, {}", code, oauthState, state);

		TokenRequestResult tokenRequestResult = null;

		if (oauthState == null || oauthState.equals(state) == false) {
			//
			tokenRequestResult = new TokenRequestResult();
			tokenRequestResult.setError("not matched state");
		} else {
			//
//			코드 체크
//			tokenRequestResult = oauthService.requestAccessTokenToAuthServer("abcd", request);
			tokenRequestResult = oauthService.requestAccessTokenToAuthServer(code, request);
		}

		if (tokenRequestResult.getError() == null) {
			//
			return "redirect:/me";
		} else {
			//
			map.put("result", tokenRequestResult);

			return "authResult";
		}
	}

	@RequestMapping(value = "/sso", method = RequestMethod.GET)
	public String sso(HttpServletRequest request) {

		System.out.println("----------------------------------------/sso-----------------------------");
		//
		String state = UUID.randomUUID().toString();
		request.getSession().setAttribute("oauthState", state);

		StringBuilder builder = new StringBuilder();
		builder.append("redirect:");
		builder.append("http://localhost:" + ssoServerPort + "/oauth/authorize");
		builder.append("?response_type=code");
		builder.append("&client_id=");
		builder.append(getOAuthClientId());
		builder.append("&redirect_uri=");
		builder.append(getOAuthRedirectUri());
		builder.append("&scope=");
		builder.append("read");
		builder.append("&state=");
		builder.append(state);

		System.out.println(builder.toString());
		return builder.toString();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		//
		return "redirect:http://localhost:" + ssoServerPort + "/userLogout?clientId=" + getOAuthClientId();
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Response logoutFromAuthServer(@RequestParam(name = "tokenId") String tokenId,
			@RequestParam(name = "userName") String userName) {
		//
		Response response = oauthService.logout(tokenId, userName);

		log.debug("\n## logout secceeded {}", userName);
		return response;
	}

}
