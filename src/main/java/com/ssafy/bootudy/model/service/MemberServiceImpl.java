package com.ssafy.bootudy.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.ssafy.bootudy.model.dto.Member;
import com.ssafy.bootudy.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private SqlSession sqlSession;
//	@Autowired
//	MemberMapper mapper;
	
	@Override
	public Member login(Member member) throws SQLException {
		if (member.getMemberId() == null || member.getPassword() == null) {
			System.out.println("member id = " + member.getMemberId());
			System.out.println("member password = " + member.getPassword());
			return null;
		}
		System.out.println(sqlSession.getMapper(MemberMapper.class).login(member));
		return sqlSession.getMapper(MemberMapper.class).login(member);
	}

	@Override
	public Member memberInfo(String memberId) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(MemberMapper.class).memberInfo(memberId);
	}

	@Override
	public Page<Member> allMembersInfo() {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(MemberMapper.class).allMembersInfo();
	}

	@Override
	public Page<Member> waitingMembersInfo() {
		return sqlSession.getMapper(MemberMapper.class).waitingMembersInfo();
	}

	@Override
	public int insert(Member member) {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(MemberMapper.class).insert(member);
	}

	@Override
	public int update(Member member) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(MemberMapper.class).update(member);
	}

	@Override
	public int updateRole(Member member) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(MemberMapper.class).updateRole(member);
	}
	
	@Override
	public int approve(String memberId) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).approve(memberId);
	}

	@Override
	public int delete(String memberId) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(MemberMapper.class).delete(memberId);
	}

	@Override
	public void saveRefreshToken(String memberId, String refreshToken) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("token", refreshToken);
		sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String memberId) throws SQLException {
		return sqlSession.getMapper(MemberMapper.class).getRefreshToken(memberId);
	}

	@Override
	public void deleteRefreshToken(String memberId) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("token", null);
		sqlSession.getMapper(MemberMapper.class).deleteRefreshToken(map);
	}
	
	@Override
	public Page<Member> memberWithId(String memberId) {
		return sqlSession.getMapper(MemberMapper.class).memberWithId(memberId);
	}
	
	@Override
	public Page<Member> waitingMemberWithId(String memberId) {
		return sqlSession.getMapper(MemberMapper.class).waitingMemberWithId(memberId);
	}
}
