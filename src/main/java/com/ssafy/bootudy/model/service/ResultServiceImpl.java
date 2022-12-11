package com.ssafy.bootudy.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Result;
import com.ssafy.bootudy.model.mapper.ResultMapper;

@Service
public class ResultServiceImpl implements ResultService {
	
	@Autowired
	ResultMapper mapper;

	@Override
	public Page<Result> selectByAYM(String sidoName, String gugunName, String dongName, int dealYear, int dealMonth) {
		return mapper.selectByAYM(sidoName, gugunName, dongName, dealYear, dealMonth);
	}
	
	@Override
	public Page<Result> selectAll() {
		return mapper.selectAll();
	}
}
