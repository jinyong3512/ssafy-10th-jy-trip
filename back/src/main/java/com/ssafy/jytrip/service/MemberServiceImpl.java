package com.ssafy.jytrip.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.jytrip.model.MemberDto;
import com.ssafy.jytrip.model.SecurityDto;
import com.ssafy.jytrip.model.TokenDto;
import com.ssafy.jytrip.model.TripDto;
import com.ssafy.jytrip.model.mapper.MemberMapper;
import com.ssafy.jytrip.model.mapper.SecurityMapper;
import com.ssafy.jytrip.model.mapper.TokenMapper;
import com.ssafy.jytrip.model.mapper.TripMapper;
import com.ssafy.jytrip.util.JwtTokenProvider;
import com.ssafy.jytrip.util.UserIPUtil;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;
	@Autowired
	SecurityMapper securityMapper;
	@Autowired
	TokenMapper tokenMapper;
	@Autowired
	TripMapper tripMapper;

	@Override
	public HashMap<String, Object> register(MemberDto memberDto) throws NoSuchAlgorithmException {

		HashMap<String, Object> resultHashMap = new HashMap<>();

		// TODO : 아이디, 닉네임 중복 체크
		if (memberMapper.getMemberDto(memberDto) != null) {
			resultHashMap.put("errorMessage", "아이디, 닉네임 중복 체크");
			return resultHashMap;
		}

		// TODO : 비밀번호 패턴 매칭 확인
		if (!isValidPassword(memberDto.getPw())) {
			resultHashMap.put("errorMessage", "비밀번호 패턴 매칭 확인");
			return resultHashMap;
		}

		// TODO : 비밀번호 암호화
		SecurityDto securityDto = new SecurityDto();
		securityDto.setId(memberDto.getId());

		pwEncryption(memberDto, securityDto);

		// TODO : member table 과 security table 에 저장하기
		memberMapper.registerMember(memberDto);
		securityMapper.registerSecurity(securityDto);

		return resultHashMap;
	}

	public static boolean isValidPassword(String pw) {
		// 최소 10자 이상, 대문자, 소문자, 특수문자를 각각 1개 이상 포함하는 정규표현식
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{10,}$";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pw);

		return matcher.matches();
	}

	public void pwEncryption(MemberDto memberDto, SecurityDto securityDto) throws NoSuchAlgorithmException {

		// salt 생성
		byte[] salt = JwtTokenProvider.generateSalt();

		// 사용자로부터 입력받은 비밀번호과 salt를 이용하여 해싱
		memberDto.setPw(hashPw(memberDto.getPw(), salt)); // dto 값을 암호화한 비밀번호로 업데이트

		// security table에 salt값 저장하기 위해 추가
		securityDto.setSalt(salt);

	}

	public static String hashPw(String pw, byte[] salt) throws NoSuchAlgorithmException {

		// Salt와 비밀번호를 합침
		byte[] combined = concatenateByteArrays(pw.getBytes(), salt);

		// SHA-256 해시 알고리즘 사용
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(combined);

		// 해시된 값을 Base64로 인코딩
		String hashedPw = Base64.getEncoder().encodeToString(messageDigest.digest());

		return hashedPw;
	}

	public static byte[] concatenateByteArrays(byte[] a, byte[] b) {
		byte[] result = new byte[a.length + b.length];
		System.arraycopy(a, 0, result, 0, a.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////

	@Override
	public HashMap<String, Object> login(MemberDto memberDto, HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultHashMap = new HashMap<>();

		// db member에서 입력된 id로 조회
		MemberDto dbMemberDto = memberMapper.getMemberDto(memberDto);

		// inputId가 유효하지 않은 것
		if (dbMemberDto == null) {
			resultHashMap.put("errorMessage", "아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.\r\n" + "입력하신 내용을 다시 확인해주세요.");
			return resultHashMap;
		}

		// db security에서 입력된 id로 조회
		SecurityDto dbSecurityDto = securityMapper.getSecurityDto(memberDto.getId());

		//
		if (dbSecurityDto == null) {
			resultHashMap.put("errorMessage", "salt table에 id가 존재하지 않음 심한 버그");
			return resultHashMap;
		}

		// salt값을 더해서 비밀번호를 해싱
		String hashedPw = hashPw(memberDto.getPw(), dbSecurityDto.getSalt());

		// TODO : 비밀번호 틀림
		if (!hashedPw.equals(dbMemberDto.getPw())) {
			resultHashMap.put("errorMessage", "아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.\r\n" + "입력하신 내용을 다시 확인해주세요.");
			return resultHashMap;
		}

		// TODO : jwt 이용
		TokenDto tokenDto = new TokenDto();

		tokenDto.setIp(UserIPUtil.getUserIp(request));
		tokenDto.setId(dbMemberDto.getId());
		tokenDto.setNickname(dbMemberDto.getNickname());
		jwtLogin(tokenDto, resultHashMap);

		if (!resultHashMap.containsKey("accessToken"))
			resultHashMap.put("errorMessage", "token 만들기 실패");

		return resultHashMap;

	}

	public void jwtLogin(TokenDto tokenDto, HashMap<String, Object> resultHashMap) throws NoSuchAlgorithmException {

		String refreshToken = JwtTokenProvider.createRefreshToken(tokenDto);
		String accessToken = JwtTokenProvider.createAccessToken(tokenDto);

		tokenDto.setAccessToken(accessToken);
		tokenDto.setRefreshToken(refreshToken);

		tokenMapper.saveToken(tokenDto);

		resultHashMap.put("accessToken", accessToken);
		resultHashMap.put("refreshToken", refreshToken);
	}

	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////

	@Override
	public HashMap<String, Object> saveTripList(List<TripDto> tripList, HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultHashMap = new HashMap<>();

		String accessToken = request.getHeader("accessToken");
		Map<String, String> accessTokenDecodedPayload = JwtTokenProvider.getDecodedPayload(accessToken);

		for (TripDto t : tripList) {
			t.setNickname(accessTokenDecodedPayload.get("nickname"));
			System.out.println(t);
			tripMapper.saveTripList(t);
		}

		return resultHashMap;
	}

	@Override
	public HashMap<String, Object> getTripList(HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultHashMap = new HashMap<>();

		String accessToken = request.getHeader("accessToken");
		Map<String, String> accessTokenDecodedPayload = JwtTokenProvider.getDecodedPayload(accessToken);

		List<TripDto> list = tripMapper.getTripList(accessTokenDecodedPayload.get("nickname"));
		for (TripDto t : list) {
			System.out.println(t);
		}
		resultHashMap.put("tripList", list);

		return resultHashMap;
	}

}
