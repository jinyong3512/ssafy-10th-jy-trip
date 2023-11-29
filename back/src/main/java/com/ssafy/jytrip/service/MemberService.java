package com.ssafy.jytrip.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ssafy.jytrip.model.MemberDto;
import com.ssafy.jytrip.model.TokenDto;
import com.ssafy.jytrip.model.TripDto;

public interface MemberService {

	HashMap<String, Object> register(MemberDto memberDto) throws NoSuchAlgorithmException;

	HashMap<String, Object> login(MemberDto memberDto, HttpServletRequest request)
			throws NoSuchAlgorithmException, Exception;

	HashMap<String, Object> saveTripList(List<TripDto> tripList,HttpServletRequest request) throws Exception;

	HashMap<String, Object> getTripList(HttpServletRequest request) throws Exception;
}
