package com.ssafy.jytrip.model.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jytrip.model.BoardDto;

@Mapper
public interface BoardMapper {

	ArrayList<BoardDto> list();

	void write(BoardDto boardDto);

//	BoardDto detail(int no);

//	void delete(int no);

//	void modify(BoardDto boardDto);

}
