package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadUserByUsername 함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	// 시큐리티 session(내부Authentication(내부 UserDetails))
	// 함수 종료 시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //loginForm의 input타입 속성 name의 값이 반드시 username이어야함 
		User userEntity = userRepository.findByUsername (username);
		if(userEntity != null) {
			
			System.out.println(new PrincipalDetails(userEntity));
			 
			return new PrincipalDetails(userEntity); //로그인 완료
		}
		return null;
	}
	
	

}
