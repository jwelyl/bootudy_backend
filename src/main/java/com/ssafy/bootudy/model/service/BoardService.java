package com.ssafy.bootudy.model.service;

import java.util.List;

import com.ssafy.bootudy.model.dto.Board;
import com.ssafy.bootudy.model.dto.BoardParameter;
import com.ssafy.bootudy.util.PageNavigation;

public interface BoardService {
	public boolean writeNotice(Board board) throws Exception;
	public boolean writeArticle(Board board) throws Exception;

	public List<Board> listNotice(BoardParameter boardParameter) throws Exception;
	public List<Board> listArticle(BoardParameter boardParameter) throws Exception;

	public PageNavigation makePageNavigation(BoardParameter boardParameter) throws Exception;

	public Board getNotice(int articleno) throws Exception;
	public Board getArticle(int articleno) throws Exception;

	public void updateHit(int articleno) throws Exception;

	public boolean modifyNotice(Board board) throws Exception;
	public boolean modifyArticle(Board board) throws Exception;

	public boolean deleteNotice(int articleno) throws Exception;
	public boolean deleteArticle(int articleno) throws Exception;
}
