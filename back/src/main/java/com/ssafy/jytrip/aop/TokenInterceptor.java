package com.ssafy.jytrip.aop;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.jytrip.model.TokenDto;
import com.ssafy.jytrip.model.mapper.TokenMapper;
import com.ssafy.jytrip.util.JwtTokenProvider;
import com.ssafy.jytrip.util.UserIPUtil;

public class TokenInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenMapper tokenMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setContentType("application/json;charset=utf-8");

		// ObjectMapper를 사용하여 Map을 JSON 문자열로 변환
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, Object> resultHashMap = new HashMap<>();

		if (request.getMethod().equals("OPTIONS"))
			return true;

		String refreshToken = request.getHeader("refreshToken");
		String accessToken = request.getHeader("accessToken");

		System.out.println("input refreshToken = " + refreshToken);
		System.out.println("input accessToken = " + accessToken);

		// refreshToken 안 가져옴 => accessToken 인증하자
		if (refreshToken == null || refreshToken.equals("null")) {
			// accessToken 안 가져옴 => 로그인 하세요
			if (accessToken == null || accessToken.equals("null")) {
				System.out.println("refreshToken accessToken 둘 다 없음 로그인하세요.");
				response.setStatus(200);
				resultHashMap.put("errorMessage", "refreshToken accessToken 둘 다 없음 \n 로그인하세요.");
				String resultJson = objectMapper.writeValueAsString(resultHashMap);
				response.getWriter().write(resultJson);
				return false;
			} else {
				// accessToken만 가져옴 => accessToken 검증하자
				Map<String, String> accessTokenDecodedPayload = JwtTokenProvider.getDecodedPayload(accessToken);

				// TODO : 변조 되었는가?
				TokenDto dbTokenDto = tokenMapper.getTokenDto(accessTokenDecodedPayload.get("nickname"));

				// nickname에 해당하는 token table의 행이 없음
				if (dbTokenDto == null) {
					System.out.println("write : nickname에 해당하는 token table의 행이 없음");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "write : nickname에 해당하는 token table의 행이 없음");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

				// 너 왜 로그인했을 때 ip랑 달라졌어
				if (!UserIPUtil.getUserIp(request).equals(dbTokenDto.getIp())) {
					System.out.println("너 왜 로그인 할 때 ip랑 달라졌어");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "너 왜 로그인 할 때 ip랑 달라졌어");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

				// inputAccessToken과 dbAccessToken이 달라
				if (!(accessToken.equals(dbTokenDto.getAccessToken()))) {
					System.out.println("inputAccessToken과 dbAccessToken이 달라");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "inputAccessToken과 dbAccessToken이 달라");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

				// TODO : 시간 체크
				if (!JwtTokenProvider.validateToken(accessTokenDecodedPayload)) {
					System.out.println("accessTokenExpired");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "accessTokenExpired");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

			}

		}
		// refreshToken 가져옴 => 재발급 하자!
		else {
			// accessToken 안 가져옴 => 내 의도로는 절대 안 일어나는 버그
			if (accessToken == null || accessToken.equals("null")) {
				System.out.println("refreshToken은 가져왔는데 accessToken을 안가져왔다\\n난 재발급시 둘다 가져오게 했음\\n버그다.");
				response.setStatus(200);
				resultHashMap.put("errorMessage", "refreshToken은 가져왔는데 accessToken을 안가져왔다\\n난 재발급시 둘다 가져오게 했음\\n버그다.");
				String resultJson = objectMapper.writeValueAsString(resultHashMap);
				response.getWriter().write(resultJson);
				return false;
			} else {
				// accessToken도 가져 옴 => 재발급 하자!

				Map<String, String> refreshTokenDecodedPayload = JwtTokenProvider.getDecodedPayload(refreshToken);

				// TODO : 변조 되었는가?
				TokenDto dbTokenDto = tokenMapper
						.getTokenDto(JwtTokenProvider.getDecodedPayload(accessToken).get("nickname"));

				// nickname에 해당하는 token table의 행이 없어
				if (dbTokenDto == null) {
					System.out.println("requestNewAcessToken : nickname에 해당하는 token table의 행이 없음");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "requestNewAcessToken : nickname에 해당하는 token table의 행이 없음");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

				// 너 왜 로그인 할 때 ip랑 달라졌어
				if (!UserIPUtil.getUserIp(request).equals(dbTokenDto.getIp())) {
					System.out.println("너 왜 로그인 할 때 ip랑 달라졌어");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "너 왜 로그인 할 때 ip랑 달라졌어");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

				// inputRefreshToken과 dbRefreshToken이 달라
				if (!refreshToken.equals(dbTokenDto.getRefreshToken())) {
					System.out.println("inputRefreshToken과 dbRefreshToken이 달라");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "inputRefreshToken과 dbRefreshToken이 달라");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

				// TODO : 시간 체크
				if (!JwtTokenProvider.validateToken(refreshTokenDecodedPayload)) {
					System.out.println("RefreshTokenExpired");
					response.setStatus(200);
					resultHashMap.put("errorMessage", "RefreshTokenExpired");
					String resultJson = objectMapper.writeValueAsString(resultHashMap);
					response.getWriter().write(resultJson);
					return false;
				}

				// TODO : accessToken 재발급 해주기
				String newAccessToken = JwtTokenProvider.createAccessToken(dbTokenDto);

				dbTokenDto.setAccessToken(newAccessToken);

				tokenMapper.saveToken(dbTokenDto);

				resultHashMap.put("accessToken", newAccessToken);
				response.setStatus(200);
				String resultJson = objectMapper.writeValueAsString(resultHashMap);
				response.getWriter().write(resultJson);
				return false;
			}

		}

		return true;
	}

}
