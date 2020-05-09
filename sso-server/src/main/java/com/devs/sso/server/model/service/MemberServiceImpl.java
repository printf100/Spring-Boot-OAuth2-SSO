package com.devs.sso.server.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devs.sso.server.model.domain.entity.Member;
import com.devs.sso.server.model.domain.repository.MemberRepository;
import com.devs.sso.server.model.vo.MemberVo;

import ch.qos.logback.core.pattern.parser.Parser;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	/*
	 * 회원가입
	 */

	// 회원가입
	@Override
	public int join(MemberVo vo) {

		Member member = vo.toEntity();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMemberpassword(passwordEncoder.encode(vo.getMemberpassword()));

		return memberRepository.save(member).getMembercode();
	}

	// 이메일 유효성 검증
	@Override
	public int emailCheck(Member vo) {
		return 0;
	}

	// 아이디 유효성 검증
	@Override
	public int idCheck(Member vo) {
		return 0;
	}

	/*
	 * 로그인
	 */

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

		System.out.println(">>>>" + account);

		// 이메일 정규식
		String regExpEmail = "^[_0-9a-zA-Z-]+@[0-9a-zA-Z-]+(.[_0-9a-zA-Z-]+)*$";
		Pattern emailP = Pattern.compile(regExpEmail);
		Matcher emailM = emailP.matcher(account);

		// 핸드폰번호 정규식 (010-1234-1234)
		String regExpPhone = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$";
		Pattern phoneP = Pattern.compile(regExpPhone);
		Matcher phoneM = phoneP.matcher(account);

		Member member = null;

		if (emailM.matches()) { // 이메일 형식이라면
			System.out.println("-----------이메일");
			member = memberRepository.findByMemberemail(account);

		} else if (phoneM.matches()) { // 핸드폰번호 형식이라면
			System.out.println("-----------핸드폰번호");
			System.out.println("전 : " + account);
			account = String.join("", account.split("-"));
			System.out.println("후 : " + account);
			member = memberRepository.findByMemberphone(account);

		} else { // 아이디 형식이라면
			System.out.println("-----------아이디");
			member = memberRepository.findByMemberid(account);
		}

		System.out.println(">>>>>" + member);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("MEMBER"));

		if (member != null) {
			return new User(member.getMemberid(), member.getMemberpassword(), authorities);
		} else {
			throw new UsernameNotFoundException(account);
		}
	}

}
