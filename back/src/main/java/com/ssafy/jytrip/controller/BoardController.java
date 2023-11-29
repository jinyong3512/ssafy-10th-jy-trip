package com.ssafy.jytrip.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ssafy.jytrip.model.BoardDto;
import com.ssafy.jytrip.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardService boardService;

	@GetMapping("list")
	public ResponseEntity<?> list() {

		HashMap<String, Object> resultHashMap = boardService.list();

		if (resultHashMap.containsKey("errorMessage"))
			return ResponseEntity.status(444).body(resultHashMap);
		else
			return ResponseEntity.ok().body(resultHashMap);
	}

	@PostMapping("write")
	public HashMap<String, Object> write(@RequestBody BoardDto boardDto, HttpServletRequest request)
			throws JsonMappingException, JsonProcessingException {

		HashMap<String, Object> resultHashMap = boardService.write(boardDto, request);
		return resultHashMap;
	}

}
