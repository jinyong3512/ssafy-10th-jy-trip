package com.ssafy.jytrip.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.jytrip.model.TripDto;

@Mapper
public interface TripMapper {

	void saveTripList(TripDto t);

	List<TripDto> getTripList(String nickname);

}
