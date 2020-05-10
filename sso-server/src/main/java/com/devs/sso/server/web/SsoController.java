package com.devs.sso.server.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.devs.sso.server.model.domain.entity.AccessToken;
import com.devs.sso.server.model.service.MemberService;
import com.devs.sso.server.model.service.SsoService;
import com.devs.sso.server.model.vo.MemberVo;
import com.devs.sso.server.model.vo.UserInfoResponse;

@Controller
public class SsoController {
	//
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SsoService ssoService;

	@Autowired
	private MemberService memberService;

	@RequestMapping(value = "/userInfo", method = RequestMethod.POST)
	@ResponseBody
	public UserInfoResponse userInfo(@RequestParam(name = "token") String token,
			@RequestParam(name = "clientId") String clientId) {
		//
		log.debug("\n## in user(). token : {}, clientId : {}", token, clientId);
		AccessToken accessToken = ssoService.getAccessToken(token, clientId);

		log.debug("\n## accessToken : '{}'", accessToken);

		UserInfoResponse response = new UserInfoResponse();
		if (accessToken == null) {
			//
			response.setResult(false);
			response.setMessage("사용자 정보를 조회할 수 없습니다.");
		} else {
			//
			response.setUserName(accessToken.getUserName());
		}

		return response;
	}

	@RequestMapping(value = "/userLogout", method = RequestMethod.GET)
	public String userLogout(@RequestParam(name = "clientId") String clientId, HttpServletRequest request) {
		//
		String userName = request.getRemoteUser();
		log.debug("\n## userLogout() : {}, {}", clientId, userName);
		String baseUri = ssoService.logoutAllClients(clientId, userName);

		request.getSession().invalidate();

		return "redirect:" + baseUri;
	}

	// 회원가입
	@PostMapping(value = "/join")
	public String join(MemberVo vo) {

		System.out.println("member join as" + vo);

		int res = memberService.join(vo);

		if (res > 0) {
			return "loginForm";
		} else {
			return "join";
		}

	}

	@ResponseBody
	@PostMapping(value = "/emailCheck")
	public Map<String, Boolean> emailCheck(@RequestBody Map<String, String> email) {
		System.out.println(email.get("memberemail"));
		Map<String, Boolean> map = new HashMap<>();

		Boolean check = false;

		Long res = memberService.emailCheck(email.get("memberemail"));
		System.out.println(res);
		if (res > 0) {
			check = true;
		}

		map.put("check", check);

		return map;
	}

	@ResponseBody
	@PostMapping(value = "/idCheck")
	public Map<String, Boolean> idCheck(@RequestBody Map<String, String> id) {
		System.out.println(id.get("memberid"));
		Map<String, Boolean> map = new HashMap<>();

		Boolean check = false;

		Long res = memberService.idCheck(id.get("memberid"));
		System.out.println(res);
		if (res > 0) {
			check = true;
		}

		map.put("check", check);

		return map;
	}

}
