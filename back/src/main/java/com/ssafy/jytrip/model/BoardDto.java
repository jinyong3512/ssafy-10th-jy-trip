package com.ssafy.jytrip.model;

import java.util.Date;

public class BoardDto {

	private int articleNumber;
	private String subject;
	private String content;
	private Date registerTime;
	private String nickname;

	public BoardDto() {
		super();
	}

	public BoardDto(int articleNumber, String subject, String content, Date registerTime, String nickname) {
		super();
		setArticleNumber(articleNumber);
		setSubject(subject);
		setContent(content);
		setRegisterTime(registerTime);
		setNickname(nickname);
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "BoardDto [articleNumber=" + articleNumber + ", subject=" + subject + ", content=" + content
				+ ", registerTime=" + registerTime + ", nickname=" + nickname + "]";
	}

}
