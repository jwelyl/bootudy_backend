package com.ssafy.bootudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Result;
import com.ssafy.bootudy.model.service.ResultService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/apt")
@CrossOrigin("*")
public class ResultController {
	
	@Autowired
	ResultService resultService;
	
	@GetMapping("/{sidoName}/{gugunName}/{dongName}/{dealYear}/{dealMonth}")
	public ResponseEntity<Page<Result>> selectByAYM(@PathVariable String sidoName, @PathVariable String gugunName, @PathVariable String dongName,
			@PathVariable int dealYear, @PathVariable int dealMonth) {
		Page<Result> res = resultService.selectByAYM(sidoName, gugunName, dongName, dealYear, dealMonth);
		return new ResponseEntity<Page<Result>>(res, HttpStatus.OK);
	}
	
	
}
