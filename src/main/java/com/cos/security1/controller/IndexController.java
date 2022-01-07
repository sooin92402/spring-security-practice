package com.cos.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //view 리턴
public class IndexController {
	
	//localhost:8080/
	//localhost:8080
	@GetMapping({"","/"})
	public String index() {
		// 머스테치 기본폴더 src/main/resources/
		// 뷰리졸버 설정 : templates (prefix), .mausatche(suffix)생략가능!! 
		return "index"; //src/main/resources/templates/index.mustache
	}
}
