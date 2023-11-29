package com.ssafy.jytrip.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
	private int code;
	private String errorMessage;
	
	public ErrorResponse(int code, String errorMessage) {
		this.code = code;
		this.errorMessage = errorMessage;
	}
}
