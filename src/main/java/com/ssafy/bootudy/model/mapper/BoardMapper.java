package com.ssafy.bootudy.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.bootudy.model.dto.Board;
import com.ssafy.bootudy.model.dto.BoardParameter;


@Mapper
public interface BoardMapper {
	public int writeNotice(Board board) throws SQLException;
	public int writeArticle(Board board) throws SQLException;
	
	public List<Board> listNotice(BoardParameter boardParameter) throws SQLException;
	public List<Board> listArticle(BoardParameter boardParameter) throws SQLException;
	
	public int getTotalCount(BoardParameter boardParameter) throws SQLException;
	
	public Board getNotice(int articleno) throws SQLException;
	public Board getArticle(int articleno) throws SQLException;
	public void updateHit(int articleno) throws SQLException;
	
	public int modifyNotice(Board board) throws SQLException;
	public int modifyArticle(Board board) throws SQLException;
	
	public int deleteNotice(int articleno) throws SQLException;
	public int deleteArticle(int articleno) throws SQLException;
	
}