package com.cos.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.security1.config.Oauth.PrincipalOauth2UserService;


//1.코드 받기 2.엑세스토큰(권한) 3.사용자프로필 정보 가져옴 
//4-1.가져온 정보를 토대로 회원가입을 자동으로 진행시키기도 함
//4-2.(이메일, 전화번호, 이름, 아이디 ) 쇼핑몰 -> 집주소, 백화점몰 -> (vip등급, 일반등급)



@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 돤다.//활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true ) //secured 어노테이션 활성화 ,preAuthorize ,postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	//해당 메서드의 리턴되는 오프젝트를 IoC로 등록해준다.
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable();//csrf비활성화
	
		http
		.authorizeRequests()
		.antMatchers("/user/**").authenticated()
		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/loginForm")
		.loginProcessingUrl("/login")// /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
		.defaultSuccessUrl("/")
		.and()
		.oauth2Login()
		.loginPage("/loginForm")//구글로그인이 완료된 뒤의 후처리가 필요함. Tip 코드X, (엑세스 토큰 + 사용자프로필 정보 O)
		.userInfoEndpoint()
		.userService(principalOauth2UserService);
	}
	

}
