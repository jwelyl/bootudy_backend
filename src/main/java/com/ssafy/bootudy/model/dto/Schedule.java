package com.ssafy.bootudy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
	private int scheduleNo;
	private String date;
	private String startTime;
	private String title;
	private String content;
}
