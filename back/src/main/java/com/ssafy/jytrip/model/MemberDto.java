package com.ssafy.jytrip.model;

public class MemberDto {

	private String id, pw, nickname, email;

	public MemberDto() {
		super();
	}

	public MemberDto(String id, String pw, String nickname, String email) {
		super();
		setId(id);
		setPw(pw);
		setNickname(nickname);
		setEmail(email);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", pw=" + pw + ", nickname=" + nickname + ", email=" + email + "]";
	}

}
