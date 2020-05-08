package com.devs.sso.server.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

//	@Override
//	public Map<String, Object> login(MemberVo vo) {
//		Map<String, Object> map = null;
//
//		MemberVo mres = null;
//
//		if (vo.getMemberemail() != null) {
//			mres = memberRepository.findByMemberemailAndMemberpassword(vo.getMemberemail(), vo.getMemberpassword());
//		} else if (vo.getMemberphone() != null) {
//			mres = memberRepository.findByMemberphoneAndMemberpassword(vo.getMemberphone(), vo.getMemberpassword());
//		} else {
//			mres = memberRepository.findByMemberidAndMemberpassword(vo.getMemberid(), vo.getMemberpassword());
//		}
//
//		System.out.println(mres);
//		if (mres != null) {
//			map = new HashMap<>();
//			map.put("login", mres);
//			return map;
//		} else {
//			return map; // 멤버 정보를 가져오지 못했다면 null인 객체를 return
//		}
//	}

	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

		System.out.println(">>>>" + account);

		Member member = memberRepository.findByMemberid(account);

		System.out.println(">>>>>" + member);

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("MEMBER"));

		if (member != null) {
			return new User(member.getMemberid(), member.getMemberpassword(), authorities);
		} else {
			throw new UsernameNotFoundException(account);
		}
	}

	@Override
	public int join(MemberVo vo) {

		Member member = vo.toEntity();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMemberpassword(passwordEncoder.encode(vo.getMemberpassword()));

		return memberRepository.save(member).getMembercode();
	}

	@Override
	public Map<String, Object> login(Member vo) {
		return null;
	}

	@Override
	public int emailCheck(Member vo) {
		return 0;
	}

	@Override
	public int idCheck(Member vo) {
		return 0;
	}

	@Override
	public Member snsLogin(Member vo) {
		return null;
	}

}
