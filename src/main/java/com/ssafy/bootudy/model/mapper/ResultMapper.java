package com.ssafy.bootudy.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Result;

@Mapper
public interface ResultMapper {
	Page<Result> selectByAYM(String sidoName, String gugunName, String dongName, int dealYear, int dealMonth);
	Page<Result> selectAll();
}
