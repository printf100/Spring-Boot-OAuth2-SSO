package com.devs.sso.server.oauthconfig;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class AuthFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		System.out.println("AuthenticationFailureHandler : 로그인 오류!");

//		request.setAttribute("errorMessage", "로그인 정보를 확인해주세요.");
//		request.getRequestDispatcher("/loginForm").forward(request, response);
		
		request.getSession().setAttribute("errorMessage", "로그인 정보를 확인해주세요.");
		response.sendRedirect("/loginForm");
	}

}
