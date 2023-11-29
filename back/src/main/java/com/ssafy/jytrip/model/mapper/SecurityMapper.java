package com.ssafy.jytrip.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jytrip.model.SecurityDto;

@Mapper
public interface SecurityMapper {

	SecurityDto getSecurityDto(String id);

	void registerSecurity(SecurityDto securityDto);
}
