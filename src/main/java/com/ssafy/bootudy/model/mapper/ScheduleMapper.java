package com.ssafy.bootudy.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Schedule;

@Mapper
public interface ScheduleMapper {
	Page<Schedule> allScheduleList();
	Page<Schedule> fromNowScheduleList();
	Page<Schedule> searchSchedulesByTitle(String title);
	Page<Schedule> searchSchedulesByContent(String content);
	Schedule getSchedule(int scheduleNo);
	Schedule getTopSchedule();
	int calcDateDiff();
	int createSchedule(Schedule schedule);
	int updateSchedule(Schedule schedule);
	int deleteSchedule(int scheduleNo);
}
