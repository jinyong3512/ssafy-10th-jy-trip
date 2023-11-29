package com.ssafy.jytrip.util;

import java.sql.SQLException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerAdvisor {

	@ExceptionHandler({ SQLException.class })
	@ResponseBody
	public ResponseEntity<?> handleException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(444, "에러뜸");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 처리하고 싶은 exception아래에 추가
}
