package com.ssafy.jytrip.model;

public class TripDto {
	private String firstimage, title, addr1, addr2, mapy, mapx, nickname;

	public TripDto() {
		super();
	}

	public TripDto(String firstimage, String title, String addr1, String addr2, String mapy, String mapx,
			String nickname) {
		super();
		setFirstimage(firstimage);
		setTitle(title);
		setAddr1(addr1);
		setAddr2(addr2);
		setMapy(mapy);
		setMapx(mapx);
		setNickname(nickname);
	}

	public String getFirstimage() {
		return firstimage;
	}

	public void setFirstimage(String firstimage) {
		this.firstimage = firstimage;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getMapy() {
		return mapy;
	}

	public void setMapy(String mapy) {
		this.mapy = mapy;
	}

	public String getMapx() {
		return mapx;
	}

	public void setMapx(String mapx) {
		this.mapx = mapx;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "TripDto [firstimage=" + firstimage + ", title=" + title + ", addr1=" + addr1 + ", addr2=" + addr2
				+ ", mapy=" + mapy + ", mapx=" + mapx + ", nickname=" + nickname + "]";
	}

}
