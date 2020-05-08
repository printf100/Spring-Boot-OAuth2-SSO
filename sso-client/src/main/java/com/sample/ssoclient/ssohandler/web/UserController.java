package com.sample.ssoclient.ssohandler.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.ssoclient.ssohandler.domain.entity.Member;
import com.sample.ssoclient.ssohandler.service.MemberService;

@Controller
public class UserController {
	//
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private MemberService service;

	@Value("${systemName}")
	private String systemName;

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public String me(HttpServletRequest request, ModelMap map) {
		//
		map.put("systemName", systemName);
		String viewName = "me";

		Member member = (Member) request.getSession().getAttribute("user");
		log.debug("\n## user in session : {}", member);

		if (member == null) {
			//
			return viewName;
		}

		member = service.getUser(member.getMemberid());
		log.debug("\n## user : {}", member);
		if (member.getTokenId() == null) {
			//
			request.getSession().removeAttribute("user");
		} else {
			//
			map.put("user", member);
		}

		return viewName;
	}
}
