package com.ssafy.jytrip.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.jytrip.model.TripDto;
import com.ssafy.jytrip.service.MemberService;

@RestController
@RequestMapping("trip")
public class TripController {

	@Autowired
	MemberService memberService;

	@PostMapping("/saveTripList")
	public Map<String, Object> saveTripList(@RequestBody List<TripDto> tripList, HttpServletRequest request)
			throws Exception {
		HashMap<String, Object> resultHashMap = memberService.saveTripList(tripList, request);
		return resultHashMap;
	}

	@GetMapping("/getTripList")
	public Map<String, Object> getTripList(HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultHashMap = memberService.getTripList(request);
		return resultHashMap;
	}
}
