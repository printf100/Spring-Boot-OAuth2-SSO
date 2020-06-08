package com.devs.sso.server.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devs.sso.server.common.sns.kakao.KakaoRestAPI;
import com.devs.sso.server.common.sns.naver.NaverLoginBO;
import com.devs.sso.server.model.domain.entity.AccessToken;
import com.devs.sso.server.model.domain.entity.Member;
import com.devs.sso.server.model.service.MemberService;
import com.devs.sso.server.model.service.SsoService;
import com.devs.sso.server.model.vo.MemberVo;
import com.devs.sso.server.model.vo.UserInfoResponse;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Controller
public class SsoController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${ssoDomain}")
	private String ssoDomain;

	@Value("${server.port}")
	private int serverPort;

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

	/*
	 * kakao 로그인
	 */
	@Autowired
	private KakaoRestAPI kakaoRestAPI;

	@RequestMapping(value = "/kakaoOauth", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session) {
		System.out.println("ssoController : @RequestMappring('kakaoOauth')");

		String access_Token = kakaoRestAPI.getAccessToken(code);
		HashMap<String, String> userInfo = kakaoRestAPI.getUserInfo(access_Token);
		System.out.println("kakao userInfo : " + userInfo + ", sns_id = " + userInfo.get("sns_id"));

		/*
		 * sns_id는 db에 sns_id 컬럼과 password(bcrypt되어) 컬럼에 저장되어 있음 -> 조회
		 */
		Member vo = memberService.snsMemberCheck(userInfo.get("sns_id"));

		System.out.println("kakao snsMemberCheck : " + vo);
		if (vo != null) { // 횐갑 되어있으면 -> 로그인 (순식간에 지나가서 안보임 ㅎ)

			System.out.println("kakao member : " + vo);

			model.addAttribute("member_id", vo.getMemberid());
			model.addAttribute("member_password", vo.getSnsid());

			return "snsloginForm";

		} else { // 횐갑 안되있으면 -> 횐갑 페이지
			model.addAttribute("snsType", "KAKAO");
			model.addAttribute("sns_id", userInfo.get("sns_id"));
			model.addAttribute("email", userInfo.get("email"));
			model.addAttribute("nickname", userInfo.get("nickname"));

			return "snsjoin";

		}
	}

	/*
	 * kakao 로그인
	 */
	@Autowired
	private NaverLoginBO naverLoginBO;

	@RequestMapping(value = "/naverOauth", method = { RequestMethod.GET, RequestMethod.POST })
	public String naverLogin(@RequestParam("code") String code, @RequestParam("state") String state, Model model,
			HttpSession session) {
		System.out.println("ssoController : @RequestMappring('naverOauth')");

		OAuth2AccessToken oauthToken;
		String apiResult = null;
		try {

			oauthToken = naverLoginBO.getAccessToken(session, code, state);
			// 로그인 사용자 정보를 읽어온다.
			apiResult = naverLoginBO.getUserProfile(oauthToken);

		} catch (IOException e) {
			System.out.println("ERROR ON NAVERLOGIN");
			e.printStackTrace();
		}

		System.out.println("\n### apiResult : " + apiResult + "\n");

		JsonParser jsonParser = new JsonParser();
		JsonObject naverRes = (JsonObject) jsonParser.parse(apiResult);

		JsonObject response = naverRes.get("response").getAsJsonObject();

		String sns_id = response.get("id").getAsString();
		String email = null;
		String name = null;
		
		if(response.get("email") != null) {
			email = response.get("email").getAsString();
		}
		
		if(response.get("name") != null) {
			name = response.get("name").getAsString();
		}

		System.out.println("sns_id : " + sns_id);
		System.out.println("email : " + email);
		System.out.println("name : " + name);

		/*
		 * sns_id는 db에 sns_id 컬럼과 password(bcrypt되어) 컬럼에 저장되어 있음 -> 조회
		 */
		Member vo = memberService.snsMemberCheck(sns_id);

		System.out.println("naver snsMemberCheck : " + vo);
		if (vo != null) { // 횐갑 되어있으면 -> 로그인 (순식간에 지나가서 안보임 ㅎ)

			System.out.println("naver member : " + vo);

			model.addAttribute("member_id", vo.getMemberid());
			model.addAttribute("member_password", vo.getSnsid());

			return "snsloginForm";

		} else { // 횐갑 안되있으면 -> 횐갑 페이지
			model.addAttribute("snsType", "NAVER");
			model.addAttribute("sns_id", sns_id);
			model.addAttribute("email", email);
			model.addAttribute("nickname", name);

			return "snsjoin";

		}
	}

	@PostMapping(value = "/snsjoin")
	public String snsjoin(MemberVo vo) {

		System.out.println("sns member join as" + vo);

		int res = memberService.snsJoin(vo);

		if (res > 0) {
			return "loginForm";
		} else {
			return "join";
		}

	}
}
