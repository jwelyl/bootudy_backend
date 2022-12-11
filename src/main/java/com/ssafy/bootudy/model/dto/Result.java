package com.ssafy.bootudy.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result implements Comparable<Result> {
	private String aptName;
	private String dealAmount;
	private String area;
	private int dealYear;
	private int dealMonth;
	private int dealDay;
	private int buildYear;
	private String lat;
	private String lng;
	private String sidoName;
	private String gugunName;
	private String dongName;
	private String dongCode;
	
	@Override
	public int compareTo(Result result) {
		// TODO Auto-generated method stub
		int ret = this.lat.compareTo(result.lat);
		
		if(ret == 0) {
			ret = this.lng.compareTo(result.lng);
		}
		
		return ret;
	}

	
}
