package com.angel.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 陈明
 * @date 2019/9/5 10:55
 */
@RestController
public class IndexController
{
	@GetMapping("hello")
	public String hello() {
		return "hello spring security";
	}
	
	@GetMapping("index")
	public Object index(Authentication authentication) {
		return authentication;
	}
	
	@PostMapping("/post")
	public void post(@RequestParam String schoolId)
	{
		System.out.println(schoolId);
	}
}
