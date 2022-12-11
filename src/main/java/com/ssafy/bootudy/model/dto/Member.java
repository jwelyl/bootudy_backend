package com.ssafy.bootudy.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "Member : 회원정보", description = "회원의 상세 정보를 나타낸다.")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
	@ApiModelProperty(value = "회원 아이디")
	private String memberId;
	@ApiModelProperty(value = "회원 비밀번호")
	private String password;
	@ApiModelProperty(value = "회원 이메일")
	private String email;
	@ApiModelProperty(value = "회원 전화번호")
	private String phoneNumber;
	@ApiModelProperty(value = "회원 역할")
	private String role;
	@ApiModelProperty(value = "회원 가입일")
	private String joinDate;
	@ApiModelProperty(value = "회원 가입 대기 상태")
	private boolean waiting;
	@ApiModelProperty(value = "회원 토큰")
	private String token;
}
