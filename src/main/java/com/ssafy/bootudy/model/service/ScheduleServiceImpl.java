package com.ssafy.bootudy.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Schedule;
import com.ssafy.bootudy.model.mapper.ScheduleMapper;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	ScheduleMapper scheduleMapper;

	/* scheduleMapper */
	@Override
	public Page<Schedule> allScheduleList() {
		// TODO Auto-generated method stub
		return scheduleMapper.allScheduleList();
	}
	
	@Override
	public Page<Schedule> fromNowScheduleList() {
		// TODO Auto-generated method stub
		return scheduleMapper.fromNowScheduleList();
	}
	
	@Override
	public Page<Schedule> searchSchedulesByTitle(String title) {
		// TODO Auto-generated method stub
		return scheduleMapper.searchSchedulesByTitle(title);
	}

	@Override
	public Page<Schedule> searchSchedulesByContent(String content) {
		// TODO Auto-generated method stub
		return scheduleMapper.searchSchedulesByContent(content);
	}
	
	@Override
	public Schedule getSchedule(int scheduleNo) {
		// TODO Auto-generated method stub
		return scheduleMapper.getSchedule(scheduleNo);
	}
	
	@Override
	public Schedule getTopSchedule() {
		// TODO Auto-generated method stub
		return scheduleMapper.getTopSchedule();
	}
	
	@Override
	public int calcDateDiff() {
		// TODO Auto-generated method stub
		return scheduleMapper.calcDateDiff();
	}

	@Override
	public int createSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		return scheduleMapper.createSchedule(schedule);
	}

	@Override
	public int updateSchedule(Schedule schedule) {
		// TODO Auto-generated method stub
		return scheduleMapper.updateSchedule(schedule);
	}

	@Override
	public int deleteSchedule(int scheduleNo) {
		// TODO Auto-generated method stub
		return scheduleMapper.deleteSchedule(scheduleNo);
	}
}
