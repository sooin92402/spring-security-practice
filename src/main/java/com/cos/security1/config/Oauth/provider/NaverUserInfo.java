package com.cos.security1.config.Oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

	private Map<String,Object> attributes; //oauth2User.getAttributes() <-PrincipalOauth2UserService
	
	//response={id=VdOCwU3AFl6ZRNY4ascoSpYG_11qz7yceHUlb5RuyLw, email=sooin_92@naver.com, name=온수인}
	public NaverUserInfo( Map<String,Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getProviderId() {
		return (String) attributes.get("id");
	}

	@Override
	public String getProvider() {
		return "naver";
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getName() {
		return (String) attributes.get("name");
	}
	

}
