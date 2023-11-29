package com.ssafy.jytrip.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jytrip.model.TokenDto;

@Mapper
public interface TokenMapper {

	public void saveToken(TokenDto tokenDto);

	public TokenDto getTokenDto(String nickname);
}
