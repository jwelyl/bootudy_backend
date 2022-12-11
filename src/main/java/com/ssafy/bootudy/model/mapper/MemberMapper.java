package com.ssafy.bootudy.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Member;

@Mapper
public interface MemberMapper {
	//	로그인(id, pw 비교)
	Member login(Member member) throws SQLException;
	
	//	단순 유저 정보 가져오기
	//	한명의 유저 정보
	Member memberInfo(String memberId);
	
	//	가입한 전체 멤버 정보
	Page<Member> allMembersInfo();
	
	//	가입 대기중인 멤버 정보
	Page<Member> waitingMembersInfo();
	
	//	회원가입, 회원정보 수정, 회원탈퇴, 추방
	int insert(Member member);
	
	//	단순 회원정보 수정
	int update(Member member) throws SQLException;
	//	회원 역할 변경
	int updateRole(Member member) throws SQLException;
	//	회원 가입 승인
	int approve(String memberId) throws SQLException;
	
	//	회원 탈퇴, 추방
	int delete(String memberId) throws SQLException;
	
	//	token 관련
	void saveRefreshToken(Map<String, String> map) throws SQLException;
	Object getRefreshToken(String memberId) throws SQLException;
	void deleteRefreshToken(Map<String, String> map) throws SQLException;
		
	Page<Member> memberWithId(String memberId);
	Page<Member> waitingMemberWithId(String memberId);
}
