package com.ssafy.jytrip.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ssafy.jytrip.model.BoardDto;

public interface BoardService {

	HashMap<String, Object> list();

	HashMap<String, Object> write(BoardDto boardDto, HttpServletRequest request)
			throws JsonMappingException, JsonProcessingException;

}
