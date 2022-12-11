package com.ssafy.bootudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Schedule;
import com.ssafy.bootudy.model.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin("*")
public class ScheduleController {
	@Autowired
	ScheduleService scheduleService;
	
	/* scheduleMapper */
	@GetMapping("/schedule")
	public ResponseEntity<Page<Schedule>> allScheduleList() {
		Page<Schedule> res = scheduleService.allScheduleList();
		return new ResponseEntity<Page<Schedule>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/schedule/fromnowschedulelist")
	public ResponseEntity<Page<Schedule>> fromNowScheduleList() {
		Page<Schedule> res = scheduleService.fromNowScheduleList();
		return new ResponseEntity<Page<Schedule>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/schedule/title/{title}")
	public ResponseEntity<Page<Schedule>> searchSchedulesByTitle(@PathVariable("title") String title) {
		Page<Schedule> res = scheduleService.searchSchedulesByTitle(title);
		return new ResponseEntity<Page<Schedule>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/schedule/content/{content}")
	public ResponseEntity<Page<Schedule>> searchSchedulesByContent(@PathVariable("content") String content) {
		Page<Schedule> res = scheduleService.searchSchedulesByContent(content);
		return new ResponseEntity<Page<Schedule>>(res, HttpStatus.OK);
	}
	
	@GetMapping("/schedule/{scheduleNo}")
	public ResponseEntity<Schedule> getSchedule(@PathVariable("scheduleNo")int scheduleNo) {
		Schedule res = scheduleService.getSchedule(scheduleNo);
		return new ResponseEntity<Schedule>(res, HttpStatus.OK);
	}
	
	@GetMapping("/schedule/top")
	public ResponseEntity<Schedule> getTopSchedule() {
		Schedule res = scheduleService.getTopSchedule();
		return new ResponseEntity<Schedule>(res, HttpStatus.OK);
	}
	
	@GetMapping("/schedule/dday")
	public ResponseEntity<Integer> calcDateDiff() {
		Integer res = scheduleService.calcDateDiff();
		return new ResponseEntity<Integer>(res, HttpStatus.OK);
	}
	
	@PostMapping("/schedule")
	public ResponseEntity<Page<Schedule>> createSchedule(@RequestBody Schedule schedule)  {
		scheduleService.createSchedule(schedule);
		Page<Schedule> res = scheduleService.allScheduleList();
		return new ResponseEntity<Page<Schedule>>(res, HttpStatus.OK);
	}
	
	@PutMapping("/schedule")
	public ResponseEntity<Page<Schedule>> updateSchedule(@RequestBody Schedule schedule) {
		scheduleService.updateSchedule(schedule);
		Page<Schedule> res = scheduleService.allScheduleList();
		return new ResponseEntity<Page<Schedule>>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/schedule/{scheduleNo}")
	public ResponseEntity<Page<Schedule>> deleteSchedule(@PathVariable("scheduleNo") String scheduleNo) {
		scheduleService.deleteSchedule(Integer.parseInt(scheduleNo));
		Page<Schedule> res = scheduleService.allScheduleList();
		return new ResponseEntity<Page<Schedule>>(res, HttpStatus.OK);
	}
	
	/* scheduleMemberMapper */
	
	
	
}
