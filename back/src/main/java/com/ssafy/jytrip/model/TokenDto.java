package com.ssafy.jytrip.model;

public class TokenDto {

	private String ip;
	private String id, refreshToken;
	private String nickname, accessToken;

	public TokenDto() {
		super();
	}

	public TokenDto(String ip, String id, String refreshToken, String nickname, String accessToken) {
		super();
		setIp(ip);
		setId(id);
		setRefreshToken(refreshToken);
		setNickname(nickname);
		setAccessToken(accessToken);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "TokenDto [ip=" + ip + ", id=" + id + ", refreshToken=" + refreshToken + ", nickname=" + nickname
				+ ", accessToken=" + accessToken + "]";
	}

}
