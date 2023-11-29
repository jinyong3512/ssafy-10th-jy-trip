package com.ssafy.jytrip.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.jytrip.model.TokenDto;

import io.jsonwebtoken.*;

public class JwtTokenProvider {

	public static String createAccessToken(TokenDto tokenDto) throws NoSuchAlgorithmException {
		String token = "";

		Claims claims = Jwts.claims();
		claims.put("nickname", tokenDto.getNickname());

		Date now = new Date();

		token = Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE).setClaims(claims)
				.setExpiration(new Date(now.getTime() + (1000L * 60 * 1)))// 만료시간 1분
				.signWith(SignatureAlgorithm.HS256, generateSalt()).compact();

		System.out.println("create AccessToken expired = " + new Date(now.getTime() + (1000L * 60 * 1)));

		return token;
	}

	public static String createRefreshToken(TokenDto tokenDto) throws NoSuchAlgorithmException {
		String token = "";

		Claims claims = Jwts.claims();
		claims.put("id", tokenDto.getId());

		Date now = new Date();

		token = Jwts.builder().setHeaderParam(Header.TYPE, Header.JWT_TYPE).setClaims(claims)
				.setExpiration(new Date(now.getTime() + (1000L * 60 * 2)))// 만료시간 2분
				.signWith(SignatureAlgorithm.HS256, generateSalt()).compact();

		System.out.println("create RefreshToken expired = " + new Date(now.getTime() + (1000L * 60 * 2)));

		return token;
	}

	public static Map<String, String> getDecodedPayload(String token)
			throws JsonMappingException, JsonProcessingException {
		// JWT 토큰은 세 부분으로 분리되어 있으며 Payload는 두 번째 부분
		String[] jwtParts = token.split("\\.");

		// Payload는 Base64로 인코딩되어 있으므로 디코딩하여 원본 데이터 획득
		String payload = jwtParts[1];
		byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);

		String decodedPayload = new String(decodedBytes);

		// Payload 데이터 출력 (여기서는 문자열로 반환)
		ObjectMapper objectMapper = new ObjectMapper();

		// JSON 문자열을 Map으로 변환
		return objectMapper.readValue(decodedPayload, new TypeReference<Map<String, String>>() {
		});
	}

	// Jwt Token 및 만료 기간 검사합니다
	public static boolean validateToken(Map<String, String> decodedPayload) {
		return !(new Date(Long.parseLong(decodedPayload.get("exp")) * 1000L).before(new Date()));
	}

	public static byte[] generateSalt() throws NoSuchAlgorithmException {
		int saltLength = 16;
		SecureRandom secureRandom;

		secureRandom = SecureRandom.getInstanceStrong();

		byte[] salt = new byte[saltLength];
		secureRandom.nextBytes(salt);

		return salt;
	}

}