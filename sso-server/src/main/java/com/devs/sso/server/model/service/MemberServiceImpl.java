package com.devs.sso.server.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devs.sso.server.model.domain.entity.Channel;
import com.devs.sso.server.model.domain.entity.Member;
import com.devs.sso.server.model.domain.entity.MemberProfile;
import com.devs.sso.server.model.domain.repository.ChannelRepository;
import com.devs.sso.server.model.domain.repository.MemberProfileRepository;
import com.devs.sso.server.model.domain.repository.MemberRepository;
import com.devs.sso.server.model.vo.MemberVo;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MemberProfileRepository memberProfileRepository;

	@Autowired
	private ChannelRepository channelRepository;

	/*
	 * 회원가입
	 */

	// 일반 회원가입
	@Override
	@Transactional
	public int join(MemberVo vo) {

		Member member = vo.toEntity();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMemberpassword(passwordEncoder.encode(vo.getMemberpassword()));

		int savedMemberCode = memberRepository.save(member).getMembercode();

		if (savedMemberCode > 0) {

			// profile 에 member_code만 주입하여 row 생성
			MemberProfile memberProfile = new MemberProfile();
			memberProfile.setMember_code(savedMemberCode);
			int savedMemberProfileMemberCode = memberProfileRepository.save(memberProfile).getMember_code();
			System.out.println("MemberProfile inserted in member_code : " + savedMemberProfileMemberCode);

			// channel 에 member_code만 주입하여 row 생성
			Channel channel = new Channel();
			channel.setMember_code(savedMemberCode);
			channel.setChannel_type("P");
			int savedChannelMemberCode = channelRepository.save(channel).getMember_code();
			System.out.println("MemberProfile inserted in member_code : " + savedChannelMemberCode);

			return savedMemberCode;
		} else {
			return 0;
		}

	}

	// sns 회원가입
	@Override
	public int snsJoin(MemberVo vo) {

		Member member = vo.toSnsEntity();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setMemberpassword(passwordEncoder.encode(vo.getMemberpassword()));

		int savedMemberCode = memberRepository.save(member).getMembercode();

		if (savedMemberCode > 0) {

			// profile 에 member_code만 주입하여 row 생성
			MemberProfile memberProfile = new MemberProfile();
			memberProfile.setMember_code(savedMemberCode);
			int savedMemberProfileMemberCode = memberProfileRepository.save(memberProfile).getMember_code();
			System.out.println("MemberProfile inserted in member_code : " + savedMemberProfileMemberCode);

			// channel 에 member_code만 주입하여 row 생성
			Channel channel = new Channel();
			channel.setMember_code(savedMemberCode);
			channel.setChannel_type("P");
			int savedChannelMemberCode = channelRepository.save(channel).getMember_code();
			System.out.println("MemberProfile inserted in member_code : " + savedChannelMemberCode);

			return savedMemberCode;
		} else {
			return 0;
		}
	}

	// 이메일 유효성 검증
	@Override
	public Long emailCheck(String memberemail) {
		return memberRepository.countByMemberemail(memberemail);
	}

	// 아이디 유효성 검증
	@Override
	public Long idCheck(String memberid) {
		return memberRepository.countByMemberid(memberid);
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

	/*
	 * sns
	 */

	// 각 sns 별로 sns_id 를 snsid로 사용함. 이를 이용해 sns 회원가입이 되어 있는지 체크
	@Override
	public Member snsMemberCheck(String snsid) {
		Member vo = null;
		vo = memberRepository.findBySnsid(snsid);

		// sns 회원가입 여부에따라 vo가 null일 수도 있고 / 회원정보를 담고있을수도 있다.
		return vo;
	}

}
