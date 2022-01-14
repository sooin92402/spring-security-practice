package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터체인에 등록이 돤다.//활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true ) //secured 어노테이션 활성화 ,preAuthorize ,postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
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
		.loginPage("/loginForm")//구글로그인이 완료된 뒤의 후처리가 필요함
		;
	}
	

}
