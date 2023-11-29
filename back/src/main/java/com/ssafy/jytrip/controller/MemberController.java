package com.ssafy.jytrip.controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ssafy.jytrip.model.MemberDto;
import com.ssafy.jytrip.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@PostMapping("register")
	public ResponseEntity<?> register(@RequestBody MemberDto memberDto) throws NoSuchAlgorithmException {
		HashMap<String, Object> resultHashMap = memberService.register(memberDto);

		if (resultHashMap.containsKey("errorMessage"))
			return ResponseEntity.status(444).body(resultHashMap);
		else
			return ResponseEntity.ok().body(resultHashMap);

	}

	@PostMapping("login")
	public ResponseEntity<?> login(@RequestBody MemberDto memberDto, HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultHashMap = memberService.login(memberDto, request);

		if (resultHashMap.containsKey("errorMessage"))
			return ResponseEntity.status(444).body(resultHashMap);
		else
			return ResponseEntity.ok().body(resultHashMap);
	}

}
