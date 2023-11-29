package com.ssafy.jytrip.model;

import java.util.Arrays;

public class SecurityDto {
	private String id;
	private byte[] salt;

	public SecurityDto() {
		super();
	}

	public SecurityDto(String id, byte[] salt) {

		super();
		setId(id);
		setSalt(salt);

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "SecurityDto [id=" + id + ", salt=" + Arrays.toString(salt) + "]";
	}

}
