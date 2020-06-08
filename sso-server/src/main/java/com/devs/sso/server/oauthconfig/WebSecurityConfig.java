package com.devs.sso.server.oauthconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.devs.sso.server.model.service.MemberService;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MemberService service;
	
	@Autowired
	private AuthFailureHandler authFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//
		http.authorizeRequests()//
				.antMatchers(// security 필터를 거치지 않고 접근 허용
						"/webjars/**", //
						"/resources/**", //
						//
						"/home", //
						"/userInfo", //
						"/signup", //
						"/join", //
						"/emailCheck", //
						"/idCheck", //
						"/kakaoOauth", "/naverOauth", "/snsjoin")//

				.permitAll()//
				.anyRequest().authenticated()//
				.and()//

				.formLogin()//
				.loginProcessingUrl("/loginProcess")//
				.loginPage("/snsloginForm")//
				.loginPage("/loginForm")//
				.usernameParameter("member_id").passwordParameter("member_password")//
				.permitAll()//
				.failureHandler(authFailureHandler)//
				.and()//

				.csrf()//
				.requireCsrfProtectionMatcher(new AntPathRequestMatcher("/user*"))//
				.disable()//

				.logout()//
				.permitAll();//
	}

	@Bean
	public BCryptPasswordEncoder pwEncode() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(service).passwordEncoder(pwEncode());
	}
}
