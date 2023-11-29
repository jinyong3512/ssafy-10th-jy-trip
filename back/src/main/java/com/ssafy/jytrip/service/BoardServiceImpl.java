package com.ssafy.jytrip.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ssafy.jytrip.model.BoardDto;
import com.ssafy.jytrip.model.mapper.BoardMapper;
import com.ssafy.jytrip.util.JwtTokenProvider;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;

	@Override
	public HashMap<String, Object> list() {

		HashMap<String, Object> resultHashMap = new HashMap<>();

		resultHashMap.put("list", boardMapper.list());

		return resultHashMap;
	}

	@Override
	public HashMap<String, Object> write(BoardDto boardDto, HttpServletRequest request)
			throws JsonMappingException, JsonProcessingException {
		HashMap<String, Object> resultHashMap = new HashMap<>();

		String accessToken = request.getHeader("accessToken");
		Map<String, String> accessTokenDecodedPayload = JwtTokenProvider.getDecodedPayload(accessToken);

		// TODO : 글 쓰기
		boardDto.setNickname(accessTokenDecodedPayload.get("nickname"));
		boardMapper.write(boardDto);

		return resultHashMap;
	}

}
