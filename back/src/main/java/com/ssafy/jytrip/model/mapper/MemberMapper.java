package com.ssafy.jytrip.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jytrip.model.MemberDto;

@Mapper
public interface MemberMapper {

	MemberDto getMemberDto(MemberDto memberDto);

	void registerMember(MemberDto memberDto);

}
